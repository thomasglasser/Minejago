package dev.thomasglasser.minejago.world.entity;

import com.google.common.collect.HashMultimap;
import com.google.common.util.concurrent.AtomicDouble;
import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.minejago.client.MinejagoKeyMappings;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleUtils;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.network.ClientboundRefreshVipDataPayload;
import dev.thomasglasser.minejago.network.ClientboundStartMeditationPayload;
import dev.thomasglasser.minejago.network.ClientboundStartMegaMeditationPayload;
import dev.thomasglasser.minejago.network.ClientboundStartSpinjitzuPayload;
import dev.thomasglasser.minejago.network.ClientboundStopMeditationPayload;
import dev.thomasglasser.minejago.network.ClientboundStopSpinjitzuPayload;
import dev.thomasglasser.minejago.network.ServerboundAdjustShadowScalePayload;
import dev.thomasglasser.minejago.network.ServerboundRecallClonesPayload;
import dev.thomasglasser.minejago.network.ServerboundStartMeditationPayload;
import dev.thomasglasser.minejago.network.ServerboundStartShadowFormPayload;
import dev.thomasglasser.minejago.network.ServerboundStartSpinjitzuPayload;
import dev.thomasglasser.minejago.network.ServerboundStopMeditationPayload;
import dev.thomasglasser.minejago.network.ServerboundStopShadowFormPayload;
import dev.thomasglasser.minejago.network.ServerboundStopSpinjitzuPayload;
import dev.thomasglasser.minejago.network.ServerboundSummonClonePayload;
import dev.thomasglasser.minejago.network.ServerboundSwitchDimensionPayload;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.tags.ElementTags;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.tags.MinejagoStructureTags;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import dev.thomasglasser.minejago.world.entity.character.Character;
import dev.thomasglasser.minejago.world.entity.character.Zane;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.minejago.world.entity.element.tornadoofcreation.TornadoOfCreationConfig;
import dev.thomasglasser.minejago.world.entity.shadow.ShadowClone;
import dev.thomasglasser.minejago.world.entity.shadow.ShadowSource;
import dev.thomasglasser.minejago.world.entity.skill.Skill;
import dev.thomasglasser.minejago.world.entity.skulkin.Skulkin;
import dev.thomasglasser.minejago.world.entity.skulkin.SkulkinHorse;
import dev.thomasglasser.minejago.world.entity.skulkin.Spykor;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaidsHolder;
import dev.thomasglasser.minejago.world.focus.FocusConstants;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.focus.modifier.BlockFocusModifier;
import dev.thomasglasser.minejago.world.focus.modifier.EntityFocusModifier;
import dev.thomasglasser.minejago.world.focus.modifier.ItemFocusModifier;
import dev.thomasglasser.minejago.world.focus.modifier.LocationFocusModifier;
import dev.thomasglasser.minejago.world.focus.modifier.Weather;
import dev.thomasglasser.minejago.world.focus.modifier.WorldFocusModifier;
import dev.thomasglasser.minejago.world.item.MinejagoItemUtils;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.level.MinejagoLevelUtils;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.minejago.world.level.gameevent.MinejagoGameEvents;
import dev.thomasglasser.minejago.world.level.storage.ShadowSourceData;
import dev.thomasglasser.minejago.world.level.storage.SkillData;
import dev.thomasglasser.minejago.world.level.storage.SkillDataSet;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import dev.thomasglasser.minejago.world.level.storage.TornadoOfCreationData;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import dev.thomasglasser.tommylib.api.tags.ConventionalItemTags;
import dev.thomasglasser.tommylib.api.world.entity.EntityUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.SharedConstants;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class MinejagoEntityEvents {
    public static final String KEY_WAIT_TICKS = "WaitTicks";
    public static final String KEY_OFF_GROUND_TICKS = "OffGroundTicks";
    public static final String KEY_START_POS = "StartPos";
    public static final String KEY_IS_IN_MONASTERY_OF_SPINJITZU = "IsInMonasteryOfSpinjitzu";
    public static final String KEY_IS_IN_CAVE_OF_DESPAIR = "IsInCaveOfDespair";
    public static final String KEY_IS_IN_TORNADO_OF_CREATION = "IsInTornadoOfCreation";
    public static final String KEY_TORNADO_OF_CREATION_SECONDS = "TornadoOfCreationSeconds";

    public static final Predicate<LivingEntity> NO_SPINJITZU = (entity -> !entity.getData(MinejagoAttachmentTypes.SPINJITZU).canDoSpinjitzu() ||
            entity.isCrouching() ||
            entity.getVehicle() != null ||
            entity.isVisuallySwimming() ||
            entity.isUnderWater() ||
            entity.isSleeping() ||
            entity.isFreezing() ||
            entity.isNoGravity() ||
            entity.isInLava() ||
            entity.isFallFlying() ||
            entity.isBlocking() ||
            entity.getActiveEffects().stream().anyMatch((mobEffectInstance -> mobEffectInstance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL)) ||
            entity.isInWater() ||
//            TommyLibServices.ENTITY.getPersistentData(entity).getInt(KEY_OFF_GROUND_TICKS) > 30 ||
            entity.getData(MinejagoAttachmentTypes.SHADOW_SOURCE).isPresent() ||
            entity.getData(MinejagoAttachmentTypes.FOCUS).getFocusLevel() < FocusConstants.SPINJITZU_LEVEL);

    public static final Predicate<LivingEntity> NO_MEDITATION = (entity -> entity.isCrouching() ||
            entity.getDeltaMovement().distanceTo(Vec3.ZERO) > 0.08 ||
            entity.getVehicle() != null ||
            entity.isVisuallySwimming() ||
            entity.isSleeping() ||
            entity.isFreezing() ||
            entity.isNoGravity() ||
            entity.isInLava() ||
            entity.isFallFlying() ||
            entity.isBlocking() ||
            entity.getActiveEffects().stream().anyMatch((mobEffectInstance -> mobEffectInstance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL)) ||
//            TommyLibServices.ENTITY.getPersistentData(entity).getInt(KEY_OFF_GROUND_TICKS) > 0 ||
            entity.getData(MinejagoAttachmentTypes.SPINJITZU).active() ||
            entity.getData(MinejagoAttachmentTypes.SHADOW_SOURCE).isPresent() /*||*/
    /*(!TommyLibServices.ENTITY.getPersistentData(entity).getString(KEY_START_POS).isEmpty() && !entity.blockPosition().toString().equals(TommyLibServices.ENTITY.getPersistentData(entity).getString(KEY_START_POS)))*/);

    public static final Predicate<LivingEntity> NO_SHADOW_FORM = (entity -> entity.getData(MinejagoAttachmentTypes.FOCUS).getFocusLevel() < FocusConstants.SHADOW_FORM_LEVEL);

    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().tickRateManager().runsNormally()) {
            FocusData focusData = player.getData(MinejagoAttachmentTypes.FOCUS);
            SpinjitzuData spinjitzu = player.getData(MinejagoAttachmentTypes.SPINJITZU);
            Optional<ShadowSourceData> shadowSource = player.getData(MinejagoAttachmentTypes.SHADOW_SOURCE);
            CompoundTag persistentData = /*TommyLibServices.ENTITY.getPersistentData(player).copy()*/new CompoundTag();

            focusData.tick(player);

            int waitTicks = persistentData.getInt(KEY_WAIT_TICKS);
            if (player instanceof ServerPlayer serverPlayer) {
                ServerLevel serverLevel = serverPlayer.serverLevel();
                if ((serverLevel.getDifficulty() == Difficulty.PEACEFUL || serverPlayer.getAbilities().instabuild) && focusData.needsFocus()) {
                    focusData.setFocusLevel(FocusConstants.MAX_FOCUS);
                }

                int j = Mth.clamp(serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
                if (j >= 24000 && serverLevel.getGameRules().getBoolean(GameRules.RULE_DOINSOMNIA) && !serverPlayer.getAbilities().instabuild) {
                    focusData.addExhaustion(FocusConstants.EXHAUSTION_INSOMNIA * ((float) j / 24000f));
                }

                if (!player.onGround()) {
                    persistentData.putInt(KEY_OFF_GROUND_TICKS, persistentData.getInt(KEY_OFF_GROUND_TICKS) + 1);
                } else if (persistentData.getInt(KEY_OFF_GROUND_TICKS) > 0) {
                    persistentData.remove(KEY_OFF_GROUND_TICKS);
//                    TommyLibServices.ENTITY.removePersistentData(serverPlayer, true, KEY_OFF_GROUND_TICKS);
                }

                if (spinjitzu.active()) {
                    if (spinjitzu.canDoSpinjitzu()) {
                        if (NO_SPINJITZU.test(serverPlayer)) {
                            stopSpinjitzu(spinjitzu, serverPlayer, !serverPlayer.isCrouching());
                            return;
                        }
                        focusData.addExhaustion(FocusConstants.EXHAUSTION_SPINJITZU);
                        if (player.tickCount % 20 == 0) {
                            serverLevel.playSound(null, serverPlayer.blockPosition(), MinejagoSoundEvents.SPINJITZU_ACTIVE.get(), SoundSource.PLAYERS);
                            serverLevel.gameEvent(serverPlayer, MinejagoGameEvents.SPINJITZU, serverPlayer.blockPosition());

                            // Tornado of Creation
                            Set<Entity> spinners = new HashSet<>(serverLevel.getEntitiesOfClass(Entity.class, player.getBoundingBox().inflate(1), entity -> entity.getData(MinejagoAttachmentTypes.SPINJITZU).active()));
                            if (persistentData.getBoolean(KEY_IS_IN_TORNADO_OF_CREATION)) {
                                Optional<TornadoOfCreationData> optionalData = player.getData(MinejagoAttachmentTypes.TORNADO_OF_CREATION);
                                if (optionalData.isPresent()) {
                                    TornadoOfCreationData tornadoData = optionalData.get();
                                    Set<Entity> originalParticipants = tornadoData.originalParticipants();
                                    if (!spinners.containsAll(originalParticipants) || shouldTornadoOfCreationFail(spinners, serverLevel)) {
                                        Set<Entity> all = new HashSet<>(spinners);
                                        all.addAll(originalParticipants);
                                        failTornadoOfCreation(serverLevel, player, all);
                                    } else {
                                        // Handle progress
                                        int seconds = persistentData.getInt(KEY_TORNADO_OF_CREATION_SECONDS) + 1;
                                        persistentData.putInt(KEY_TORNADO_OF_CREATION_SECONDS, seconds);
                                        if (tornadoData.blocksRemaining().isEmpty() && tornadoData.entitiesRemaining().isEmpty()) {
                                            // Place structure
                                            Optional<Structure> optionalStructure = WeightedRandom.getRandomItem(serverLevel.random, tornadoData.structures()).map(entry -> entry.structure().value());
                                            if (optionalStructure.isPresent()) {
                                                Structure structure = optionalStructure.get();
                                                ChunkGenerator chunkgenerator = serverLevel.getChunkSource().getGenerator();
                                                BlockPos pos = player.blockPosition().offset(serverLevel.random.nextInt(-8, 8), 0, serverLevel.random.nextInt(-8, 8));
                                                StructureStart structurestart = structure.generate(serverLevel.registryAccess(), chunkgenerator, chunkgenerator.getBiomeSource(), serverLevel.getChunkSource().randomState(), serverLevel.getStructureManager(), serverLevel.getSeed(), new ChunkPos(pos), 0, serverLevel, (p_214580_) -> true);
                                                if (!structurestart.isValid())
                                                    failTornadoOfCreation(serverLevel, player, spinners);
                                                else {
                                                    BoundingBox boundingbox = structurestart.getBoundingBox();
                                                    ChunkPos start = new ChunkPos(SectionPos.blockToSectionCoord(boundingbox.minX()), SectionPos.blockToSectionCoord(boundingbox.minZ()));
                                                    ChunkPos end = new ChunkPos(SectionPos.blockToSectionCoord(boundingbox.maxX()), SectionPos.blockToSectionCoord(boundingbox.maxZ()));
                                                    ChunkPos.rangeClosed(start, end).forEach((chunkPos) -> structurestart.placeInChunk(serverLevel, serverLevel.structureManager(), chunkgenerator, serverLevel.getRandom(), new BoundingBox(chunkPos.getMinBlockX(), serverLevel.getMinBuildHeight(), chunkPos.getMinBlockZ(), chunkPos.getMaxBlockX(), serverLevel.getMaxBuildHeight(), chunkPos.getMaxBlockZ()), chunkPos));
                                                    stopTornadoOfCreation(spinners);
                                                }
                                            } else
                                                failTornadoOfCreation(serverLevel, player, spinners);
                                        } else if (seconds > MinejagoServerConfig.get().timeout.getAsInt()) {
                                            failTornadoOfCreation(serverLevel, player, spinners);
                                        } else {
                                            AABB pickupRange = player.getBoundingBox().inflate(MinejagoServerConfig.get().resourceAbsorbDistance.getAsInt());
                                            List<Pair<BlockPredicate, BlockPos>> blocks = new ArrayList<>();
                                            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
                                            for (int x = (int) pickupRange.minX; x < pickupRange.maxX; x++) {
                                                mutable.setX(x);
                                                for (int y = (int) pickupRange.minY; y < pickupRange.maxY; y++) {
                                                    mutable.setY(y);
                                                    for (int z = (int) pickupRange.minZ; z < pickupRange.maxZ; z++) {
                                                        mutable.setZ(z);
                                                        for (BlockPredicate predicate : tornadoData.blocksRemaining().keySet()) {
                                                            if (predicate.matches(serverLevel, mutable)) {
                                                                blocks.add(Pair.of(predicate, mutable.immutable()));
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            List<Pair<EntityPredicate, Entity>> entities = new ArrayList<>();
                                            for (Entity entity : serverLevel.getEntities(player, pickupRange, entity -> !spinners.contains(entity))) {
                                                for (EntityPredicate predicate : tornadoData.entitiesRemaining().keySet()) {
                                                    if (predicate.matches(serverPlayer, entity)) {
                                                        entities.add(Pair.of(predicate, entity));
                                                        break;
                                                    }
                                                }
                                            }
                                            for (int i = 0; i < spinners.size() * MinejagoServerConfig.get().resourcesAbsorbedPerSecond.getAsInt(); i++) {
                                                if (serverLevel.random.nextBoolean() && !blocks.isEmpty()) {
                                                    // Take block
                                                    Pair<BlockPredicate, BlockPos> taken = blocks.get(serverLevel.random.nextInt(blocks.size()));
                                                    serverLevel.destroyBlock(taken.getSecond(), false);
                                                    tornadoData.absorbedBlock(taken.getFirst());
                                                } else if (!entities.isEmpty()) {
                                                    // Take entity
                                                    Pair<EntityPredicate, Entity> taken = entities.get(serverLevel.random.nextInt(entities.size()));
                                                    taken.getSecond().discard();
                                                    tornadoData.absorbedEntity(taken.getFirst());
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                Set<Holder<Element>> elements = spinners.stream().map(entity -> serverLevel.holderOrThrow(entity.getData(MinejagoAttachmentTypes.ELEMENT).element())).collect(Collectors.toSet());
                                boolean hasAllCreation = true;
                                for (Holder<Element> element : serverLevel.holderLookup(MinejagoRegistries.ELEMENT).get(ElementTags.ELEMENTS_OF_CREATION).orElseThrow()) {
                                    if (!elements.contains(element)) {
                                        hasAllCreation = false;
                                        break;
                                    }
                                }
                                if (hasAllCreation) {
                                    if (shouldTornadoOfCreationFail(spinners, serverLevel)) {
                                        failTornadoOfCreation(serverLevel, player, spinners);
                                    } else {
                                        AABB pickupRange = player.getBoundingBox().inflate(MinejagoServerConfig.get().resourceAbsorbDistance.getAsInt() * 2);
                                        List<BlockPos> blocks = new ArrayList<>();
                                        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
                                        for (int x = (int) pickupRange.minX; x < pickupRange.maxX; x++) {
                                            mutable.setX(x);
                                            for (int y = (int) pickupRange.minY; y < pickupRange.maxY; y++) {
                                                mutable.setY(y);
                                                for (int z = (int) pickupRange.minZ; z < pickupRange.maxZ; z++) {
                                                    mutable.setZ(z);
                                                    blocks.add(mutable.immutable());
                                                }
                                            }
                                        }
                                        List<Entity> entities = new ArrayList<>(serverLevel.getEntities(player, pickupRange, entity -> !spinners.contains(entity)));
                                        List<TornadoOfCreationConfig> valid = serverLevel.holderLookup(MinejagoRegistries.TORNADO_OF_CREATION_CONFIG).listElements().map(Holder::value).filter(config -> {
                                            for (TornadoOfCreationConfig.BlockRequirement blockRequirement : config.requiredBlocks()) {
                                                int matching = 0;
                                                for (BlockPos pos : blocks) {
                                                    if (blockRequirement.predicate().matches(serverLevel, pos))
                                                        matching++;
                                                }
                                                if (matching < blockRequirement.count())
                                                    return false;
                                            }
                                            for (TornadoOfCreationConfig.EntityRequirement entityRequirement : config.requiredEntities()) {
                                                int matching = 0;
                                                for (Entity entity : entities) {
                                                    if (entityRequirement.predicate().matches(serverPlayer, entity))
                                                        matching++;
                                                }
                                                if (matching < entityRequirement.count())
                                                    return false;
                                            }
                                            return true;
                                        }).toList();
                                        if (valid.isEmpty())
                                            failTornadoOfCreation(serverLevel, player, spinners);
                                        else {
                                            TornadoOfCreationConfig config = valid.get(serverLevel.random.nextInt(valid.size()));
                                            player.setData(MinejagoAttachmentTypes.TORNADO_OF_CREATION, Optional.of(TornadoOfCreationData.create(spinners, config)));
                                            persistentData.putInt(KEY_TORNADO_OF_CREATION_SECONDS, 0);
                                            CompoundTag tag = new CompoundTag();
                                            tag.putBoolean(KEY_IS_IN_TORNADO_OF_CREATION, true);
                                            for (Entity spinner : spinners) {
//                                                TommyLibServices.ENTITY.mergePersistentData(spinner, tag, true);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (player.getData(MinejagoAttachmentTypes.TORNADO_OF_CREATION).isEmpty())
                            player.level().holderOrThrow(player.getData(MinejagoAttachmentTypes.ELEMENT).element()).value().borderParticle().ifPresent(particle -> MinejagoParticleUtils.renderSpinjitzuBorder(particle, serverPlayer));
                    } else {
                        stopSpinjitzu(spinjitzu, serverPlayer, true);
                    }
                } else if (persistentData.getBoolean(KEY_IS_IN_TORNADO_OF_CREATION)) {
                    persistentData.remove(KEY_IS_IN_TORNADO_OF_CREATION);
//                    TommyLibServices.ENTITY.removePersistentData(serverPlayer, true, KEY_IS_IN_TORNADO_OF_CREATION);
                }

                ShadowSource source = shadowSource.map(data -> serverLevel.getServer().getLevel(data.level()) instanceof ServerLevel sourceLevel && sourceLevel.getEntity(data.uuid()) instanceof ShadowSource s ? s : null).orElse(null);
                boolean shadowMeditating = source != null && source.getData(MinejagoAttachmentTypes.FOCUS).isMeditating();
                if (focusData.isMeditating() || shadowMeditating) {
                    if (focusData.isMeditating() && NO_MEDITATION.test(serverPlayer)) {
                        focusData.stopMeditating();
                        TommyLibServices.NETWORK.sendToAllClients(new ClientboundStopMeditationPayload(serverPlayer.getUUID(), true), serverPlayer.getServer());
                    }

                    if ((shadowMeditating && player.tickCount % 20 == 0) || (focusData.isMegaMeditating() && player.tickCount % 200 == 0) || (focusData.isNormalMeditating() && player.tickCount % 60 == 0)) {
                        ServerLevel level = serverLevel;
                        BlockPos blockPos = player.blockPosition();
                        Vec3 pos = player.position();
                        if (shadowMeditating) {
                            level = serverLevel.getServer().getLevel(shadowSource.get().level()) instanceof ServerLevel sourceLevel ? sourceLevel : serverLevel;
                            blockPos = source.blockPosition();
                            pos = source.position();
                        }
                        Biome biome = level.getBiome(blockPos).value();
                        AtomicDouble i = new AtomicDouble(1);
                        Weather weather = Weather.CLEAR;
                        Biome.Precipitation precipitation = biome.getPrecipitationAt(blockPos);
                        if (level.isThundering()) {
                            if (precipitation == Biome.Precipitation.SNOW) {
                                weather = Weather.THUNDER_SNOW;
                            } else if (precipitation == Biome.Precipitation.RAIN) weather = Weather.THUNDER_RAIN;
                        } else if (level.isRaining()) {
                            if (precipitation == Biome.Precipitation.SNOW) {
                                weather = Weather.SNOW;
                            } else if (precipitation == Biome.Precipitation.RAIN) weather = Weather.RAIN;
                        }
                        i.set(WorldFocusModifier.checkAndApply(level, (int) level.getDayTime(), weather, blockPos.getY(), TeapotBlock.getBiomeTemperature(level, blockPos), i.get()));
                        i.set(LocationFocusModifier.checkAndApply(level, pos, i.get()));
                        Stream<BlockPos> blocks = BlockPos.betweenClosedStream((shadowMeditating ? source : player).getBoundingBox().inflate(4));
                        blocks.forEach(bP -> i.set(BlockFocusModifier.checkAndApply(serverLevel, bP, i.get())));
                        EntityFocusModifier.checkAndApply(level, pos, player, true, i.get());
                        List<Entity> entities = level.getEntities(serverPlayer, (shadowMeditating ? source : serverPlayer).getBoundingBox().inflate(4));
                        final ServerLevel finalLevel = level;
                        entities.forEach(entity -> {
                            if (entity instanceof ItemEntity item) {
                                i.set(ItemFocusModifier.checkAndApply(finalLevel, item.getItem(), i.get()));
                            } else if (entity instanceof ItemFrame itemFrame) {
                                i.set(ItemFocusModifier.checkAndApply(finalLevel, itemFrame.getItem(), i.get()));
                            } else {
                                i.set(EntityFocusModifier.checkAndApply(finalLevel, entity.position(), entity, false, i.get()));
                            }
                        });
                        Inventory inventory = serverPlayer.getInventory();
                        inventory.armor.forEach(stack -> i.set(ItemFocusModifier.checkAndApply(finalLevel, stack, i.get())));
                        inventory.items.forEach(stack -> i.set(ItemFocusModifier.checkAndApply(finalLevel, stack, i.get())));
                        inventory.offhand.forEach(stack -> i.set(ItemFocusModifier.checkAndApply(finalLevel, stack, i.get())));
                        focusData.meditate(shadowMeditating || focusData.isMegaMeditating(), (int) i.getAndSet(0), FocusConstants.FOCUS_SATURATION_NORMAL);
                    }

                    if (!shadowMeditating) {
                        if (focusData.canMegaMeditate(serverPlayer)) {
                            if (!focusData.isMegaMeditating()) {
                                focusData.startMegaMeditating();
                                serverPlayer.refreshDimensions();
                                TommyLibServices.NETWORK.sendToAllClients(new ClientboundStartMegaMeditationPayload(player.getUUID()), serverLevel.getServer());
                            }
                        } else if (focusData.isMegaMeditating()) {
                            focusData.startMeditating();
                            serverPlayer.refreshDimensions();
                            TommyLibServices.NETWORK.sendToAllClients(new ClientboundStartMeditationPayload(player.getUUID()), serverLevel.getServer());
                        }
                    }
                } else if (persistentData.contains(KEY_START_POS)) {
                    persistentData.remove(KEY_START_POS);
//                    TommyLibServices.ENTITY.removePersistentData(serverPlayer, true, KEY_START_POS);
                }

                if (shadowSource.isPresent()) {
                    if (source == null || NO_SHADOW_FORM.test(serverPlayer) || source.isInLava()/* || (!TommyLibServices.ENTITY.getPersistentData(source).getString(KEY_START_POS).isEmpty() && !source.blockPosition().toString().equals(TommyLibServices.ENTITY.getPersistentData(source).getString(KEY_START_POS)))*/) {
                        stopShadowForm(serverPlayer);
                    } else {
                        if (serverPlayer.noPhysics) {
                            recallShadowClones(serverPlayer);
                        }
                        if (player.getData(MinejagoAttachmentTypes.SHADOW_SOURCE).isPresent()) {
                            if (player.getLightLevelDependentMagicValue() < 0.1f && !player.getAbilities().flying) {
                                player.getAbilities().flying = true;
                                player.onUpdateAbilities();
                            }
                        }
                        focusData.addExhaustion(FocusConstants.EXHAUSTION_IN_SHADOW_FORM);
                        if (!player.getAbilities().flying)
                            focusData.addExhaustion(FocusConstants.EXHAUSTION_SHADOW_FORM_NORMAL_ABILITY);
                        if (!(player.level().dimension().equals(shadowSource.get().level())))
                            focusData.addExhaustion(FocusConstants.EXHAUSTION_SHADOW_FORM_NORMAL_ABILITY);
                        int count = 0;
                        for (ItemStack stack : player.getInventory().items) {
                            count += stack.getCount();
                        }
                        for (ItemStack stack : player.getAllSlots()) {
                            count += stack.getCount();
                        }
                        if (count > 0)
                            focusData.addExhaustion(FocusConstants.EXHAUSTION_SHADOW_FORM_HOLDING_ITEM * count);
                        int clones = player.getData(MinejagoAttachmentTypes.SHADOW_CLONES).size();
                        if (clones > 0)
                            focusData.addExhaustion(FocusConstants.EXHAUSTION_SHADOW_FORM_NORMAL_ABILITY * clones);
                    }
                }

                if (serverPlayer.tickCount % (SharedConstants.TICKS_PER_SECOND * 5) == 0) {
                    SkulkinRaidsHolder.of(serverLevel).minejago$getSkulkinRaids().checkRaidCenters(serverPlayer);
                    persistentData.putBoolean(KEY_IS_IN_CAVE_OF_DESPAIR, MinejagoLevelUtils.isStructureInRange(MinejagoStructureTags.CAVE_OF_DESPAIR, serverLevel, serverPlayer.blockPosition(), 64));
                    persistentData.putBoolean(KEY_IS_IN_MONASTERY_OF_SPINJITZU, MinejagoLevelUtils.isStructureInRange(MinejagoStructureTags.MONASTERY_OF_SPINJITZU, serverLevel, serverPlayer.blockPosition(), 64));
                }

//                if (!persistentData.equals(TommyLibServices.ENTITY.getPersistentData(serverPlayer))) {
//                    TommyLibServices.ENTITY.mergePersistentData(serverPlayer, persistentData, true);
//                }
            } else if (player.isLocalPlayer()) {
                int startTicks = persistentData.getInt(ClientboundStartSpinjitzuPayload.KEY_SPINJITZUSTARTTICKS);
                if (startTicks > 0)
                    persistentData.putInt(ClientboundStartSpinjitzuPayload.KEY_SPINJITZUSTARTTICKS, startTicks - 1);
                if (waitTicks > 0) {
                    persistentData.putInt(KEY_WAIT_TICKS, --waitTicks);
                } else {
                    if (MinejagoKeyMappings.ACTIVATE_SPINJITZU.isDown()) {
                        if (spinjitzu.active())
                            TommyLibServices.NETWORK.sendToServer(ServerboundStopSpinjitzuPayload.INSTANCE);
                        else if (!NO_SPINJITZU.test(player))
                            TommyLibServices.NETWORK.sendToServer(ServerboundStartSpinjitzuPayload.INSTANCE);
                        persistentData.putInt(KEY_WAIT_TICKS, 10);
                    }
                    if (MinejagoKeyMappings.MEDITATE.isDown()) {
                        if (focusData.isMeditating())
                            TommyLibServices.NETWORK.sendToServer(new ServerboundStopMeditationPayload(false));
                        else if (!NO_MEDITATION.test(player))
                            TommyLibServices.NETWORK.sendToServer(ServerboundStartMeditationPayload.INSTANCE);
                        persistentData.putInt(KEY_WAIT_TICKS, 10);
                    }
                    if (MinejagoKeyMappings.ENTER_SHADOW_FORM.isDown()) {
                        if (shadowSource.isPresent())
                            TommyLibServices.NETWORK.sendToServer(ServerboundStopShadowFormPayload.INSTANCE);
                        else if (!NO_SHADOW_FORM.test(player) && !NO_MEDITATION.test(player))
                            TommyLibServices.NETWORK.sendToServer(ServerboundStartShadowFormPayload.INSTANCE);
                        persistentData.putInt(KEY_WAIT_TICKS, 10);
                    }
                    if (shadowSource.isPresent()) {
                        if (MinejagoKeyMappings.SWITCH_DIMENSION.isDown()) {
                            TommyLibServices.NETWORK.sendToServer(ServerboundSwitchDimensionPayload.INSTANCE);
                            persistentData.putInt(KEY_WAIT_TICKS, 10);
                        }
                        if (MinejagoKeyMappings.INCREASE_SCALE.isDown()) {
                            TommyLibServices.NETWORK.sendToServer(new ServerboundAdjustShadowScalePayload(0.1));
                        }
                        if (MinejagoKeyMappings.DECREASE_SCALE.isDown()) {
                            TommyLibServices.NETWORK.sendToServer(new ServerboundAdjustShadowScalePayload(-0.1));
                        }
                        if (MinejagoKeyMappings.SUMMON_CLONE.isDown()) {
                            TommyLibServices.NETWORK.sendToServer(ServerboundSummonClonePayload.INSTANCE);
                            persistentData.putInt(KEY_WAIT_TICKS, 5);
                        }
                        if (MinejagoKeyMappings.RECALL_CLONES.isDown()) {
                            TommyLibServices.NETWORK.sendToServer(ServerboundRecallClonesPayload.INSTANCE);
                            persistentData.putInt(KEY_WAIT_TICKS, 5);
                        }
                    }
                    if (MinejagoKeyMappings.OPEN_SKILL_SCREEN.isDown()) {
                        MinejagoClientUtils.openSkillScreen();
                        persistentData.putInt(KEY_WAIT_TICKS, 10);
                    }
                    if (player.isShiftKeyDown()) {
                        if (spinjitzu.active()) {
                            TommyLibServices.NETWORK.sendToServer(ServerboundStopSpinjitzuPayload.INSTANCE);
                        }
                        if (focusData.isMeditating()) {
                            focusData.stopMeditating();
                            TommyLibServices.NETWORK.sendToServer(new ServerboundStopMeditationPayload(false));
                        }
                        persistentData.putInt(KEY_WAIT_TICKS, 10);
                    }
                }
//                TommyLibServices.ENTITY.mergePersistentData(player, persistentData, false);
            }
        }
    }

    public static boolean shouldTornadoOfCreationFail(Set<Entity> participants, Level level) {
        double averageFocusPercent = 0;
        for (Entity entity : participants) {
            averageFocusPercent += entity.getData(MinejagoAttachmentTypes.FOCUS).getFocusLevel();
        }
        averageFocusPercent /= participants.size();
        averageFocusPercent /= FocusConstants.MAX_FOCUS;
        return level.random.nextDouble() < 1.01 - averageFocusPercent;
    }

    public static void failTornadoOfCreation(ServerLevel level, Entity main, Set<Entity> participants) {
        int radius = Math.min(participants.size() * participants.size(), 256);
        level.explode(main, main.getX(), main.getY(), main.getZ(), radius, true, Level.ExplosionInteraction.MOB);
        stopTornadoOfCreation(participants);
    }

    public static void stopTornadoOfCreation(Set<Entity> participants) {
        for (Entity entity : participants) {
            stopSpinjitzu(entity.getData(MinejagoAttachmentTypes.SPINJITZU), entity, false);
            entity.getData(MinejagoAttachmentTypes.FOCUS).addExhaustion(FocusConstants.EXHAUSTION_TORNADO_OF_CREATION);
            entity.removeData(MinejagoAttachmentTypes.TORNADO_OF_CREATION);
//            TommyLibServices.ENTITY.removePersistentData(entity, true, KEY_TORNADO_OF_CREATION_SECONDS);
//            TommyLibServices.ENTITY.removePersistentData(entity, true, KEY_IS_IN_TORNADO_OF_CREATION);
            if (entity instanceof LivingEntity livingEntity)
                livingEntity.knockback(2, entity.getX(), entity.getZ());
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
                if (entity.isSpectator()) {
                    livingEntity.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).removeModifier(Minejago.modLoc("agility_modifier"));
                    SkillDataSet data = livingEntity.getData(MinejagoAttachmentTypes.SKILL);
                    if (!data.isDirty()) {
                        data.setDirty(true);
                        data.save(livingEntity, true);
                    }
                    return;
                }

                int offGroundTicks = /*TommyLibServices.ENTITY.getPersistentData(livingEntity).getInt(KEY_OFF_GROUND_TICKS)*/0;
                SkillDataSet data = livingEntity.getData(MinejagoAttachmentTypes.SKILL);
                if (!(livingEntity.isFallFlying() || livingEntity instanceof Player player && player.getAbilities().flying)) {
                    if (livingEntity.isSprinting()) {
                        data.addPractice(livingEntity, Skill.AGILITY, 1 / 40f);
                    }
                    if (offGroundTicks > 0 && offGroundTicks <= 30) {
                        data.addPractice(livingEntity, Skill.AGILITY, 1 / 40f);
                    }
                    if (livingEntity.isSteppingCarefully() && !livingEntity.level().getEntities(livingEntity, livingEntity.getBoundingBox().inflate(16)).isEmpty()) {
                        data.addPractice(livingEntity, Skill.STEALTH, 1 / 40f);
                    }
                }

                boolean dirty = data.isDirty();

                if (dirty) {
                    HashMultimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
                    modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(Minejago.modLoc("agility_modifier"), 0.01 * (data.get(Skill.AGILITY).level()), AttributeModifier.Operation.ADD_VALUE));
                    modifiers.put(Attributes.JUMP_STRENGTH, new AttributeModifier(Minejago.modLoc("agility_modifier"), 0.04 * (data.get(Skill.AGILITY).level()), AttributeModifier.Operation.ADD_VALUE));
                    modifiers.put(Attributes.SAFE_FALL_DISTANCE, new AttributeModifier(Minejago.modLoc("agility_modifier"), data.get(Skill.AGILITY).level(), AttributeModifier.Operation.ADD_VALUE));
                    modifiers.put(Attributes.SNEAKING_SPEED, new AttributeModifier(Minejago.modLoc("stealth_modifier"), 0.1 * (data.get(Skill.STEALTH).level()), AttributeModifier.Operation.ADD_VALUE));
                    livingEntity.getAttributes().addTransientAttributeModifiers(modifiers);
                    data.setDirty(false);
                }

                boolean emptyHanded = livingEntity.getMainHandItem().isEmpty();
                boolean toolHanded = livingEntity.getMainHandItem().is(ConventionalItemTags.TOOLS);
                if (dirty || emptyHanded && !livingEntity.getAttributes().hasModifier(Attributes.MINING_EFFICIENCY, Minejago.modLoc("dexterity_modifier")) || toolHanded && !livingEntity.getAttributes().hasModifier(Attributes.MINING_EFFICIENCY, Minejago.modLoc("tool_proficiency_modifier"))) {
                    HashMultimap<Holder<Attribute>, AttributeModifier> emptyHandModifiers = HashMultimap.create();
                    emptyHandModifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Minejago.modLoc("dexterity_modifier"), 0.2 * (data.get(Skill.DEXTERITY).level()), AttributeModifier.Operation.ADD_VALUE));
                    emptyHandModifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(Minejago.modLoc("dexterity_modifier"), 0.2 * (data.get(Skill.DEXTERITY).level()), AttributeModifier.Operation.ADD_VALUE));
                    emptyHandModifiers.put(Attributes.BLOCK_BREAK_SPEED, new AttributeModifier(Minejago.modLoc("dexterity_modifier"), 0.1 * (data.get(Skill.DEXTERITY).level()), AttributeModifier.Operation.ADD_VALUE));

                    HashMultimap<Holder<Attribute>, AttributeModifier> toolModifiers = HashMultimap.create();
                    toolModifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Minejago.modLoc("proficiency_modifier"), 0.2 * (data.get(Skill.PROFICIENCY).level()), AttributeModifier.Operation.ADD_VALUE));
                    toolModifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(Minejago.modLoc("proficiency_modifier"), 0.2 * (data.get(Skill.PROFICIENCY).level()), AttributeModifier.Operation.ADD_VALUE));
                    toolModifiers.put(Attributes.BLOCK_BREAK_SPEED, new AttributeModifier(Minejago.modLoc("proficiency_modifier"), 0.1 * (data.get(Skill.PROFICIENCY).level()), AttributeModifier.Operation.ADD_VALUE));

                    if (emptyHanded) {
                        livingEntity.getAttributes().addTransientAttributeModifiers(emptyHandModifiers);
                        livingEntity.getAttributes().removeAttributeModifiers(toolModifiers);
                    } else {
                        livingEntity.getAttributes().addTransientAttributeModifiers(toolModifiers);
                        livingEntity.getAttributes().removeAttributeModifiers(emptyHandModifiers);
                    }
                }
            }

            HolderSet.Named<Item> weapons = livingEntity.level().registryAccess().registryOrThrow(Registries.ITEM).getOrCreateTag(MinejagoItemTags.GOLDEN_WEAPONS);
            if (weapons.size() > 0 && weapons.stream().allMatch(item -> EntityUtils.hasAnyInInventory(livingEntity, stack -> stack.is(item.value())))) {
                // TODO: Overload event
            }
        }
    }

    public static void onPlayerEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        InteractionHand hand = event.getHand();
        Entity entity = event.getTarget();
        CompoundTag persistentData = /*TommyLibServices.ENTITY.getPersistentData(entity)*/new CompoundTag();
        if (level instanceof ServerLevel serverLevel && hand == InteractionHand.MAIN_HAND && entity instanceof Painting painting && painting.getVariant().is(MinejagoPaintingVariants.FOUR_WEAPONS) && !persistentData.getBoolean("MapTaken")) {
            player.addItem(MinejagoItemUtils.createFourWeaponsMaps(serverLevel, player.blockPosition()));
            if (!player.isCreative()) {
                persistentData.putBoolean("MapTaken", true);
                persistentData.putBoolean("MapTakenByPlayer", true);
//                TommyLibServices.ENTITY.setPersistentData(entity, persistentData, true);
            }
        }
    }

    public static void stopSpinjitzu(SpinjitzuData spinjitzu, Entity entity, boolean fail) {
        if (spinjitzu.active()) {
            new SpinjitzuData(spinjitzu.unlocked(), false).save(entity, true);
            if (entity instanceof ServerPlayer serverPlayer)
                TommyLibServices.NETWORK.sendToAllClients(new ClientboundStopSpinjitzuPayload(serverPlayer.getUUID(), fail), serverPlayer.getServer());
            if (entity instanceof LivingEntity livingEntity) {
                AttributeInstance speed = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
                if (speed != null && speed.hasModifier(SpinjitzuData.SPEED_MODIFIER))
                    speed.removeModifier(SpinjitzuData.SPEED_MODIFIER);
                AttributeInstance kb = livingEntity.getAttribute(Attributes.ATTACK_KNOCKBACK);
                if (kb != null && kb.hasModifier(SpinjitzuData.KNOCKBACK_MODIFIER))
                    kb.removeModifier(SpinjitzuData.KNOCKBACK_MODIFIER);
            }
            entity.level().playSound(null, entity.blockPosition(), MinejagoSoundEvents.SPINJITZU_STOP.get(), SoundSource.PLAYERS);
        }
    }

    public static void stopShadowForm(LivingEntity entity) {
        if (!(entity.level() instanceof ServerLevel serverLevel)) return;
        Optional<ShadowSourceData> sourceData = entity.getData(MinejagoAttachmentTypes.SHADOW_SOURCE);
        sourceData.ifPresent(data -> {
            ShadowSourceData.remove(entity, true);
            ServerLevel sourceLevel = serverLevel.getServer().getLevel(data.level());
            ShadowSource source = sourceLevel != null && sourceLevel.getEntity(data.uuid()) instanceof ShadowSource shadowSource ? shadowSource : null;
            if (source != null) {
                if (entity instanceof ServerPlayer serverPlayer) {
                    serverPlayer.getInventory().dropAll();
                    if (source.getInventory() != null) {
                        serverPlayer.getInventory().load(source.getInventory());
                        source.clearInventory();
                    }
                } else {
                    for (EquipmentSlot slot : EquipmentSlot.values()) {
                        ItemStack original = entity.getItemBySlot(slot);
                        if (!original.isEmpty()) {
                            ItemEntity item = new ItemEntity(serverLevel, entity.getX(), entity.getY(), entity.getZ(), original);
                            item.setDefaultPickUpDelay();
                            serverLevel.addFreshEntity(item);
                        }
                    }
                }
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    ItemStack stack = source.getItemBySlot(slot);
                    entity.setItemSlot(slot, stack);
                    source.setItemSlot(slot, ItemStack.EMPTY);
                }
                entity.changeDimension(new DimensionTransition(sourceLevel, source.position(), Vec3.ZERO, source.getYRot(), source.getXRot(), DimensionTransition.DO_NOTHING));
                sourceLevel.getChunkSource().removeRegionTicket(TicketType.PLAYER, source.chunkPosition(), 3, source.chunkPosition());
                source.remove(Entity.RemovalReason.DISCARDED);
            }
            recallShadowClones(entity);
            AttributeInstance flight = entity.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
            if (flight != null && flight.hasModifier(ShadowSourceData.FLIGHT_MODIFIER))
                flight.removeModifier(ShadowSourceData.FLIGHT_MODIFIER);
            AttributeInstance scale = entity.getAttribute(Attributes.SCALE);
            if (scale != null && scale.hasModifier(ShadowSourceData.SCALE_MODIFIER))
                scale.removeModifier(ShadowSourceData.SCALE_MODIFIER);
        });
    }

    public static void recallShadowClones(LivingEntity entity) {
        for (UUID uuid : entity.getData(MinejagoAttachmentTypes.SHADOW_CLONES)) {
            ShadowClone clone = ((ServerLevel) entity.level()).getEntity(uuid) instanceof ShadowClone shadowClone ? shadowClone : null;
            if (clone != null) {
                clone.remove(Entity.RemovalReason.DISCARDED);
            }
        }
        entity.removeData(MinejagoAttachmentTypes.SHADOW_CLONES);
    }

    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        for (EntityType<? extends LivingEntity> type : MinejagoEntityTypes.getAllAttributes().keySet()) {
            event.put(type, MinejagoEntityTypes.getAllAttributes().get(type));
        }
    }

    public static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(MinejagoEntityTypes.ZANE.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR, Zane::checkZaneSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(MinejagoEntityTypes.COLE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Character::checkNaturalCharacterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(MinejagoEntityTypes.SKULKIN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Skulkin::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(MinejagoEntityTypes.SKULKIN_HORSE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SkulkinHorse::checkSkeletonHorseSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
        event.register(MinejagoEntityTypes.SPYKOR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Spykor::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
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
        Entity entity = event.getEntity();
        if (!entity.level().isClientSide && entity instanceof LivingEntity livingEntity) {
            entity.getData(MinejagoAttachmentTypes.ELEMENT).save(livingEntity, true);
            entity.getData(MinejagoAttachmentTypes.SPINJITZU).save(livingEntity, true);
            entity.getData(MinejagoAttachmentTypes.FOCUS).setDirty(true);
            entity.getData(MinejagoAttachmentTypes.SKILL).save(livingEntity, true);
            entity.getData(MinejagoAttachmentTypes.SHADOW_SOURCE).ifPresent(data -> data.save(livingEntity, true));
        }
    }

    public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
        Entity entity = event.getEntity();
        if (!entity.level().isClientSide && entity instanceof LivingEntity livingEntity) {
            recallShadowClones(livingEntity);
        }
    }

    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            stopShadowForm(player);
        }
    }

    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.getEntity().level().isClientSide) {
            event.getEntity().getData(MinejagoAttachmentTypes.SPINJITZU).save(event.getEntity(), true);
            event.getEntity().getData(MinejagoAttachmentTypes.ELEMENT).save(event.getEntity(), true);
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
        SkillData data = event.getEntity().getData(MinejagoAttachmentTypes.SKILL).get(Skill.STEALTH);
        if (data.level() > 0)
            event.modifyVisibility(0.1 * data.level());
    }

    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (!player.getAbilities().instabuild) {
            SkillDataSet data = player.getData(MinejagoAttachmentTypes.SKILL);
            float defaultDestroyTime = event.getState().getBlock().defaultDestroyTime();
            if (player.getMainHandItem().isEmpty())
                data.addPractice(player, Skill.DEXTERITY, defaultDestroyTime);
            else if (player.getMainHandItem().isCorrectToolForDrops(event.getState()))
                data.addPractice(player, Skill.PROFICIENCY, defaultDestroyTime);
        }
    }

    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        Entity source = event.getSource().getEntity();
        if (!event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY) && entity.getData(MinejagoAttachmentTypes.SHADOW_SOURCE).isPresent() && (source == null || source.getData(MinejagoAttachmentTypes.SHADOW_SOURCE).isEmpty())) {
            event.setCanceled(true);
            return;
        }
        if (source != null && source.getData(MinejagoAttachmentTypes.SHADOW_SOURCE).isPresent()) {
            entity.getData(MinejagoAttachmentTypes.FOCUS).addExhaustion(FocusConstants.EXHAUSTION_SHADOW_FORM_NORMAL_ABILITY);
        }
        if (/*TommyLibServices.ENTITY.getPersistentData(entity).getBoolean(FrozenMobEffect.TAG_FROZEN) &&*/ !(event.getSource().is(DamageTypeTags.IS_FREEZING) || event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY))) {
//            event.setCanceled(true);
            if ((event.getSource().is(DamageTypeTags.IS_FIRE) || event.getSource().getWeaponItem() != null && event.getSource().getWeaponItem().canPerformAction(ItemAbilities.PICKAXE_DIG)) && entity.getRandom().nextInt(10) == 0) {
                entity.removeEffect(MinejagoMobEffects.FROZEN);
                // TODO: Remove when TEL fixes onRemove
                entity.addEffect(new MobEffectInstance(MinejagoMobEffects.FROZEN, 1, 0));
            }
            return;
        }
        if (event.getSource().getDirectEntity() instanceof LivingEntity attacker) {
            SkillDataSet data = attacker.getData(MinejagoAttachmentTypes.SKILL);
            if (attacker.getMainHandItem().isEmpty())
                data.addPractice(attacker, Skill.DEXTERITY, event.getAmount() / (data.get(Skill.DEXTERITY).level() + 1));
            else if (attacker.getMainHandItem().is(ConventionalItemTags.MELEE_WEAPON_TOOLS))
                data.addPractice(attacker, Skill.PROFICIENCY, event.getAmount() / (data.get(Skill.PROFICIENCY).level() + 1));
        }
    }

    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getData(MinejagoAttachmentTypes.SHADOW_SOURCE).isPresent()) {
            if (!entity.level().isClientSide())
                stopShadowForm(entity);
            event.getEntity().setHealth((float) event.getEntity().getAttributeBaseValue(Attributes.MAX_HEALTH));
            event.setCanceled(true);
        }
    }

    public static void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
        Entity entity = event.getEntity();
        if (entity.getData(MinejagoAttachmentTypes.SHADOW_SOURCE).isPresent()) {
            FocusData focusData = entity.getData(MinejagoAttachmentTypes.FOCUS);
            focusData.addExhaustion(FocusConstants.EXHAUSTION_SHADOW_FORM_SUPER_ABILITY);
            if (focusData.getFocusLevel() < FocusConstants.SHADOW_FORM_LEVEL)
                event.setCanceled(true);
        }
    }
}
