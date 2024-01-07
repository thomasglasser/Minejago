package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.services.DataHelper;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.level.storage.PowerAttachment;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuAttachment;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ForgeDataHelper implements DataHelper
{
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Minejago.MOD_ID);

    private static final DeferredHolder<AttachmentType<?>, AttachmentType<PowerAttachment>> POWER = ATTACHMENT_TYPES.register("power", () -> AttachmentType.builder(() -> new PowerAttachment(MinejagoPowers.NONE, false)).serialize(PowerAttachment.CODEC).build());
    private static final DeferredHolder<AttachmentType<?>, AttachmentType<SpinjitzuAttachment>> SPINJITZU = ATTACHMENT_TYPES.register("spinjitzu", () -> AttachmentType.builder(() -> new SpinjitzuAttachment(false, false)).serialize(SpinjitzuAttachment.CODEC).build());

    @Override
    public PowerData getPowerData(LivingEntity entity) {
        PowerAttachment attachment = entity.getData(POWER);
        return new PowerData(attachment.getPower(), attachment.isGiven());
    }

    @Override
    public void setPowerData(PowerData data, LivingEntity entity) {
        PowerAttachment attachment = entity.getData(POWER);
        attachment.setPower(data.power());
        attachment.setGiven(data.given());
        DataHelper.super.setPowerData(data, entity);
    }

    @Override
    public SpinjitzuData getSpinjitzuData(LivingEntity entity) {
        SpinjitzuAttachment attachment = entity.getData(SPINJITZU);
        return new SpinjitzuData(attachment.isUnlocked(), attachment.isActive());
    }

    @Override
    public void setSpinjitzuData(SpinjitzuData data, LivingEntity entity) {
        SpinjitzuAttachment attachment = entity.getData(SPINJITZU);
        attachment.setUnlocked(data.unlocked());
        attachment.setActive(data.active());
    }
}
