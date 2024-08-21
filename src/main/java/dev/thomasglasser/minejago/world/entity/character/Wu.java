package dev.thomasglasser.minejago.world.entity.character;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.network.ClientboundOpenPowerSelectionScreenPayload;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.ai.behavior.GivePowerAndGi;
import dev.thomasglasser.minejago.world.entity.ai.behavior.TrackSpinjitzuCourseCompletion;
import dev.thomasglasser.minejago.world.entity.ai.memory.MinejagoMemoryModuleTypes;
import dev.thomasglasser.minejago.world.entity.ai.poi.MinejagoPoiTypes;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.AbstractSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.SpinjitzuCourseTracker;
import dev.thomasglasser.minejago.world.focus.FocusConstants;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;

public class Wu extends Character implements SpinjitzuCourseTracker {
    public static final String POWER_GIVEN_KEY = "gui.power_selection.power_given";
    public static final String NO_POWER_GIVEN_KEY = "gui.power_selection.no_power_given";

    public static final RawAnimation ATTACK_SWING_WITH_STICK = RawAnimation.begin().thenPlay("attack.swing_with_stick");
    public static final RawAnimation MOVE_WALK_WITH_STICK = RawAnimation.begin().thenPlay("move.walk_with_stick");

    protected final Map<Player, Set<AbstractSpinjitzuCourseElement<?>>> courseData = new HashMap<>();
    protected final Map<Integer, List<Player>> entitiesOnCooldown = new HashMap<>();
    protected final Set<AbstractSpinjitzuCourseElement<?>> trackedCourseElements = new HashSet<>();

    protected List<ResourceKey<Power>> powersToGive = new ArrayList<>();
    protected boolean givingPower;
    protected boolean lifting;
    protected boolean interrupted;

