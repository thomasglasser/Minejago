package dev.thomasglasser.minejago.client;

import dev.lambdaurora.lambdynlights.api.DynamicLightHandler;
import dev.lambdaurora.lambdynlights.api.DynamicLightHandlers;
import dev.thomasglasser.minejago.client.gui.screens.inventory.ScrollLecternScreen;
import dev.thomasglasser.minejago.network.ServerboundFlyVehiclePacket;
import dev.thomasglasser.minejago.network.ServerboundStopMeditationPacket;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.util.MinejagoItemUtils;
import dev.thomasglasser.minejago.world.entity.DataHolder;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.PlayerRideableFlying;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaid;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.focus.FocusDataHolder;
import dev.thomasglasser.minejago.world.inventory.MinejagoMenuTypes;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
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
import java.util.Objects;

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

    public static List<ItemStack> getItemsForTab(ResourceKey<CreativeModeTab> tab)
    {
        List<ItemStack> items = new ArrayList<>();

        MinejagoItems.getItemTabs().forEach((itemTab, itemLikes) -> {
            if (tab == itemTab)
            {
                itemLikes.forEach((itemLike) -> items.add(Objects.requireNonNull(BuiltInRegistries.ITEM.get(itemLike)).getDefaultInstance()));
            }
        });

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
        Player mainClientPlayer = MinejagoClientUtils.getMainClientPlayer();
        if (mainClientPlayer != null && key != GLFW.GLFW_KEY_ESCAPE)
        {
            FocusData focusData = ((FocusDataHolder) mainClientPlayer).getFocusData();
            if (focusData.isMeditating() && ((DataHolder)mainClientPlayer).getPersistentData().getInt("WaitTicks") <= 0)
            {
                Services.NETWORK.sendToServer(ServerboundStopMeditationPacket.class);
                focusData.setMeditating(false);
                ((DataHolder) mainClientPlayer).getPersistentData().putInt("WaitTicks", 5);
            }
        }
    }

    public static void registerDynamicLights()
    {
        DynamicLightHandlers.registerDynamicLightHandler(EntityType.PLAYER, DynamicLightHandler.makeHandler(player -> Services.DATA.getSpinjitzuData(player).active() ? 7 : 0, player -> false));
        DynamicLightHandlers.registerDynamicLightHandler(MinejagoEntityTypes.WU.get(), DynamicLightHandler.makeHandler(wu -> Services.DATA.getSpinjitzuData(wu).active() ? 7 : 0, wu -> false));
    }
}
