package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.ai.memory.MinejagoMemoryModuleTypes;
import dev.thomasglasser.minejago.world.entity.character.Wu;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import java.util.Optional;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

public record ServerboundSetPowerDataPayload(ResourceKey<Power> power, boolean markGiven, Optional<Integer> wuId) implements ExtendedPacketPayload {

    public static final Type<ServerboundSetPowerDataPayload> TYPE = new Type<>(Minejago.modLoc("serverbound_set_power_data_packet"));
    public static final StreamCodec<FriendlyByteBuf, ServerboundSetPowerDataPayload> CODEC = StreamCodec.composite(
            ResourceKey.streamCodec(MinejagoRegistries.POWER), ServerboundSetPowerDataPayload::power,
            ByteBufCodecs.BOOL, ServerboundSetPowerDataPayload::markGiven,
            ByteBufCodecs.optional(ByteBufCodecs.INT), ServerboundSetPowerDataPayload::wuId,
            ServerboundSetPowerDataPayload::new);

    // ON SERVER
    public void handle(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            Wu wu = null;
            if (wuId.isPresent() && serverPlayer.level().getEntity(wuId.get()) instanceof Wu) {
                wu = (Wu) serverPlayer.level().getEntity(wuId.get());
            }
            ResourceKey<Power> oldPower = serverPlayer.getData(MinejagoAttachmentTypes.POWER).power();
            if (serverPlayer.getData(MinejagoAttachmentTypes.POWER).given() && oldPower != MinejagoPowers.NONE && MinejagoServerConfig.INSTANCE.drainPool.get() && wu != null) {
                wu.addPowersToGive(oldPower);
            }
            if (power != MinejagoPowers.NONE && wu != null) wu.removePowersToGive(power);
            if (power == MinejagoPowers.NONE) {
                new PowerData(power, true).save(serverPlayer, true);
                serverPlayer.displayClientMessage(Component.translatable(Wu.NO_POWER_GIVEN_KEY), true);
            } else if (wu != null) {
                BrainUtils.setMemory(wu, MemoryModuleType.INTERACTION_TARGET, serverPlayer);
                BrainUtils.setMemory(wu, MinejagoMemoryModuleTypes.SELECTED_POWER.get(), power);
            } else {
                new PowerData(power, true).save(serverPlayer, true);
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
