package dev.thomasglasser.minejago.world.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.ai.behavior.TrackSpinjitzuCourseCompletion;
import dev.thomasglasser.minejago.world.entity.character.Character;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import dev.thomasglasser.tommylib.api.util.TommyLibExtraStreamCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class MinejagoEntitySerializers {
    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, Minejago.MOD_ID);

    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Character.MeditationStatus>> MEDITATION_STATUS = ENTITY_DATA_SERIALIZERS.register("meditation_status", () -> EntityDataSerializer.forValueType(TommyLibExtraStreamCodecs.forEnum(Character.MeditationStatus.class)));
    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<TrackSpinjitzuCourseCompletion.Stage>> STAGE = ENTITY_DATA_SERIALIZERS.register("stage", () -> EntityDataSerializer.forValueType(TommyLibExtraStreamCodecs.forEnum(TrackSpinjitzuCourseCompletion.Stage.class)));

    public static void init() {}
}
