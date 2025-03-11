package dev.thomasglasser.minejago.world.attachment;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import dev.thomasglasser.minejago.world.level.storage.SkillDataSet;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import java.util.Optional;
import java.util.UUID;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class MinejagoAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Minejago.MOD_ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<PowerData>> POWER = ATTACHMENT_TYPES.register("power", () -> AttachmentType.builder(PowerData::new).serialize(PowerData.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<SpinjitzuData>> SPINJITZU = ATTACHMENT_TYPES.register("spinjitzu", () -> AttachmentType.builder(SpinjitzuData::new).serialize(SpinjitzuData.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<FocusData>> FOCUS = ATTACHMENT_TYPES.register("focus", () -> AttachmentType.builder(FocusData::new).serialize(FocusData.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<SkillDataSet>> SKILL = ATTACHMENT_TYPES.register("skill", () -> AttachmentType.builder(() -> new SkillDataSet()).serialize(SkillDataSet.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Optional<UUID>>> SHADOW_SOURCE = ATTACHMENT_TYPES.register("shadow_source", () -> AttachmentType.builder(Optional::<UUID>empty).build());

    public static void init() {}
}
