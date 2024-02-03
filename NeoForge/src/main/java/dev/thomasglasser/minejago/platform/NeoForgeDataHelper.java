package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.services.DataHelper;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class NeoForgeDataHelper implements DataHelper
{
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Minejago.MOD_ID);

    private static final DeferredHolder<AttachmentType<?>, AttachmentType<PowerData>> POWER = ATTACHMENT_TYPES.register("power", () -> AttachmentType.builder(() -> new PowerData(MinejagoPowers.NONE, false)).serialize(PowerData.CODEC).build());
    private static final DeferredHolder<AttachmentType<?>, AttachmentType<SpinjitzuData>> SPINJITZU = ATTACHMENT_TYPES.register("spinjitzu", () -> AttachmentType.builder(() -> new SpinjitzuData(false, false)).serialize(SpinjitzuData.CODEC).build());
    private static final DeferredHolder<AttachmentType<?>, AttachmentType<FocusData>> FOCUS = ATTACHMENT_TYPES.register("focus", () -> AttachmentType.builder(FocusData::new).serialize(FocusData.CODEC).build());

    @Override
    public PowerData getPowerData(LivingEntity entity) {
        return entity.getData(POWER);
    }

    @Override
    public void setPowerData(PowerData data, LivingEntity entity) {
        entity.setData(POWER, data);
        DataHelper.super.setPowerData(data, entity);
    }

    @Override
    public SpinjitzuData getSpinjitzuData(LivingEntity entity) {
        return entity.getData(SPINJITZU);
    }

    @Override
    public void setSpinjitzuData(SpinjitzuData data, LivingEntity entity) {
        entity.setData(SPINJITZU, data);
    }

    @Override
    public FocusData getFocusData(LivingEntity entity)
    {
        return entity.getData(FOCUS);
    }

    @Override
    public void setFocusData(FocusData data, LivingEntity entity)
    {
        entity.setData(FOCUS, data);
    }
}
