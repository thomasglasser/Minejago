package dev.thomasglasser.minejago.world.entity;

import com.google.common.collect.HashMultimap;
import com.google.common.util.concurrent.AtomicDouble;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoKeyMappings;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleUtils;
import dev.thomasglasser.minejago.network.ClientboundRefreshVipDataPayload;
import dev.thomasglasser.minejago.network.ClientboundStartMeditationPayload;
import dev.thomasglasser.minejago.network.ClientboundStartMegaMeditationPayload;
import dev.thomasglasser.minejago.network.ClientboundStartSpinjitzuPayload;
import dev.thomasglasser.minejago.network.ClientboundStopAnimationPayload;
import dev.thomasglasser.minejago.network.ClientboundStopMeditationPayload;
import dev.thomasglasser.minejago.network.ClientboundStopSpinjitzuPayload;
import dev.thomasglasser.minejago.network.ServerboundStartMeditationPayload;
import dev.thomasglasser.minejago.network.ServerboundStartSpinjitzuPayload;
import dev.thomasglasser.minejago.network.ServerboundStopMeditationPayload;
import dev.thomasglasser.minejago.network.ServerboundStopSpinjitzuPayload;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.character.Character;
import dev.thomasglasser.minejago.world.entity.character.Zane;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.entity.skill.MinejagoSkills;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaid;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaidsHolder;
import dev.thomasglasser.minejago.world.focus.FocusConstants;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.focus.modifier.blockstate.BlockStateFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.entity.EntityFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.itemstack.ItemStackFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.resourcekey.ResourceKeyFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.world.Weather;
import dev.thomasglasser.minejago.world.focus.modifier.world.WorldFocusModifiers;
import dev.thomasglasser.minejago.world.item.GoldenWeaponItem;
import dev.thomasglasser.minejago.world.item.MinejagoItemUtils;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.level.MinejagoLevelUtils;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.minejago.world.level.gameevent.MinejagoGameEvents;
import dev.thomasglasser.minejago.world.level.levelgen.SkulkinArmySpawner;
import dev.thomasglasser.minejago.world.level.storage.SkillData;
import dev.thomasglasser.minejago.world.level.storage.SkillDataSet;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import dev.thomasglasser.tommylib.api.tags.ConventionalItemTags;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ModifyCustomSpawnersEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class MinejagoEntityEvents {
    public static final Predicate<LivingEntity> NO_SPINJITZU = (player -> !player.getData(MinejagoAttachmentTypes.SPINJITZU).canDoSpinjitzu() ||
            player.isCrouching() ||
            player.getVehicle() != null ||
            player.isVisuallySwimming() ||
            player.isUnderWater() ||
            player.isSleeping() ||
            player.isFreezing() ||
            player.isNoGravity() ||
            player.isInLava() ||
            player.isFallFlying() ||
            player.isBlocking() ||
            player.getActiveEffects().stream().anyMatch((mobEffectInstance -> mobEffectInstance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL)) ||
            player.isInWater() ||
            TommyLibServices.ENTITY.getPersistentData(player).getInt("OffGroundTicks") > 30 ||
            player.getData(MinejagoAttachmentTypes.FOCUS).getFocusLevel() < FocusConstants.SPINJITZU_LEVEL);

    public static final Predicate<LivingEntity> NO_MEDITATION = (player -> player.isCrouching() ||
            player.getVehicle() != null ||
            player.isVisuallySwimming() ||
            player.isSleeping() ||
            player.isNoGravity() ||
            player.isInLava() ||
            player.isFallFlying() ||
            (!TommyLibServices.ENTITY.getPersistentData(player).getString("StartPos").isEmpty() && !player.blockPosition().toString().equals(TommyLibServices.ENTITY.getPersistentData(player).getString("StartPos"))));

    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().tickRateManager().runsNormally()) {
            FocusData focusData = player.getData(MinejagoAttachmentTypes.FOCUS);
            focusData.tick(player);

            SpinjitzuData spinjitzu = player.getData(MinejagoAttachmentTypes.SPINJITZU);
            CompoundTag persistentData = TommyLibServices.ENTITY.getPersistentData(player);
            int waitTicks = persistentData.getInt("WaitTicks");
            if (player instanceof ServerPlayer serverPlayer) {
                ServerLevel level = serverPlayer.serverLevel();
                if ((level.getDifficulty() == Difficulty.PEACEFUL || serverPlayer.getAbilities().instabuild) && focusData.needsFocus()) {
                    focusData.setFocusLevel(FocusConstants.MAX_FOCUS);
                }

                int j = Mth.clamp(serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
                if (j >= 24000 && level.getGameRules().getBoolean(GameRules.RULE_DOINSOMNIA) && !serverPlayer.getAbilities().instabuild) {
                    focusData.addExhaustion(FocusConstants.EXHAUSTION_INSOMNIA);
                }

                if (!player.onGround()) {
                    persistentData.putInt("OffGroundTicks", persistentData.getInt("OffGroundTicks") + 1);
                    TommyLibServices.ENTITY.setPersistentData(player, persistentData, true);
                } else if (persistentData.getInt("OffGroundTicks") > 0) {
                    persistentData.putInt("OffGroundTicks", 0);
                    TommyLibServices.ENTITY.setPersistentData(player, persistentData, true);
                }

                if (spinjitzu.canDoSpinjitzu()) {
                    if (spinjitzu.active()) {
                        if (focusData.isMeditating()) {
                            focusData.stopMeditating();
                            TommyLibServices.NETWORK.sendToAllClients(new ClientboundStopAnimationPayload(serverPlayer.getUUID()), serverPlayer.getServer());
                        }

                        if (NO_SPINJITZU.test(serverPlayer)) {
                            stopSpinjitzu(spinjitzu, serverPlayer, !serverPlayer.isCrouching());
                            return;
                        }
                        focusData.addExhaustion(FocusConstants.EXHAUSTION_SPINJITZU);
                        if (player.tickCount % 20 == 0) {
                            level.playSound(null, serverPlayer.blockPosition(), MinejagoSoundEvents.SPINJITZU_ACTIVE.get(), SoundSource.PLAYERS);
                            level.gameEvent(serverPlayer, MinejagoGameEvents.SPINJITZU, serverPlayer.blockPosition());
                        }
                        Power power = player.level().holderOrThrow(player.getData(MinejagoAttachmentTypes.POWER).power()).value();
                        if (power.getBorderParticle() != null) {
                            MinejagoParticleUtils.renderNormalSpinjitzuBorder(power.getBorderParticle(), serverPlayer, 4, false);
                        }
                    }
                } else if (spinjitzu.active()) {
                    stopSpinjitzu(spinjitzu, serverPlayer, true);
                }

                if (focusData.isMeditating()) {
                    if (NO_MEDITATION.test(serverPlayer)) {
                        focusData.stopMeditating();
                        TommyLibServices.NETWORK.sendToAllClients(new ClientboundStopMeditationPayload(serverPlayer.getUUID(), true), serverPlayer.getServer());
                    }

                    if ((focusData.isMegaMeditating() && player.tickCount % 200 == 0) || (focusData.isNormalMeditating() && player.tickCount % 60 == 0)) {
                        BlockPos playerPos = player.blockPosition();
                        Biome biome = level.getBiome(playerPos).value();
                        AtomicDouble i = new AtomicDouble(1);
                        Weather weather = Weather.CLEAR;
                        Biome.Precipitation precipitation = biome.getPrecipitationAt(playerPos, level.getSeaLevel());
                        if (level.isThundering()) {
                            if (precipitation == Biome.Precipitation.SNOW) {
                                weather = Weather.THUNDER_SNOW;
                            } else if (precipitation == Biome.Precipitation.RAIN) weather = Weather.THUNDER_RAIN;
                        } else if (level.isRaining()) {
                            if (precipitation == Biome.Precipitation.SNOW) {
                                weather = Weather.SNOW;
                            } else if (precipitation == Biome.Precipitation.RAIN) weather = Weather.RAIN;
                        }
                        i.set(WorldFocusModifiers.applyModifier((int) level.getDayTime(), weather, playerPos.getY(), TeapotBlock.getBiomeTemperature(level, playerPos), i.get()));
                        i.set(ResourceKeyFocusModifiers.applyModifier(level.getBiome(playerPos).unwrapKey().orElseThrow(), i.get()));
                        i.set(ResourceKeyFocusModifiers.applyModifier(level.dimension(), i.get()));
                        serverPlayer.serverLevel().structureManager().getAllStructuresAt(playerPos).keySet().forEach(structure -> i.set(ResourceKeyFocusModifiers.applyModifier(level.registryAccess().lookupOrThrow(Registries.STRUCTURE).getResourceKey(structure).orElseThrow(), i.get())));
                        Stream<BlockState> blocks = level.getBlockStates(player.getBoundingBox().inflate(4));
                        blocks.forEach(blockState -> i.set(BlockStateFocusModifiers.applyModifier(blockState, i.get())));
                        serverPlayer.getActiveEffects().forEach(mobEffectInstance -> i.set(ResourceKeyFocusModifiers.applyModifier(mobEffectInstance.getEffect().unwrapKey().orElseThrow(), i.get()) * (mobEffectInstance.getAmplifier() + 1)));
                        List<Entity> entities = level.getEntities(serverPlayer, serverPlayer.getBoundingBox().inflate(4));
                        entities.forEach(entity -> {
                            if (entity instanceof ItemEntity item) {
                                i.set(ItemStackFocusModifiers.applyModifier(item.getItem(), i.get()));
                            } else if (entity instanceof ItemFrame itemFrame) {
                                i.set(ItemStackFocusModifiers.applyModifier(itemFrame.getItem(), i.get()));
                            } else {
                                i.set(EntityFocusModifiers.applyModifier(entity, i.get()));
                            }
                        });
                        serverPlayer.getInventory().items.forEach(stack -> i.set(ItemStackFocusModifiers.applyModifier(stack, i.get())));
                        focusData.meditate(focusData.isMegaMeditating(), (int) i.getAndSet(0), FocusConstants.FOCUS_SATURATION_NORMAL);
                    }

                    if (focusData.canMegaMeditate(serverPlayer)) {
                        if (!focusData.isMegaMeditating()) {
                            focusData.startMegaMeditating();
                            serverPlayer.refreshDimensions();
                            TommyLibServices.NETWORK.sendToAllClients(new ClientboundStartMegaMeditationPayload(player.getUUID()), level.getServer());
                        }
                    } else if (focusData.isMegaMeditating()) {
                        focusData.startMeditating();
                        serverPlayer.refreshDimensions();
                        TommyLibServices.NETWORK.sendToAllClients(new ClientboundStartMeditationPayload(player.getUUID()), level.getServer());
                    }
                } else if (TommyLibServices.ENTITY.getPersistentData(serverPlayer).contains("StartPos")) {
                    TommyLibServices.ENTITY.getPersistentData(serverPlayer).remove("StartPos");
                    TommyLibServices.ENTITY.setPersistentData(serverPlayer, persistentData, true);
                }

                if (MinejagoLevelUtils.isGoldenWeaponsMapHolderNearby(serverPlayer, SkulkinRaid.PAINTING_RADIUS_BUFFER)) {
                    ((SkulkinRaidsHolder) level).getSkulkinRaids().createOrExtendSkulkinRaid(serverPlayer);
                }
            } else {
                int startTicks = persistentData.getInt(ClientboundStartSpinjitzuPayload.KEY_SPINJITZUSTARTTICKS);
                if (startTicks > 0)
                    persistentData.putInt(ClientboundStartSpinjitzuPayload.KEY_SPINJITZUSTARTTICKS, startTicks - 1);
                if (waitTicks > 0) {
                    persistentData.putInt("WaitTicks", --waitTicks);
                } else if (MinejagoKeyMappings.ACTIVATE_SPINJITZU.isDown() && !focusData.isMeditating()) {
                    if (spinjitzu.active()) {
                        TommyLibServices.NETWORK.sendToServer(ServerboundStopSpinjitzuPayload.INSTANCE);
                    } else if (!NO_SPINJITZU.test(player)) {
                        TommyLibServices.NETWORK.sendToServer(ServerboundStartSpinjitzuPayload.INSTANCE);
                    }
                    persistentData.putInt("WaitTicks", 10);
                } else if (MinejagoKeyMappings.MEDITATE.isDown() && !spinjitzu.active()) {
                    if (focusData.isMeditating()) {
                        focusData.stopMeditating();
                        TommyLibServices.NETWORK.sendToServer(new ServerboundStopMeditationPayload(false));
                    } else if (!NO_MEDITATION.test(player)) {
                        focusData.startMeditating();
                        TommyLibServices.NETWORK.sendToServer(ServerboundStartMeditationPayload.INSTANCE);
                    }
                    persistentData.putInt("WaitTicks", 10);
                } else if (player.isShiftKeyDown()) {
                    if (spinjitzu.active()) {
                        TommyLibServices.NETWORK.sendToServer(ServerboundStopSpinjitzuPayload.INSTANCE);
                    }
                    if (focusData.isMeditating()) {
                        focusData.stopMeditating();
                        TommyLibServices.NETWORK.sendToServer(new ServerboundStopMeditationPayload(false));
                    }
                    persistentData.putInt("WaitTicks", 10);
                }
                TommyLibServices.ENTITY.setPersistentData(player, persistentData, false);
            }
        }
    }

    public static void onServerPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        for (ServerPlayer serverPlayer : ((ServerLevel) player.level()).getPlayers(serverPlayer -> true)) {
            TommyLibServices.NETWORK.sendToAllClients(ClientboundRefreshVipDataPayload.INSTANCE, serverPlayer.getServer());
        }
    }

    public static void onLivingTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            if (!livingEntity.level().isClientSide) {
                int offGroundTicks = TommyLibServices.ENTITY.getPersistentData(livingEntity).getInt("OffGroundTicks");
                SkillDataSet data = livingEntity.getData(MinejagoAttachmentTypes.SKILL);
                if (livingEntity.isSprinting()) {
                    data.addPractice(livingEntity, MinejagoSkills.AGILITY, 1 / 20f);
                }
                if (offGroundTicks > 0 && offGroundTicks <= 30) {
                    data.addPractice(livingEntity, MinejagoSkills.AGILITY, 1 / 20f);
                }
                if (livingEntity.isSteppingCarefully() && !livingEntity.level().getEntities(livingEntity, livingEntity.getBoundingBox().inflate(16)).isEmpty()) {
                    data.addPractice(livingEntity, MinejagoSkills.STEALTH, 1 / 20f);
                }

                boolean dirty = data.isDirty();

                if (dirty) {
                    HashMultimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
                    modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(Minejago.modLoc("agility_modifier"), 0.1 * (data.get(MinejagoSkills.AGILITY).level()), AttributeModifier.Operation.ADD_VALUE));
                    modifiers.put(Attributes.JUMP_STRENGTH, new AttributeModifier(Minejago.modLoc("agility_modifier"), 0.1 * (data.get(MinejagoSkills.AGILITY).level()), AttributeModifier.Operation.ADD_VALUE));
                    modifiers.put(Attributes.SNEAKING_SPEED, new AttributeModifier(Minejago.modLoc("stealth_modifier"), 0.1 * (data.get(MinejagoSkills.STEALTH).level()), AttributeModifier.Operation.ADD_VALUE));
                    livingEntity.getAttributes().addTransientAttributeModifiers(modifiers);
                    data.setDirty(false);
                }

                boolean emptyHanded = livingEntity.getMainHandItem().isEmpty();
                boolean toolHanded = livingEntity.getMainHandItem().is(ConventionalItemTags.TOOLS);
                if (dirty || emptyHanded && !livingEntity.getAttributes().hasModifier(Attributes.MINING_EFFICIENCY, Minejago.modLoc("dexterity_modifier")) || toolHanded && !livingEntity.getAttributes().hasModifier(Attributes.MINING_EFFICIENCY, Minejago.modLoc("tool_proficiency_modifier"))) {
                    HashMultimap<Holder<Attribute>, AttributeModifier> emptyHandModifiers = HashMultimap.create();
                    emptyHandModifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Minejago.modLoc("dexterity_modifier"), 0.1 * (data.get(MinejagoSkills.DEXTERITY).level()), AttributeModifier.Operation.ADD_VALUE));
                    emptyHandModifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(Minejago.modLoc("dexterity_modifier"), 0.1 * (data.get(MinejagoSkills.DEXTERITY).level()), AttributeModifier.Operation.ADD_VALUE));
                    emptyHandModifiers.put(Attributes.MINING_EFFICIENCY, new AttributeModifier(Minejago.modLoc("dexterity_modifier"), 0.1 * (data.get(MinejagoSkills.DEXTERITY).level()), AttributeModifier.Operation.ADD_VALUE));

                    HashMultimap<Holder<Attribute>, AttributeModifier> toolModifiers = HashMultimap.create();
                    toolModifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Minejago.modLoc("tool_proficiency_modifier"), 0.1 * (data.get(MinejagoSkills.TOOL_PROFICIENCY).level()), AttributeModifier.Operation.ADD_VALUE));
                    toolModifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(Minejago.modLoc("tool_proficiency_modifier"), 0.1 * (data.get(MinejagoSkills.TOOL_PROFICIENCY).level()), AttributeModifier.Operation.ADD_VALUE));
                    toolModifiers.put(Attributes.MINING_EFFICIENCY, new AttributeModifier(Minejago.modLoc("tool_proficiency_modifier"), 0.1 * (data.get(MinejagoSkills.TOOL_PROFICIENCY).level()), AttributeModifier.Operation.ADD_VALUE));

                    if (emptyHanded) {
                        livingEntity.getAttributes().addTransientAttributeModifiers(emptyHandModifiers);
                        livingEntity.getAttributes().removeAttributeModifiers(toolModifiers);
                    } else {
                        livingEntity.getAttributes().addTransientAttributeModifiers(toolModifiers);
                        livingEntity.getAttributes().removeAttributeModifiers(emptyHandModifiers);
                    }
                }
            }

            if (livingEntity instanceof Player player) {
                Inventory i = player.getInventory();

                if (i.contains(MinejagoItems.SCYTHE_OF_QUAKES.get().getDefaultInstance()) /*&& i.contains(MinejagoElements.SHURIKENS_OF_ICE.get().getDefaultInstance()) && i.contains(MinejagoElements.NUNCHUCKS_OF_LIGHTNING.get().getDefaultInstance()) && i.contains(MinejagoElements.SWORD_OF_FIRE.get().getDefaultInstance())*/) {
                    GoldenWeaponItem.overload(livingEntity);
                }
            } else if (livingEntity instanceof InventoryCarrier carrier) {
                boolean f = false, e = false, i = false, l = false;

                for (ItemStack stack : livingEntity.getAllSlots()) {
                    if (stack.getItem() == MinejagoItems.SCYTHE_OF_QUAKES.get()) {
                        e = true;
                    }
                    /*else if (stack.getItem() == MinejagoElements.SWORD_OF_FIRE.get())
                    {
                    f = true;
                    }
                    else if (stack.getItem() == MinejagoElements.NUNCHUCKS_OF_LIGHTNING.get())
                    {
                    l = true;
                    }
                    else if (stack.getItem() == MinejagoElements.SHURIKENS_OF_ICE.get())
                    {
                    i = true;
                    }*/
                }

                SimpleContainer inventory = carrier.getInventory();

                if ((inventory.hasAnyOf(Set.of(MinejagoItems.SCYTHE_OF_QUAKES.get())) || e) /*&& (inventory.hasAnyOf(Set.of(MinejagoElements.SWORD_OF_FIRE.get())) || f) && (inventory.hasAnyOf(Set.of(MinejagoElements.NUNCHUCKS_OF_LIGHTNING.get())) || l) && (inventory.hasAnyOf(Set.of(MinejagoElements.SHURIKENS_OF_ICE.get())) || i)*/) {
                    GoldenWeaponItem.overload(livingEntity);
                }
            } else {
                boolean f = false, e = false, i = false, l = false;

                for (ItemStack stack : livingEntity.getAllSlots()) {
                    if (stack.getItem() == MinejagoItems.SCYTHE_OF_QUAKES.get()) {
                        e = true;
                    }
                    /*else if (stack.getItem() == MinejagoElements.SWORD_OF_FIRE.get())
                    {
                    f = true;
                    }
                    else if (stack.getItem() == MinejagoElements.NUNCHUCKS_OF_LIGHTNING.get())
                    {
                    l = true;
                    }
                    else if (stack.getItem() == MinejagoElements.SHURIKENS_OF_ICE.get())
                    {
                    i = true;
                    }*/
                }

                if (f && e && i && l) GoldenWeaponItem.overload(livingEntity);
            }
        }
    }

    public static void onPlayerEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        InteractionHand hand = event.getHand();
        Entity entity = event.getTarget();
        CompoundTag persistentData = TommyLibServices.ENTITY.getPersistentData(entity);
        if (level instanceof ServerLevel serverLevel && hand == InteractionHand.MAIN_HAND && entity instanceof Painting painting && painting.getVariant().is(MinejagoPaintingVariants.FOUR_WEAPONS) && !persistentData.getBoolean("MapTaken")) {
            player.addItem(MinejagoItemUtils.createCaveOfDespairMap(serverLevel, player));
            if (!player.isCreative()) {
                persistentData.putBoolean("MapTaken", true);
                persistentData.putBoolean("MapTakenByPlayer", true);
                TommyLibServices.ENTITY.setPersistentData(entity, persistentData, true);
            }
        }
    }

    public static void stopSpinjitzu(SpinjitzuData spinjitzu, ServerPlayer serverPlayer, boolean fail) {
        if (spinjitzu.active()) {
            new SpinjitzuData(spinjitzu.unlocked(), false).save(serverPlayer, true);
            TommyLibServices.NETWORK.sendToAllClients(new ClientboundStopSpinjitzuPayload(serverPlayer.getUUID(), fail), serverPlayer.getServer());
            AttributeInstance speed = serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speed != null && speed.hasModifier(SpinjitzuData.SPEED_MODIFIER))
                speed.removeModifier(SpinjitzuData.SPEED_MODIFIER);
            AttributeInstance kb = serverPlayer.getAttribute(Attributes.ATTACK_KNOCKBACK);
            if (kb != null && kb.hasModifier(SpinjitzuData.KNOCKBACK_MODIFIER))
                kb.removeModifier(SpinjitzuData.KNOCKBACK_MODIFIER);
            serverPlayer.level().playSound(null, serverPlayer.blockPosition(), MinejagoSoundEvents.SPINJITZU_STOP.get(), SoundSource.PLAYERS);
        }
    }

    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        for (EntityType<? extends LivingEntity> type : MinejagoEntityTypes.getAllAttributes().keySet()) {
            event.put(type, MinejagoEntityTypes.getAllAttributes().get(type));
        }
    }

    public static void onSpawnPlacementsRegister(RegisterSpawnPlacementsEvent event) {
        event.register(MinejagoEntityTypes.ZANE.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR_WG, Zane::checkZaneSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(MinejagoEntityTypes.COLE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Character::checkCharacterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
    }

    public static void onLivingKnockBack(LivingKnockBackEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp && event.getStrength() > 2) {
            MinejagoEntityEvents.stopSpinjitzu(sp.getData(MinejagoAttachmentTypes.SPINJITZU), sp, true);
        }
    }

    public static void onAddBlocksToBlockEntityType(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.BRUSHABLE_BLOCK, MinejagoBlocks.SUSPICIOUS_RED_SAND.get());

        event.modify(BlockEntityType.SIGN, MinejagoBlocks.ENCHANTED_WOOD_SET.sign().get());
        event.modify(BlockEntityType.SIGN, MinejagoBlocks.ENCHANTED_WOOD_SET.wallSign().get());
        event.modify(BlockEntityType.HANGING_SIGN, MinejagoBlocks.ENCHANTED_WOOD_SET.hangingSign().get());
        event.modify(BlockEntityType.HANGING_SIGN, MinejagoBlocks.ENCHANTED_WOOD_SET.wallHangingSign().get());
    }

    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide && event.getEntity() instanceof LivingEntity livingEntity) {
            livingEntity.getData(MinejagoAttachmentTypes.SPINJITZU).save(livingEntity, true);
            livingEntity.getData(MinejagoAttachmentTypes.POWER).save(livingEntity, true);
        }
    }

    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.getEntity().level().isClientSide) {
            event.getEntity().getData(MinejagoAttachmentTypes.SPINJITZU).save(event.getEntity(), true);
            event.getEntity().getData(MinejagoAttachmentTypes.POWER).save(event.getEntity(), true);
        }
    }

    public static void onEntitySize(EntityEvent.Size event) {
        if (event.getEntity() instanceof LivingEntity livingEntity && livingEntity.getData(MinejagoAttachmentTypes.FOCUS).isNormalMeditating()) {
            event.setNewSize(event.getOldSize().scale(1, 0.7f));
        }
    }

    public static void onLivingVisibility(LivingEvent.LivingVisibilityEvent event) {
        if (MinejagoArmors.isWearingFullGi(event.getEntity()))
            event.modifyVisibility(0.8);
        SkillData data = event.getEntity().getData(MinejagoAttachmentTypes.SKILL).get(MinejagoSkills.STEALTH);
        if (data.level() > 0)
            event.modifyVisibility(0.1 * data.level());
    }

    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer().getMainHandItem().isEmpty())
            event.getPlayer().getData(MinejagoAttachmentTypes.SKILL).addPractice(event.getPlayer(), MinejagoSkills.DEXTERITY, event.getState().getBlock().defaultDestroyTime());
        else if (event.getPlayer().getMainHandItem().isCorrectToolForDrops(event.getState()))
            event.getPlayer().getData(MinejagoAttachmentTypes.SKILL).addPractice(event.getPlayer(), MinejagoSkills.TOOL_PROFICIENCY, event.getState().getBlock().defaultDestroyTime());
    }

    public static void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getSource().getDirectEntity() instanceof LivingEntity attacker) {
            SkillDataSet data = attacker.getData(MinejagoAttachmentTypes.SKILL);
            if (attacker.getMainHandItem().isEmpty())
                data.addPractice(attacker, MinejagoSkills.DEXTERITY, event.getAmount() / (data.get(MinejagoSkills.DEXTERITY).level() + 1));
            else if (attacker.getMainHandItem().is(ConventionalItemTags.MELEE_WEAPON_TOOLS))
                data.addPractice(attacker, MinejagoSkills.TOOL_PROFICIENCY, event.getAmount() / (data.get(MinejagoSkills.TOOL_PROFICIENCY).level() + 1));
        }
    }

    public static void onModifyCustomSpawners(ModifyCustomSpawnersEvent event) {
        event.addCustomSpawner(new SkulkinArmySpawner());
    }
}
