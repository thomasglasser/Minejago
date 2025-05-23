package dev.thomasglasser.minejago.client;

import dev.thomasglasser.minejago.client.gui.screens.inventory.DragonInventoryScreen;
import dev.thomasglasser.minejago.client.gui.screens.inventory.ElementSelectionScreen;
import dev.thomasglasser.minejago.client.gui.screens.inventory.ScrollViewScreen;
import dev.thomasglasser.minejago.client.gui.screens.inventory.SkillScreen;
import dev.thomasglasser.minejago.client.renderer.MinejagoBlockEntityWithoutLevelRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.layers.SnapshotTesterCosmeticOptions;
import dev.thomasglasser.minejago.client.renderer.entity.layers.VipData;
import dev.thomasglasser.minejago.network.ServerboundChangeVipDataPayload;
import dev.thomasglasser.minejago.world.entity.character.Wu;
import dev.thomasglasser.minejago.world.entity.dragon.Dragon;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.minejago.world.inventory.DragonInventoryMenu;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import dev.thomasglasser.tommylib.api.world.entity.player.SpecialPlayerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class MinejagoClientUtils {
    private static final HashMap<Player, VipData> vipData = new HashMap<>();
    private static final MinejagoBlockEntityWithoutLevelRenderer bewlr = new MinejagoBlockEntityWithoutLevelRenderer();

    public static boolean renderSnapshotTesterLayer(AbstractClientPlayer player) {
        return vipData.get(player) != null && SpecialPlayerUtils.renderCosmeticLayerInSlot(player, snapshotChoice(player).slot()) && vipData.get(player).displaySnapshot();
    }

    public static SnapshotTesterCosmeticOptions snapshotChoice(AbstractClientPlayer player) {
        return vipData.get(player) != null && vipData.get(player).choice() != null ? vipData.get(player).choice() : SnapshotTesterCosmeticOptions.BAMBOO_HAT;
    }

    public static boolean renderDevLayer(AbstractClientPlayer player) {
        return vipData.get(player) != null && SpecialPlayerUtils.renderCosmeticLayerInSlot(player, EquipmentSlot.HEAD/*TODO:Figure out slot*/) && vipData.get(player).displayDev();
    }

    public static boolean renderLegacyDevLayer(AbstractClientPlayer player) {
        return vipData.get(player) != null && SpecialPlayerUtils.renderCosmeticLayerInSlot(player, EquipmentSlot.HEAD) && vipData.get(player).displayLegacyDev();
    }

    public static void refreshVip() {
        if (Minecraft.getInstance().player != null) {
            UUID uuid = Minecraft.getInstance().player.getUUID();

            boolean displaySnapshot;
            boolean displayDev;
            boolean displayLegacyDev;

            displaySnapshot = MinejagoClientConfig.get().displaySnapshotTesterCosmetic.get();
            displayDev = MinejagoClientConfig.get().displayDevTeamCosmetic.get();
            displayLegacyDev = MinejagoClientConfig.get().displayLegacyDevTeamCosmetic.get();

            TommyLibServices.NETWORK.sendToServer(new ServerboundChangeVipDataPayload(uuid, new VipData(MinejagoClientConfig.get().snapshotTesterCosmeticChoice.get(), displaySnapshot, displayDev, displayLegacyDev)));
        }
    }

    public static boolean verifySnapshotTester(UUID uuid) {
        return SpecialPlayerUtils.getSpecialTypes(VipData.GIST, uuid).contains(SpecialPlayerUtils.SNAPSHOT_TESTER_KEY);
    }

    public static void setVipData(Player player, VipData data) {
        vipData.put(player, data);
    }

    public static MinejagoBlockEntityWithoutLevelRenderer getBewlr() {
        return bewlr;
    }

    public static void openElementSelectionScreen(ArrayList<Holder<Element>> elements, Optional<Integer> wuId) {
        Minecraft.getInstance().setScreen(new ElementSelectionScreen(Component.translatable(ElementSelectionScreen.TITLE), elements, wuId.isPresent() && ClientUtils.getEntityById(wuId.get()) instanceof Wu wu ? wu : null));
    }

    public static void openScrollScreen(BookViewScreen.BookAccess bookAccess) {
        Minecraft.getInstance().setScreen(new ScrollViewScreen(bookAccess));
    }

    public static void openDragonInventoryScreen(DragonInventoryMenu dragonInventoryMenu, Player player, Dragon dragon, int columns) {
        Minecraft.getInstance().setScreen(new DragonInventoryScreen(dragonInventoryMenu, player.getInventory(), dragon, columns));
    }

    public static void openSkillScreen() {
        Minecraft.getInstance().setScreen(new SkillScreen());
    }
}
