package dev.thomasglasser.minejago.client;

import dev.thomasglasser.minejago.client.gui.screens.inventory.ScrollLecternScreen;
import dev.thomasglasser.minejago.network.ServerboundFlyVehiclePacket;
import dev.thomasglasser.minejago.network.ServerboundStopMeditationPacket;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaid;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.inventory.MinejagoMenuTypes;
import dev.thomasglasser.minejago.world.item.MinejagoItemUtils;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import dev.thomasglasser.tommylib.api.world.entity.PlayerRideableFlying;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

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
        if (PlayerRideableFlying.isRidingFlyable(player)) {
            if (MinejagoKeyMappings.ASCEND.isDown())
            {
                ((PlayerRideableFlying)player.getVehicle()).ascend();
                TommyLibServices.NETWORK.sendToServer(ServerboundFlyVehiclePacket.ID, ServerboundFlyVehiclePacket::new, ServerboundFlyVehiclePacket.write(ServerboundFlyVehiclePacket.Type.START_ASCEND));
            }
            else if (MinejagoKeyMappings.DESCEND.isDown())
            {
                ((PlayerRideableFlying)player.getVehicle()).descend();
                TommyLibServices.NETWORK.sendToServer(ServerboundFlyVehiclePacket.ID, ServerboundFlyVehiclePacket::new, ServerboundFlyVehiclePacket.write(ServerboundFlyVehiclePacket.Type.START_DESCEND));
            }
            else
            {
                ((PlayerRideableFlying)player.getVehicle()).stop();
                TommyLibServices.NETWORK.sendToServer(ServerboundFlyVehiclePacket.ID, ServerboundFlyVehiclePacket::new, ServerboundFlyVehiclePacket.write(ServerboundFlyVehiclePacket.Type.STOP));
            }
        }
    }

    public static List<ItemStack> getItemsForTab(ResourceKey<CreativeModeTab> tab)
    {
        List<ItemStack> items = new ArrayList<>();

        if (tab == CreativeModeTabs.FOOD_AND_DRINKS)
        {
            for (Potion potion : BuiltInRegistries.POTION) {
                if (potion != Potions.EMPTY) {
                    items.add(MinejagoItemUtils.fillTeacup(potion));
                }
            }
        }

        if (tab == CreativeModeTabs.COMBAT)
        {
            MinejagoArmors.ARMOR_SETS.forEach(set ->
                    items.addAll(set.getAllAsItems().stream().map(Item::getDefaultInstance).toList()));
        }

        if (tab == CreativeModeTabs.FUNCTIONAL_BLOCKS)
        {
            items.add(SkulkinRaid.getLeaderBannerInstance());
        }

        return items;
    }

    public static void onInput(int key)
    {
        Player mainClientPlayer = ClientUtils.getMainClientPlayer();
        if (mainClientPlayer != null && !(key >= GLFW.GLFW_KEY_F1 && key <= GLFW.GLFW_KEY_F25) && !MinejagoKeyMappings.MEDITATE.isDown() && key != GLFW.GLFW_KEY_LEFT_SHIFT && key != GLFW.GLFW_KEY_ESCAPE)
        {
            FocusData focusData = Services.DATA.getFocusData(mainClientPlayer);
            CompoundTag persistentData = TommyLibServices.ENTITY.getPersistentData(mainClientPlayer);
            if (focusData.isMeditating() && persistentData.getInt("WaitTicks") <= 0)
            {
                TommyLibServices.NETWORK.sendToServer(ServerboundStopMeditationPacket.ID, ServerboundStopMeditationPacket::new, ServerboundStopMeditationPacket.write(true));
                focusData.stopMeditating();
                persistentData.putInt("WaitTicks", 5);
                TommyLibServices.ENTITY.setPersistentData(mainClientPlayer, persistentData, false);
            }
        }
    }
}
