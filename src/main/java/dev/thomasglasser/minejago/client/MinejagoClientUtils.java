package dev.thomasglasser.minejago.client;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.gui.screens.inventory.DragonInventoryScreen;
import dev.thomasglasser.minejago.client.gui.screens.inventory.PowerSelectionScreen;
import dev.thomasglasser.minejago.client.gui.screens.inventory.ScrollViewScreen;
import dev.thomasglasser.minejago.client.gui.screens.inventory.SkillScreen;
import dev.thomasglasser.minejago.client.renderer.MinejagoBlockEntityWithoutLevelRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.layers.SnapshotTesterCosmeticOptions;
import dev.thomasglasser.minejago.client.renderer.entity.layers.VipData;
import dev.thomasglasser.minejago.network.ServerboundChangeVipDataPayload;
import dev.thomasglasser.minejago.world.entity.character.Wu;
import dev.thomasglasser.minejago.world.entity.dragon.Dragon;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.inventory.DragonInventoryMenu;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;

public class MinejagoClientUtils {
    private static final String GIST = "thomasglasser/281c3473f07430ddb83aac3e357a7797";
    private static final HashMap<Player, VipData> vipData = new HashMap<>();
    private static final MinejagoBlockEntityWithoutLevelRenderer bewlr = new MinejagoBlockEntityWithoutLevelRenderer();

    public static boolean renderCosmeticLayerInSlot(AbstractClientPlayer player, EquipmentSlot slot) {
        return slot == null || !player.hasItemInSlot(slot);
    }

    public static boolean renderSnapshotTesterLayer(AbstractClientPlayer player) {
        return vipData.get(player) != null && snapshotChoice(player) != null && renderCosmeticLayerInSlot(player, snapshotChoice(player).slot()) && vipData.get(player).displaySnapshot();
    }

    @Nullable
    public static SnapshotTesterCosmeticOptions snapshotChoice(AbstractClientPlayer player) {
        return vipData.get(player) != null && vipData.get(player).choice() != null ? vipData.get(player).choice() : null;
    }

    public static boolean renderDevLayer(AbstractClientPlayer player) {
        return vipData.get(player) != null && renderCosmeticLayerInSlot(player, EquipmentSlot.HEAD/*TODO:Figure out slot*/) && vipData.get(player).displayDev();
    }

    public static boolean renderLegacyDevLayer(AbstractClientPlayer player) {
        return vipData.get(player) != null && renderCosmeticLayerInSlot(player, EquipmentSlot.HEAD) && vipData.get(player).displayLegacyDev();
    }

    public static void refreshVip() {
        if (Minecraft.getInstance().player != null) {
            UUID uuid = Minecraft.getInstance().player.getUUID();

            boolean displaySnapshot;
            boolean displayDev;
            boolean displayLegacyDev;

            displaySnapshot = MinejagoClientConfig.get().displaySnapshotTesterCosmetic.get() && ClientUtils.checkSnapshotTester(GIST, uuid);
            displayDev = MinejagoClientConfig.get().displayDevTeamCosmetic.get() && ClientUtils.checkDevTeam(GIST, uuid);
            displayLegacyDev = MinejagoClientConfig.get().displayLegacyDevTeamCosmetic.get() && ClientUtils.checkLegacyDevTeam(GIST, uuid);

            TommyLibServices.NETWORK.sendToServer(new ServerboundChangeVipDataPayload(uuid, new VipData(MinejagoClientConfig.get().snapshotTesterCosmeticChoice.get(), displaySnapshot, /*displayDev*/false, displayLegacyDev)));
        }
    }

    public static boolean verifySnapshotTester(UUID uuid) {
        return ClientUtils.checkSnapshotTester(GIST, uuid);
    }

    public static void setVipData(Player player, VipData data) {
        vipData.put(player, data);
    }

    public static MinejagoBlockEntityWithoutLevelRenderer getBewlr() {
        return bewlr;
    }

    public static void openPowerSelectionScreen(List<ResourceKey<Power>> powers, Optional<Integer> wuId) {
        ClientUtils.setScreen(new PowerSelectionScreen(Component.translatable(PowerSelectionScreen.TITLE), powers, wuId.isPresent() && ClientUtils.getEntityById(wuId.get()) instanceof Wu wu ? wu : null));
    }

    public static void openScrollScreen(BookViewScreen.BookAccess bookAccess) {
        ClientUtils.setScreen(new ScrollViewScreen(bookAccess));
    }

    public static void openDragonInventoryScreen(DragonInventoryMenu dragonInventoryMenu, Player player, Dragon dragon, int columns) {
        ClientUtils.setScreen(new DragonInventoryScreen(dragonInventoryMenu, player.getInventory(), dragon, columns));
    }

    public static void openSkillScreen() {
        ClientUtils.setScreen(new SkillScreen());
    }
}
