package dev.thomasglasser.minejago.util;

import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.util.Ease;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.MinejagoPlayerAnimator;
import dev.thomasglasser.minejago.client.renderer.entity.layers.BetaTesterLayerOptions;
import dev.thomasglasser.minejago.client.renderer.entity.layers.LayersConfig;
import dev.thomasglasser.minejago.client.renderer.entity.layers.VipData;
import dev.thomasglasser.minejago.network.ServerboundChangeVipDataPacket;
import dev.thomasglasser.minejago.platform.Services;
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

    private MinejagoClientUtils() {}

    public static AbstractClientPlayer getClientPlayerByUUID(UUID uuid) {
        return (AbstractClientPlayer) Minecraft.getInstance().level.getPlayerByUUID(uuid);
    }

    public static boolean renderBetaLayer(AbstractClientPlayer player)
    {
        return vipData.get(player) != null && vipData.get(player).displayBeta() && betaChoice(player) != null;
    }

    @Nullable
    public static BetaTesterLayerOptions betaChoice(AbstractClientPlayer player)
    {
        return vipData.get(player) != null && vipData.get(player).choice() != null ? vipData.get(player).choice() : null;
    }

    public static boolean renderDevLayer(AbstractClientPlayer player)
    {
        return vipData.get(player) != null && vipData.get(player).displayDev();
    }

    public static void refreshVip()
    {
        if (Minecraft.getInstance().player != null)
        {
            UUID uuid = Minecraft.getInstance().player.getUUID();

            boolean displayDev;
            boolean displayBeta;

            displayDev = LayersConfig.DISPLAY_DEV.get() && MinejagoClientUtils.checkDev(uuid);
            displayBeta = LayersConfig.DISPLAY_BETA.get() && MinejagoClientUtils.checkBetaTester(uuid);

            Services.NETWORK.sendToServer(ServerboundChangeVipDataPacket.class, ServerboundChangeVipDataPacket.toBytes(uuid, displayBeta, LayersConfig.BETA_CHOICE.get(), displayDev));
        }
    }


    public static boolean checkBetaTester(UUID uuid)
    {
        return isVip(uuid, "beta");
    }

    public static boolean checkDev(UUID uuid)
    {
        return isVip(uuid, "dev");
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

    public static void startAnimation(KeyframeAnimation startAnim, @Nullable KeyframeAnimation goAnim, AbstractClientPlayer player, FirstPersonMode firstPersonMode)
    {
        var animation = MinejagoPlayerAnimator.animationData.get(player);
        //Get the animation for that player
        if (animation != null) {
            //You can set an animation from anywhere ON THE CLIENT
            //Do not attempt to do this on a server, that will only fail
            animation.setAnimation(new KeyframeAnimationPlayer(startAnim).setFirstPersonMode(firstPersonMode));
            if (goAnim != null)
                animation.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(20, Ease.CONSTANT), new KeyframeAnimationPlayer(goAnim).setFirstPersonMode(firstPersonMode));
        }

    }

    public static void stopAnimation(AbstractClientPlayer player)
    {
        if (Services.PLATFORM.isModLoaded(Minejago.Dependencies.PLAYER_ANIMATOR.getModId()))
        {
            var animation = MinejagoPlayerAnimator.animationData.get(player);
            animation.setAnimation(null);
        }
    }
}
