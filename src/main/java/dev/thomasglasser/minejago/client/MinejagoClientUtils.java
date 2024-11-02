package dev.thomasglasser.minejago.client;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.gui.screens.inventory.DragonInventoryScreen;
import dev.thomasglasser.minejago.client.gui.screens.inventory.PowerSelectionScreen;
import dev.thomasglasser.minejago.client.gui.screens.inventory.ScrollViewScreen;
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
//            boolean displayDev;
            boolean displayLegacyDev;

            displaySnapshot = MinejagoClientConfig.get().displaySnapshotTesterCosmetic.get() && MinejagoClientUtils.checkSnapshotTester(uuid);
//            displayDev = MinejagoClientConfig.displayDevTeamCosmetic && MinejagoClientUtils.checkDevTeam(uuid);
            displayLegacyDev = MinejagoClientConfig.get().displayLegacyDevTeamCosmetic.get() && MinejagoClientUtils.checkLegacyDevTeam(uuid);

            TommyLibServices.NETWORK.sendToServer(new ServerboundChangeVipDataPayload(uuid, new VipData(MinejagoClientConfig.get().snapshotTesterCosmeticChoice.get(), displaySnapshot, /*displayDev*/false, displayLegacyDev)));
        }
    }

    public static boolean checkSnapshotTester(UUID uuid) {
        return isVip(uuid, "snapshot");
    }

    public static boolean checkDevTeam(UUID uuid) {
        return isVip(uuid, "dev");
    }

    public static boolean checkLegacyDevTeam(UUID uuid) {
        return isVip(uuid, "legacy_dev");
    }

    public static void setVipData(Player player, VipData data) {
        vipData.put(player, data);
    }

    private static boolean isVip(UUID uuid, String type) {
        BufferedReader fileReader = null;

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://gist.github.com/thomasglasser/281c3473f07430ddb83aac3e357a7797/raw/").openConnection();

            connection.setConnectTimeout(1000);
            connection.connect();

            if (HttpURLConnection.HTTP_OK != connection.getResponseCode()) {
                Minejago.LOGGER.error("Failed connection to cloud based special player list, response code " + connection.getResponseMessage());

                return false;
            }

            fileReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = fileReader.readLine()) != null) {
                if (!line.startsWith(" <!DOCTYPE")) {
                    String[] lineSplit = line.split("\\|");
                    UUID givenUUID;

                    if (lineSplit.length > 2) {
                        try {
                            givenUUID = UUID.fromString(lineSplit[1]);

                            if (givenUUID.equals(uuid) && lineSplit[2].contains(type)) {
                                return true;
                            }
                        } catch (IllegalArgumentException ex) {
                            Minejago.LOGGER.error("Invalid UUID format from web: " + lineSplit[1]);
                        }
                    }
                }
            }

            connection.disconnect();
        } catch (Exception e) {
            Minejago.LOGGER.error("Error while performing HTTP Tasks, dropping.", e);
        } finally {
            IOUtils.closeQuietly(fileReader);
        }

        return false;
    }

    public static MinejagoBlockEntityWithoutLevelRenderer getBewlr() {
        return bewlr;
    }

    public static void openPowerSelectionScreen(List<ResourceKey<Power>> powers, Optional<Integer> wuId) {
        ClientUtils.setScreen(new PowerSelectionScreen(Component.translatable("gui.power_selection.title"), powers, wuId.isPresent() && ClientUtils.getEntityById(wuId.get()) instanceof Wu wu ? wu : null));
    }

    public static void openScrollScreen(BookViewScreen.BookAccess bookAccess) {
        ClientUtils.setScreen(new ScrollViewScreen(bookAccess));
    }

    public static void openDragonInventoryScreen(DragonInventoryMenu dragonInventoryMenu, Player player, Dragon dragon, int columns) {
        ClientUtils.setScreen(new DragonInventoryScreen(dragonInventoryMenu, player.getInventory(), dragon, columns));
    }
}
