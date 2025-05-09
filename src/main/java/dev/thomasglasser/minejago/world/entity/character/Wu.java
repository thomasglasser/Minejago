package dev.thomasglasser.minejago.world.entity.character;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.network.ClientboundOpenElementSelectionScreenPayload;
import dev.thomasglasser.minejago.network.ClientboundSetGlowingTag;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.tags.ElementTags;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.MinejagoEntitySerializers;
import dev.thomasglasser.minejago.world.entity.ai.behavior.GiveElementAndGi;
import dev.thomasglasser.minejago.world.entity.ai.behavior.TrackSpinjitzuCourseCompletion;
import dev.thomasglasser.minejago.world.entity.ai.memory.MinejagoMemoryModuleTypes;
import dev.thomasglasser.minejago.world.entity.ai.poi.MinejagoPoiTypes;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.minejago.world.entity.element.Elements;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.AbstractSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.SpinjitzuCourseTracker;
import dev.thomasglasser.minejago.world.focus.FocusConstants;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.level.storage.ElementData;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;

public class Wu extends Character implements SpinjitzuCourseTracker {
    public static final String ELEMENT_GIVEN_KEY = "gui.element_selection.element_given";
    public static final String NO_ELEMENT_GIVEN_KEY = "gui.element_selection.no_element_given";

    public static final RawAnimation ATTACK_SWING_WITH_STICK = RawAnimation.begin().thenPlay("attack.swing_with_stick");
    public static final RawAnimation MOVE_WALK_WITH_STICK = RawAnimation.begin().thenPlay("move.walk_with_stick");
    public static final RawAnimation MISC_PLACE_IN_LAP = RawAnimation.begin().thenPlay("misc.place_in_lap");
    public static final RawAnimation MISC_PREPARE_TEA = RawAnimation.begin().thenPlay("misc.prepare_tea");
    public static final RawAnimation MISC_KNOCK_CUP = RawAnimation.begin().thenPlay("misc.knock_cup");
    public static final RawAnimation MISC_DRINK = RawAnimation.begin().thenPlay("misc.drink");
    public static final RawAnimation MISC_LOWER_CUP = RawAnimation.begin().thenPlay("misc.lower_cup");

