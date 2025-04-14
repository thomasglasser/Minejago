package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.ai.memory.MinejagoMemoryModuleTypes;
import dev.thomasglasser.minejago.world.entity.character.Wu;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.minejago.world.entity.element.MinejagoElements;
import dev.thomasglasser.minejago.world.level.storage.ElementData;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import net.tslat.smartbrainlib.util.BrainUtils;

public record ServerboundSetElementDataPayload(Holder<Element> element, boolean markGiven, Optional<Integer> wuId) implements ExtendedPacketPayload {

    public static final Type<ServerboundSetElementDataPayload> TYPE = new Type<>(Minejago.modLoc("serverbound_set_element_data_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundSetElementDataPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.holder(MinejagoRegistries.ELEMENT, Element.STREAM_CODEC), ServerboundSetElementDataPayload::element,
            ByteBufCodecs.BOOL, ServerboundSetElementDataPayload::markGiven,
            ByteBufCodecs.optional(ByteBufCodecs.INT), ServerboundSetElementDataPayload::wuId,
            ServerboundSetElementDataPayload::new);

    // ON SERVER
    public void handle(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            Wu wu = null;
            if (wuId.isPresent() && serverPlayer.level().getEntity(wuId.get()) instanceof Wu) {
                wu = (Wu) serverPlayer.level().getEntity(wuId.get());
            }
            Holder<Element> old = player.level().holderOrThrow(serverPlayer.getData(MinejagoAttachmentTypes.ELEMENT).element());
            if (serverPlayer.getData(MinejagoAttachmentTypes.ELEMENT).given() && old != MinejagoElements.NONE && MinejagoServerConfig.get().drainPool.get() && wu != null) {
                wu.addElementsToGive(old);
            }
            if (element != MinejagoElements.NONE && wu != null) wu.removeElementsToGive(element);
            if (element == MinejagoElements.NONE) {
                new ElementData(element.getKey(), true).save(serverPlayer, true);
                serverPlayer.displayClientMessage(Component.translatable(Wu.NO_ELEMENT_GIVEN_KEY), true);
            } else if (wu != null) {
                BrainUtils.setMemory(wu, MemoryModuleType.INTERACTION_TARGET, serverPlayer);
                BrainUtils.setMemory(wu, MinejagoMemoryModuleTypes.SELECTED_ELEMENT.get(), element.getKey());
            } else {
                new ElementData(element.getKey(), true).save(serverPlayer, true);
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