    public Wu(EntityType<? extends Wu> entityType, Level level) {
        super(entityType, level);
        new PowerData(MinejagoPowers.CREATION, true).save(this, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Character.createAttributes()
                .add(Attributes.MAX_HEALTH, ((RangedAttribute) Attributes.MAX_HEALTH.value()).getMaxValue())
                .add(Attributes.ATTACK_KNOCKBACK, ((RangedAttribute) Attributes.ATTACK_KNOCKBACK.value()).getMaxValue())
                .add(Attributes.ATTACK_DAMAGE, ((RangedAttribute) Attributes.ATTACK_DAMAGE.value()).getMaxValue());
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, MinejagoItems.BAMBOO_STAFF.get().getDefaultInstance());
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        populateDefaultEquipmentSlots(random, difficultyInstance);
        setCanPickUpLoot(false);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        boolean canGivePower = !player.getData(MinejagoAttachmentTypes.POWER).given() || MinejagoServerConfig.INSTANCE.allowChange.get();
        if (canGivePower && player.getInventory().hasAnyMatching(itemStack -> itemStack.has(MinejagoDataComponents.GOLDEN_WEAPONS_MAP.get()))) {
            Registry<Power> registry = level().registryAccess().registryOrThrow(MinejagoRegistries.POWER);

            if (!MinejagoServerConfig.INSTANCE.drainPool.get() || (powersToGive.size() <= 1)) {
                powersToGive = new ArrayList<>(registry.registryKeySet());
                if (!MinejagoServerConfig.INSTANCE.enableNoPower.get())
                    removePowersToGive(MinejagoPowers.NONE);
                powersToGive.removeIf(key -> registry.get(key) != null && registry.get(key).isSpecial());
            }

            if (player instanceof ServerPlayer serverPlayer && hand == InteractionHand.MAIN_HAND) {
                givingPower = true;
                if (MinejagoServerConfig.INSTANCE.allowChoose.get()) {
                    TommyLibServices.NETWORK.sendToClient(new ClientboundOpenPowerSelectionScreenPayload(powersToGive, Optional.of(this.getId())), serverPlayer);
                } else if (this.distanceTo(serverPlayer) > 1.0f) {
                    ResourceKey<Power> oldPower = serverPlayer.getData(MinejagoAttachmentTypes.POWER).power();
                    if (serverPlayer.getData(MinejagoAttachmentTypes.POWER).given() && oldPower != MinejagoPowers.NONE && MinejagoServerConfig.INSTANCE.drainPool.get())
                        addPowersToGive(oldPower);
                    ResourceKey<Power> newPower = powersToGive.get(random.nextInt(powersToGive.size()));
                    if (newPower != MinejagoPowers.NONE) removePowersToGive(newPower);
                    if (newPower == MinejagoPowers.NONE) {
                        new PowerData(newPower, true).save(serverPlayer, true);
                        serverPlayer.displayClientMessage(Component.translatable(Wu.NO_POWER_GIVEN_KEY), true);
                        givingPower = false;
                    } else {
                        BrainUtils.setMemory(this, MemoryModuleType.INTERACTION_TARGET, serverPlayer);
                        BrainUtils.setMemory(this, MinejagoMemoryModuleTypes.SELECTED_POWER.get(), newPower);
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
                    // TODO: Make happy noise (particles?)
                    player.sendSystemMessage(Component.literal("Success"));
                    stopTracking(player);
                    setLifting(false);
                    setInterrupted(false);
                }
            } else {
                List<LivingEntity> cooldownList = new ArrayList<>();
                entitiesOnCooldown.values().forEach(cooldownList::addAll);
                if (cooldownList.contains(player)) {
                    // TODO: Make sad noise (particles?)
                    player.sendSystemMessage(Component.literal("Cooldown"));
                } else {
                    Optional<BlockPos> teapotPos = serverLevel.getPoiManager().findClosest(poi -> poi.is(MinejagoPoiTypes.TEAPOTS), blockPosition(), MinejagoServerConfig.INSTANCE.courseRadius.get(), PoiManager.Occupancy.ANY);
                    if (teapotPos.isPresent()) {
                        if (trackedCourseElements.isEmpty()) {
                            List<? extends AbstractSpinjitzuCourseElement<?>> nearbyElements = level().getEntities(this, getBoundingBox().inflate(MinejagoServerConfig.INSTANCE.courseRadius.get()), entity -> entity instanceof AbstractSpinjitzuCourseElement<?> element && element.isActive()).stream().map(entity -> (AbstractSpinjitzuCourseElement<?>) entity).toList();
                            if (!nearbyElements.isEmpty()) {
                                trackedCourseElements.addAll(nearbyElements);
                                nearbyElements.forEach(element -> element.beginTracking(this));
                            }
                        }
                        if (trackedCourseElements.isEmpty()) {
                            // TODO: Make sad noise (particles?)
                            player.sendSystemMessage(Component.literal("No course"));
                        } else {
                            courseData.put(player, new HashSet<>());
                            BlockPos pos = teapotPos.get();
                            BrainUtils.setMemory(this, MemoryModuleType.WALK_TARGET, new WalkTarget(pos.relative(Direction.getRandom(random)), 1f, 2));
                            // TODO: Doesn't stay in rest state, fix
                            getBrain().setActiveActivityIfPossible(Activity.REST);
                        }
                    } else {
                        // TODO: Make sad noise (particles?)
                        player.sendSystemMessage(Component.literal("No teapots"));
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

    public void stopTracking(Player player) {
        courseData.remove(player);
        if (courseData.isEmpty()) {
            trackedCourseElements.forEach(AbstractSpinjitzuCourseElement::endTracking);
            trackedCourseElements.clear();
        }
    }

    @SafeVarargs
    public final List<ResourceKey<Power>> addPowersToGive(ResourceKey<Power>... keys) {
        Arrays.stream(keys).forEach(key -> {
            if (!powersToGive.contains(key)) powersToGive.add(key);
        });

        return powersToGive;
    }

    @SafeVarargs
    public final List<ResourceKey<Power>> removePowersToGive(ResourceKey<Power>... keys) {
        Arrays.stream(keys).forEach(key -> {
            while (powersToGive.contains(key)) powersToGive.remove(key);
        });

        return powersToGive;
    }

    @Override
    public BrainActivityGroup<Character> getIdleTasks() {
        return super.getIdleTasks().behaviours(
                new FirstApplicableBehaviour<>(
                        Pair.of(new GivePowerAndGi<Character>()
                                .startCondition(character -> character instanceof Wu wu && wu.givingPower)
                                .whenStarting(character -> character.setDoingSpinjitzu(true))
                                .whenStopping(character -> character.setDoingSpinjitzu(false)), 0),
                        Pair.of(new TrackSpinjitzuCourseCompletion(), 0)));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
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
        controllerRegistrar.add(new AnimationController<GeoAnimatable>(this, "spinjitzu", animationState -> {
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
                interrupted = true;
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

    public boolean wasInterrupted() {
        return interrupted;
    }

    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }

    public boolean isLifting() {
        return lifting;
    }

    public void setLifting(boolean lifting) {
        this.lifting = lifting;
    }

    @Override
    public boolean shouldSetRandomWalkTarget(Character character) {
        return courseData.isEmpty();
    }
}