    private static final EntityDataAccessor<TrackSpinjitzuCourseCompletion.Stage> DATA_STAGE = SynchedEntityData.defineId(Wu.class, MinejagoEntitySerializers.STAGE.get());
    private static final EntityDataAccessor<Boolean> DATA_INTERRUPTED = SynchedEntityData.defineId(Wu.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_INTERRUPTED_TICKS = SynchedEntityData.defineId(Wu.class, EntityDataSerializers.INT);

    protected final Map<Player, Set<AbstractSpinjitzuCourseElement<?>>> courseData = new HashMap<>();
    protected final Map<Integer, List<Player>> entitiesOnCooldown = new HashMap<>();
    protected final Set<AbstractSpinjitzuCourseElement<?>> trackedCourseElements = new HashSet<>();

    protected ArrayList<Holder<Element>> elementsToGive = new ArrayList<>();
    protected boolean givingElement;
    protected boolean lifting;
    protected int maxTime;
    protected int timeLeft;

    public Wu(EntityType<? extends Wu> entityType, Level level) {
        super(entityType, level);
        new ElementData(Elements.CREATION, true).save(this, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Character.createAttributes()
                .add(Attributes.MAX_HEALTH, ((RangedAttribute) Attributes.MAX_HEALTH.value()).getMaxValue())
                .add(Attributes.ATTACK_KNOCKBACK, ((RangedAttribute) Attributes.ATTACK_KNOCKBACK.value()).getMaxValue())
                .add(Attributes.ATTACK_DAMAGE, ((RangedAttribute) Attributes.ATTACK_DAMAGE.value()).getMaxValue());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_STAGE, TrackSpinjitzuCourseCompletion.Stage.STAND_UP);
        builder.define(DATA_INTERRUPTED, false);
        builder.define(DATA_INTERRUPTED_TICKS, 0);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, MinejagoItems.BAMBOO_STAFF.get().getDefaultInstance());
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_363316_, @Nullable SpawnGroupData p_146749_) {
        SpawnGroupData data = super.finalizeSpawn(p_146746_, p_146747_, p_363316_, p_146749_);
        populateDefaultEquipmentSlots(random, p_146747_);
        setCanPickUpLoot(false);
        return data;
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        boolean canGiveElement = !player.getData(MinejagoAttachmentTypes.ELEMENT).given() || MinejagoServerConfig.get().allowChange.get();
        if (canGiveElement && player.getInventory().hasAnyMatching(itemStack -> itemStack.has(MinejagoDataComponents.GOLDEN_WEAPONS_MAP.get()))) {

            if (!MinejagoServerConfig.get().drainPool.get() || (elementsToGive.size() <= 1)) {
                elementsToGive = new ArrayList<>(level().registryAccess().registryOrThrow(MinejagoRegistries.ELEMENT).getOrCreateTag(ElementTags.ELEMENTS_OF_CREATION).stream().toList());
                if (!MinejagoServerConfig.get().enableNoElement.get())
                    removeElementsToGive(level().holderOrThrow(Elements.NONE));
            }

            if (player instanceof ServerPlayer serverPlayer && hand == InteractionHand.MAIN_HAND) {
                givingElement = true;
                if (MinejagoServerConfig.get().allowChoose.get()) {
                    TommyLibServices.NETWORK.sendToClient(new ClientboundOpenElementSelectionScreenPayload(elementsToGive, Optional.of(this.getId())), serverPlayer);
                } else if (this.distanceTo(serverPlayer) > 1.0f) {
                    Holder<Element> oldElement = level().holderOrThrow(serverPlayer.getData(MinejagoAttachmentTypes.ELEMENT).element());
                    if (serverPlayer.getData(MinejagoAttachmentTypes.ELEMENT).given() && oldElement != Elements.NONE && MinejagoServerConfig.get().drainPool.get())
                        addElementsToGive(oldElement);
                    Holder<Element> newElement = elementsToGive.get(random.nextInt(elementsToGive.size()));
                    if (newElement.getKey() != Elements.NONE) removeElementsToGive(newElement);
                    if (newElement == Elements.NONE) {
                        new ElementData(newElement.getKey(), true).save(serverPlayer, true);
                        serverPlayer.displayClientMessage(Component.translatable(Wu.NO_ELEMENT_GIVEN_KEY), true);
                        givingElement = false;
                    } else {
                        BrainUtils.setMemory(this, MemoryModuleType.INTERACTION_TARGET, serverPlayer);
                        BrainUtils.setMemory(this, MinejagoMemoryModuleTypes.SELECTED_ELEMENT.get(), newElement.getKey());
                    }
                }
            }
        } else if (!player.getInventory().hasAnyOf(Set.copyOf(MinejagoArmors.BLACK_GI_SET.getAllAsItems()))) {
            MinejagoArmors.BLACK_GI_SET.getAllAsItems().forEach(armorItem -> player.addItem(armorItem.getDefaultInstance()));
        } else if (level() instanceof ServerLevel serverLevel && player.getData(MinejagoAttachmentTypes.FOCUS).getFocusLevel() >= FocusConstants.LEARN_SPINJITZU_LEVEL) {
            if (courseData.containsKey(player)) {
                if (courseData.get(player).equals(trackedCourseElements)) {
                    new SpinjitzuData(true, false).save(player, true);
                    player.getData(MinejagoAttachmentTypes.FOCUS).addExhaustion(FocusConstants.EXHAUSTION_LEARN_SPINJITZU);
                    playSound(SoundEvents.VILLAGER_CELEBRATE, 1.0f, 0.9f);
                    courseData.remove(player);
                    setLifting(false);
                    entityData.set(DATA_INTERRUPTED, false);
                    if (player instanceof ServerPlayer serverPlayer)
                        TommyLibServices.NETWORK.sendToClient(new ClientboundSetGlowingTag(trackedCourseElements.stream().map(Entity::getId).toList(), false), serverPlayer);
                } else {
                    playSound(SoundEvents.VILLAGER_NO, 1.0f, 0.9f);
                    ArrayList<AbstractSpinjitzuCourseElement<?>> remaining = new ArrayList<>(trackedCourseElements);
                    remaining.removeAll(courseData.get(player));
                    if (player instanceof ServerPlayer serverPlayer)
                        TommyLibServices.NETWORK.sendToClient(new ClientboundSetGlowingTag(remaining.stream().map(Entity::getId).toList(), true), serverPlayer);
                }
            } else {
                List<LivingEntity> cooldownList = new ArrayList<>();
                entitiesOnCooldown.values().forEach(cooldownList::addAll);
                if (cooldownList.contains(player)) {
                    playSound(SoundEvents.VILLAGER_NO, 1.0f, 0.9f);
                } else {
                    Optional<BlockPos> teapotPos = serverLevel.getPoiManager().findClosest(poi -> poi.is(MinejagoPoiTypes.TEAPOTS), blockPosition(), MinejagoServerConfig.get().courseRadius.get(), PoiManager.Occupancy.ANY);
                    if (teapotPos.isPresent()) {
                        if (trackedCourseElements.isEmpty()) {
                            List<? extends AbstractSpinjitzuCourseElement<?>> nearbyElements = level().getEntities(this, getBoundingBox().inflate(MinejagoServerConfig.get().courseRadius.get()), entity -> entity instanceof AbstractSpinjitzuCourseElement<?> element && element.isActive() && element.shouldIncludeInTracking()).stream().map(entity -> (AbstractSpinjitzuCourseElement<?>) entity).toList();
                            if (!nearbyElements.isEmpty()) {
                                trackedCourseElements.addAll(nearbyElements);
                                nearbyElements.forEach(element -> element.beginTracking(this));
                            }
                        }
                        if (trackedCourseElements.isEmpty()) {
                            playSound(SoundEvents.VILLAGER_NO, 1.0f, 0.9f);
                        } else {
                            courseData.put(player, new HashSet<>());
                            BlockPos pos = teapotPos.get();
                            BrainUtils.setMemory(this, MemoryModuleType.WALK_TARGET, new WalkTarget(pos.relative(Direction.getRandom(random)), 1f, 0));
                        }
                    } else {
                        playSound(SoundEvents.VILLAGER_NO, 1.0f, 0.9f);
                    }
                }
            }
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            entitiesOnCooldown.remove((int) level().getGameTime());
        }
    }

