package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.storage.SkillDataSet;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public record ClientboundSyncSkillDataSetPayload(SkillDataSet skillDataSet, int entity) implements ExtendedPacketPayload {
    public static final Type<ClientboundSyncSkillDataSetPayload> TYPE = new Type<>(Minejago.modLoc("sync_skill_data_set"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundSyncSkillDataSetPayload> CODEC = StreamCodec.composite(
            SkillDataSet.STREAM_CODEC, ClientboundSyncSkillDataSetPayload::skillDataSet,
            ByteBufCodecs.INT, ClientboundSyncSkillDataSetPayload::entity,
            ClientboundSyncSkillDataSetPayload::new);

    // ON CLIENT
    @Override
    public void handle(Player player) {
        if (player.level().getEntity(entity) instanceof LivingEntity livingEntity)
            skillDataSet.save(livingEntity, false);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
