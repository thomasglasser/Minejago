package dev.thomasglasser.minejago.world.level.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.network.ClientboundSyncSpinjitzuDataPayload;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public record SpinjitzuData(boolean unlocked, boolean active) {

    public static final Codec<SpinjitzuData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("unlocked").forGetter(SpinjitzuData::unlocked),
            Codec.BOOL.fieldOf("active").forGetter(SpinjitzuData::active))
            .apply(instance, SpinjitzuData::new));
    public static final StreamCodec<ByteBuf, SpinjitzuData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, SpinjitzuData::unlocked,
            ByteBufCodecs.BOOL, SpinjitzuData::active,
            SpinjitzuData::new);

    public static final ResourceLocation SPEED_MODIFIER = Minejago.modLoc("spinjitzu_speed");
    public static final ResourceLocation KNOCKBACK_MODIFIER = Minejago.modLoc("spinjitzu_knockback");
    public SpinjitzuData() {
        this(false, false);
    }

    public boolean canDoSpinjitzu() {
        return !MinejagoServerConfig.INSTANCE.requireCourseCompletion.get() || unlocked;
    }

    public void save(LivingEntity entity, boolean syncToClient) {
        entity.setData(MinejagoAttachmentTypes.SPINJITZU, this);
        if (active && entity instanceof ServerPlayer serverPlayer)
            MinejagoCriteriaTriggers.DID_SPINJITZU.get().trigger(serverPlayer);
        if (syncToClient) TommyLibServices.NETWORK.sendToAllClients(new ClientboundSyncSpinjitzuDataPayload(entity.getId(), this), entity.getServer());
    }
}
