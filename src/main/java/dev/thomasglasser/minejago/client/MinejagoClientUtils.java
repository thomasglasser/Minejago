package dev.thomasglasser.minejago.client;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.renderer.MinejagoBlockEntityWithoutLevelRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.layers.SnapshotTesterCosmeticOptions;
import dev.thomasglasser.minejago.client.renderer.entity.layers.VipData;
import dev.thomasglasser.minejago.network.ServerboundChangeVipDataPayload;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

public class MinejagoClientUtils {
    private static final HashMap<Player, VipData> vipData = new HashMap<>();
    private static final MinejagoBlockEntityWithoutLevelRenderer bewlr = new MinejagoBlockEntityWithoutLevelRenderer();

    public static boolean renderSnapshotTesterLayer(AbstractClientPlayer player)
    {
        return vipData.get(player) != null && vipData.get(player).displaySnapshot() && snapshotChoice(player) != null;
    }

    @Nullable
    public static SnapshotTesterCosmeticOptions snapshotChoice(AbstractClientPlayer player)
    {
        return vipData.get(player) != null && vipData.get(player).choice() != null ? vipData.get(player).choice() : null;
    }

    public static boolean renderDevLayer(AbstractClientPlayer player)
    {
        return vipData.get(player) != null && vipData.get(player).displayDev();
    }

    public static boolean renderOgDevLayer(AbstractClientPlayer player)
    {
        return vipData.get(player) != null && vipData.get(player).displayOgDev();
    }

    public static void refreshVip()
    {
        if (Minecraft.getInstance().player != null)
        {
            UUID uuid = Minecraft.getInstance().player.getUUID();

            boolean displaySnapshot;
//            boolean displayDev;
            boolean displayOgDev;

            displaySnapshot = MinejagoClientConfig.displaySnapshotTesterCosmetic && MinejagoClientUtils.checkSnapshotTester(uuid);
//            displayDev = MinejagoClientConfig.displayDevTeamCosmetic && MinejagoClientUtils.checkDevTeam(uuid);
            displayOgDev = MinejagoClientConfig.displayOgDevTeamCosmetic && MinejagoClientUtils.checkOgDevTeam(uuid);

            TommyLibServices.NETWORK.sendToServer(new ServerboundChangeVipDataPayload(uuid, new VipData(MinejagoClientConfig.snapshotTesterCosmeticChoice, displaySnapshot, /*displayDev*/false, displayOgDev)));
        }
    }


    public static boolean checkSnapshotTester(UUID uuid)
    {
        return isVip(uuid, "snapshot");
    }

    public static boolean checkDevTeam(UUID uuid)
    {
        return isVip(uuid, "dev");
    }

    public static boolean checkOgDevTeam(UUID uuid)
    {
        return isVip(uuid, "og_dev");
    }

    public static void setVipData(Player player, VipData data) {
        vipData.put(player, data);
    }

    private static boolean isVip(UUID uuid, String type) {
        BufferedReader fileReader = null;

        try {
            HttpURLConnection connection = (HttpURLConnection)new URL("https://gist.github.com/thomasglasser/281c3473f07430ddb83aac3e357a7797/raw/").openConnection();

            connection.setConnectTimeout(1000);
            connection.connect();

            if (HttpURLConnection.HTTP_OK != connection.getResponseCode()) {
                Minejago.LOGGER.error("Failed connection to cloud based special player list, response code " + connection.getResponseMessage());

                return false;
            }

            fileReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            if (vipData != null) {
                while ((line = fileReader.readLine()) != null) {
                    if (!line.startsWith(" <!DOCTYPE")) {
                        String[] lineSplit = line.split("\\|");
                        UUID givenUUID;

                        if (lineSplit.length > 2) {
                            try {
                                givenUUID = UUID.fromString(lineSplit[1]);

                                if (givenUUID.equals(uuid) && lineSplit[2].contains(type))
                                {
                                    return true;
                                }
                            }
                            catch (IllegalArgumentException ex) {
                                Minejago.LOGGER.error("Invalid UUID format from web: " + lineSplit[1]);
                            }
                        }
                    }
                }
            }

            connection.disconnect();
        }
        catch (Exception e) {
            Minejago.LOGGER.error("Error while performing HTTP Tasks, dropping.", e);
        }
        finally {
            IOUtils.closeQuietly(fileReader);
        }

        return false;
    }

    public static MinejagoBlockEntityWithoutLevelRenderer getBewlr()
    {
        return bewlr;
    }
}
