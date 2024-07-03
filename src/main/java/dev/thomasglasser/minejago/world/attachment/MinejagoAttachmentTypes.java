package dev.thomasglasser.minejago.world.attachment;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class MinejagoAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Minejago.MOD_ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<PowerData>> POWER = ATTACHMENT_TYPES.register("power", () -> AttachmentType.builder(() -> new PowerData(MinejagoPowers.NONE, false)).serialize(PowerData.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<SpinjitzuData>> SPINJITZU = ATTACHMENT_TYPES.register("spinjitzu", () -> AttachmentType.builder(() -> new SpinjitzuData(false, false)).serialize(SpinjitzuData.CODEC).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<FocusData>> FOCUS = ATTACHMENT_TYPES.register("focus", () -> AttachmentType.builder(FocusData::new).serialize(FocusData.CODEC).build());

    public static void init() {}
}
