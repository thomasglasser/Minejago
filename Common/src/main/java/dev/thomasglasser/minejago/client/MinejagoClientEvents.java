package dev.thomasglasser.minejago.client;

import dev.thomasglasser.minejago.client.gui.screens.inventory.ScrollLecternScreen;
import dev.thomasglasser.minejago.network.ServerboundFlyVehiclePacket;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.world.entity.PlayerRideableFlying;
import dev.thomasglasser.minejago.world.inventory.MinejagoMenuTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;

public class MinejagoClientEvents
{
    public static void onPlayerLoggedIn()
    {
        MinejagoClientUtils.refreshVip();
    }

    public static void registerMenuScreens()
    {
        MenuScreens.register(MinejagoMenuTypes.SCROLL_LECTERN.get(), ScrollLecternScreen::new);
    }

    public static void onClientTick()
    {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null)
            return;
        if (isRidingFlyable(player)) {
            if (MinejagoKeyMappings.ASCEND.isDown())
            {
                ((PlayerRideableFlying)player.getVehicle()).ascend();
                Services.NETWORK.sendToServer(ServerboundFlyVehiclePacket.class, ServerboundFlyVehiclePacket.toBytes(ServerboundFlyVehiclePacket.Type.START_ASCEND));
            }
            else if (MinejagoKeyMappings.DESCEND.isDown())
            {
                ((PlayerRideableFlying)player.getVehicle()).descend();
                Services.NETWORK.sendToServer(ServerboundFlyVehiclePacket.class, ServerboundFlyVehiclePacket.toBytes(ServerboundFlyVehiclePacket.Type.START_DESCEND));
            }
            else
            {
                ((PlayerRideableFlying)player.getVehicle()).stop();
                Services.NETWORK.sendToServer(ServerboundFlyVehiclePacket.class, ServerboundFlyVehiclePacket.toBytes(ServerboundFlyVehiclePacket.Type.STOP));
            }
        }
    }

    public static boolean isRidingFlyable(Player player) {
        return player.getVehicle() instanceof PlayerRideableFlying && player.getVehicle().getControllingPassenger().is(player);
    }
}
