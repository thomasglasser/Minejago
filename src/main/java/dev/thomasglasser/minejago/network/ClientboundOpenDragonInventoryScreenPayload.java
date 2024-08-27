package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.minejago.world.entity.dragon.Dragon;
import dev.thomasglasser.minejago.world.inventory.DragonInventoryMenu;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;

public record ClientboundOpenDragonInventoryScreenPayload(int id, int columns, int containerId) implements ExtendedPacketPayload {

    public static final Type<ClientboundOpenDragonInventoryScreenPayload> TYPE = new Type<>(Minejago.modLoc("open_dragon_inventory_screen"));
    public static final StreamCodec<ByteBuf, ClientboundOpenDragonInventoryScreenPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ClientboundOpenDragonInventoryScreenPayload::id,
            ByteBufCodecs.INT, ClientboundOpenDragonInventoryScreenPayload::columns,
            ByteBufCodecs.INT, ClientboundOpenDragonInventoryScreenPayload::containerId,
            ClientboundOpenDragonInventoryScreenPayload::new);

    // ON CLIENT
    @Override
    public void handle(Player player) {
        if (player.level().getEntity(id) instanceof Dragon dragon) {
            SimpleContainer simplecontainer = new SimpleContainer(Dragon.getInventorySize(columns));
            DragonInventoryMenu dragonInventoryMenu = new DragonInventoryMenu(
                    containerId, player.getInventory(), simplecontainer, dragon, columns);
            player.containerMenu = dragonInventoryMenu;
            MinejagoClientUtils.openDragonInventoryScreen(dragonInventoryMenu, player, dragon, columns);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
