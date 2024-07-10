package dev.thomasglasser.minejago.world.level.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.network.ClientboundSyncPowerDataPayload;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public record PowerData(ResourceKey<Power> power, boolean given) {

    public static final Codec<PowerData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceKey.codec(MinejagoRegistries.POWER).fieldOf("power").forGetter(PowerData::power),
            Codec.BOOL.fieldOf("given").forGetter(PowerData::given))
            .apply(instance, PowerData::new));
    public static final StreamCodec<ByteBuf, PowerData> STREAM_CODEC = StreamCodec.composite(
            ResourceKey.streamCodec(MinejagoRegistries.POWER), PowerData::power,
            ByteBufCodecs.BOOL, PowerData::given,
            PowerData::new);
    public PowerData() {
        this(MinejagoPowers.NONE, false);
    }

    public PowerData(ResourceLocation location, boolean given) {
        this(ResourceKey.create(MinejagoRegistries.POWER, location), given);
    }

    @Override
    public ResourceKey<Power> power() {
        return power == null ? MinejagoPowers.NONE : power;
    }

    public void save(LivingEntity entity, boolean syncToClient) {
        entity.setData(MinejagoAttachmentTypes.POWER, this);
        if (power != MinejagoPowers.NONE && entity instanceof ServerPlayer serverPlayer)
            MinejagoCriteriaTriggers.GOT_POWER.get().trigger(serverPlayer, this.power());
        if (syncToClient) TommyLibServices.NETWORK.sendToAllClients(new ClientboundSyncPowerDataPayload(entity.getId(), this), entity.getServer());
    }
}
