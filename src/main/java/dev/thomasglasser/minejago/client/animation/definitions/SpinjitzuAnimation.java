package dev.thomasglasser.minejago.client.animation.definitions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.InputConstants;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.data.gson.GeckoLibSerializer;
import dev.kosmx.playerAnim.core.util.Ease;
import dev.thomasglasser.minejago.client.animation.MinejagoPlayerAnimator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static dev.thomasglasser.minejago.MinejagoMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class SpinjitzuAnimation {

    public static final List<KeyframeAnimation> ANIMATIONS = GeckoLibSerializer.serialize((JsonObject) JsonParser.parseReader(new InputStreamReader(Objects.requireNonNull(SpinjitzuAnimation.class.getClassLoader().getResourceAsStream("assets/minejago/animations/spinjitzu.animation.json")), StandardCharsets.UTF_8)));

    @SubscribeEvent
    public static void onChatReceived(ClientChatReceivedEvent event) {
        //Test if it is a player (main or other) and the message
        if (!event.getPlayerChatMessage().signer().isSystem() && event.getPlayerChatMessage().signedContent().plain().equals("Ninja, go!")) {


            //Get the player from Minecraft, using the chat profile ID. From network packets, you'll receive entity IDs instead of UUIDs
            var player = Minecraft.getInstance().level.getPlayerByUUID(event.getPlayerChatMessage().signer().profileId());

            if (player == null) return; //The player can be null because it was a system message or because it is not loaded by this player.

            var animation = MinejagoPlayerAnimator.animationData.get(Minecraft.getInstance().player);
            //Get the animation for that player
            if (animation != null) {
                //You can set an animation from anywhere ON THE CLIENT
                //Do not attempt to do this on a server, that will only fail
                animation.setAnimation(new KeyframeAnimationPlayer(ANIMATIONS.get(0)));
                animation.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(15, Ease.CONSTANT), new KeyframeAnimationPlayer(ANIMATIONS.get(1)));
                //You might use  animation.replaceAnimationWithFade(); to create fade effect instead of sudden change
                //See javadoc for details
            }
        }
    }

    @SubscribeEvent
    public static void onKeyPressed(InputEvent.Key event)
    {
        if (event.getKey() == InputConstants.KEY_LSHIFT)
        {
            var animation = MinejagoPlayerAnimator.animationData.get(Minecraft.getInstance().player);
            animation.setAnimation(null);

            //TODO: Disable Spinjitzu
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.PlayerTickEvent event)
    {
        if (event.side == LogicalSide.CLIENT)
        {
            //TODO: Render spinjitzu if active
        }
    }
}
