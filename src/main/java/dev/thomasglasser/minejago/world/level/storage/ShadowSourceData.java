package dev.thomasglasser.minejago.world.level.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.network.ClientboundSyncShadowSourcePayload;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public record ShadowSourceData(UUID uuid, ResourceKey<Level> level) {
    public static final Codec<ShadowSourceData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            UUIDUtil.CODEC.fieldOf("uuid").forGetter(ShadowSourceData::uuid),
            Level.RESOURCE_KEY_CODEC.fieldOf("level").forGetter(ShadowSourceData::level)).apply(instance, ShadowSourceData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ShadowSourceData> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, ShadowSourceData::uuid,
            ResourceKey.streamCodec(Registries.DIMENSION), ShadowSourceData::level,
            ShadowSourceData::new);
    public static final ResourceLocation FLIGHT_MODIFIER = Minejago.modLoc("shadow_flight");
    public static final ResourceLocation SCALE_MODIFIER = Minejago.modLoc("shadow_scale");

    public void save(LivingEntity entity, boolean syncToClient) {
        Optional<ShadowSourceData> data = Optional.of(this);
        entity.setData(MinejagoAttachmentTypes.SHADOW_SOURCE, data);
        // TODO: Advancement
//        if (active && entity instanceof ServerPlayer serverPlayer)
//            MinejagoCriteriaTriggers.DID_SPINJITZU.get().trigger(serverPlayer);
        if (syncToClient) TommyLibServices.NETWORK.sendToAllClients(new ClientboundSyncShadowSourcePayload(entity.getId(), data), entity.getServer());
    }

    public static void remove(LivingEntity entity, boolean syncToClient) {
        entity.setData(MinejagoAttachmentTypes.SHADOW_SOURCE, Optional.empty());
        if (syncToClient) TommyLibServices.NETWORK.sendToAllClients(new ClientboundSyncShadowSourcePayload(entity.getId(), Optional.empty()), entity.getServer());
    }
}
