package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.platform.services.DataHelper;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

@SuppressWarnings({"UnstableApiUsage"})
public class FabricDataHelper implements DataHelper {
    private static final AttachmentType<PowerData> POWER = AttachmentRegistry.<PowerData>builder().initializer(PowerData::new).persistent(PowerData.CODEC).buildAndRegister(Minejago.modLoc("power"));
    private static final AttachmentType<SpinjitzuData> SPINJITZU = AttachmentRegistry.<SpinjitzuData>builder().initializer(SpinjitzuData::new).persistent(SpinjitzuData.CODEC).buildAndRegister(Minejago.modLoc("spinjitzu"));
    public static final AttachmentType<FocusData> FOCUS = AttachmentRegistry.<FocusData>builder().initializer(FocusData::new).persistent(FocusData.CODEC).buildAndRegister(Minejago.modLoc("focus"));

    @Override
    public PowerData getPowerData(LivingEntity entity) {
        return entity.getAttachedOrCreate(POWER);
    }

    @Override
    public void setPowerData(PowerData data, LivingEntity entity) {
        entity.setAttached(POWER, data);
        if (entity instanceof ServerPlayer serverPlayer)
            MinejagoCriteriaTriggers.GOT_POWER.get().trigger(serverPlayer, data.power());
    }

    @Override
    public SpinjitzuData getSpinjitzuData(LivingEntity entity) {
        return entity.getAttachedOrCreate(SPINJITZU);
    }

    @Override
    public void setSpinjitzuData(SpinjitzuData data, LivingEntity entity) {
        entity.setAttached(SPINJITZU, data);
    }

    @Override
    public FocusData getFocusData(LivingEntity entity)
    {
        return entity.getAttachedOrCreate(FOCUS);
    }

    @Override
    public void setFocusData(FocusData data, LivingEntity entity)
    {
        entity.setAttached(FOCUS, data);
    }
}