    @SafeVarargs
    public final ArrayList<Holder<Element>> addElementsToGive(Holder<Element>... holders) {
        for (Holder<Element> holder : holders) {
            if (!elementsToGive.contains(holder)) elementsToGive.add(holder);
        }

        return elementsToGive;
    }

    @SafeVarargs
    public final ArrayList<Holder<Element>> removeElementsToGive(Holder<Element>... holders) {
        for (Holder<Element> holder : holders) {
            while (elementsToGive.contains(holder)) elementsToGive.remove(holder);
        }

        return elementsToGive;
    }

    @Override
    public BrainActivityGroup<Character> getIdleTasks() {
        return super.getIdleTasks().behaviours(
                new FirstApplicableBehaviour<>(
                        Pair.of(new GiveElementAndGi<Character>()
                                .startCondition(character -> character instanceof Wu wu && wu.givingElement)
                                .whenStarting(character -> character.setDoingSpinjitzu(true))
                                .whenStopping(character -> character.setDoingSpinjitzu(false)), 0),
                        Pair.of(new TrackSpinjitzuCourseCompletion(), 0)));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "Tea", animationState -> {
            if (isInterrupted())
                return animationState.setAndContinue(MISC_KNOCK_CUP);
            else if (getCurrentStage() == TrackSpinjitzuCourseCompletion.Stage.SIT_DOWN)
                return animationState.setAndContinue(Character.MEDITATION_START);
            else if (getCurrentStage() == TrackSpinjitzuCourseCompletion.Stage.PAPER)
                return animationState.setAndContinue(MISC_PLACE_IN_LAP);
            else if (getCurrentStage() == TrackSpinjitzuCourseCompletion.Stage.PREPARE)
                return animationState.setAndContinue(MISC_PREPARE_TEA);
            else if (getCurrentStage() == TrackSpinjitzuCourseCompletion.Stage.DRINK)
                return animationState.setAndContinue(MISC_DRINK);
            else if (getCurrentStage() == TrackSpinjitzuCourseCompletion.Stage.PUT_DOWN)
                return animationState.setAndContinue(MISC_LOWER_CUP);
            else if (getCurrentStage() == TrackSpinjitzuCourseCompletion.Stage.STAND_UP)
                return animationState.setAndContinue(Character.MEDITATION_FINISH);
            return PlayState.STOP;
        }));
        controllerRegistrar.add(new AnimationController<>(this, "StickAttack", 5, state -> {
            if (this.swinging)
                return getMainHandItem().getItem() == MinejagoItems.BAMBOO_STAFF.get() ? state.setAndContinue(ATTACK_SWING_WITH_STICK) : state.setAndContinue(DefaultAnimations.ATTACK_SWING);

            state.getController().forceAnimationReset();

            return PlayState.STOP;
        }));
        controllerRegistrar.add(new AnimationController<>(this, "StickWalk", 5, state -> {
            if (state.isMoving())
                return getMainHandItem().getItem() == MinejagoItems.BAMBOO_STAFF.get() ? state.setAndContinue(MOVE_WALK_WITH_STICK) : state.setAndContinue(DefaultAnimations.WALK);

            return PlayState.STOP;
        }));
        controllerRegistrar.add(new AnimationController<>(this, "Spinjitzu", animationState -> {
            if (isDoingSpinjitzu())
                return animationState.setAndContinue(SPINJITZU);

            animationState.getController().forceAnimationReset();

            return PlayState.STOP;
        }));
    }

    @Override
    public void markVisited(AbstractSpinjitzuCourseElement<?> element, Player visitor) {
        if (courseData.containsKey(visitor)) {
            courseData.get(visitor).add(element);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!courseData.isEmpty() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            if (isLifting()) {
                markInterrupted();
            }
            return false;
        }
        return super.hurt(source, amount);
    }

    public Map<Player, Set<AbstractSpinjitzuCourseElement<?>>> getCourseData() {
        return courseData;
    }

    public Map<Integer, List<Player>> getEntitiesOnCooldown() {
        return entitiesOnCooldown;
    }

    public boolean isLifting() {
        return lifting;
    }

    public void setLifting(boolean lifting) {
        this.lifting = lifting;
    }

    @Override
    public boolean shouldSetRandomWalkTarget(Character character) {
        return trackedCourseElements.isEmpty();
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public int tickTimeLeft() {
        return --timeLeft;
    }

    public void setCurrentStage(TrackSpinjitzuCourseCompletion.Stage currentStage) {
        this.entityData.set(DATA_STAGE, currentStage);
    }

    public TrackSpinjitzuCourseCompletion.Stage getCurrentStage() {
        return this.entityData.get(DATA_STAGE);
    }

    public void markInterrupted() {
        entityData.set(DATA_INTERRUPTED, true);
        entityData.set(DATA_INTERRUPTED_TICKS, TrackSpinjitzuCourseCompletion.INTERRUPTED_DURATION);
        setLifting(false);
        setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
    }

    public boolean isInterrupted() {
        return entityData.get(DATA_INTERRUPTED);
    }

    public boolean tickInterrupted() {
        if (isInterrupted()) {
            int interruptedTicks = entityData.get(DATA_INTERRUPTED_TICKS) - 1;
            entityData.set(DATA_INTERRUPTED_TICKS, interruptedTicks);
            if (interruptedTicks <= 0) {
                entityData.set(DATA_INTERRUPTED, false);
                setTimeLeft(getMaxTime() - (TrackSpinjitzuCourseCompletion.SIT_DOWN_DURATION + TrackSpinjitzuCourseCompletion.PAPER_DURATION));
                setItemInHand(InteractionHand.MAIN_HAND, MinejagoItems.TEACUPS.get(DyeColor.LIGHT_BLUE).toStack());
            }
        }
        return isInterrupted();
    }

    public Set<AbstractSpinjitzuCourseElement<?>> getTrackedElements() {
        return trackedCourseElements;
    }

    public void clearTrackedElements() {
        trackedCourseElements.forEach(AbstractSpinjitzuCourseElement::endTracking);
        trackedCourseElements.clear();
    }
}
