package dev.thomasglasser.minejago.client.animation.definitions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.data.gson.GeckoLibSerializer;
import dev.kosmx.playerAnim.core.util.Ease;
import dev.thomasglasser.minejago.client.animation.MinejagoPlayerAnimator;
import net.minecraft.client.Minecraft;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class SpinjitzuAnimation {

    public static final List<KeyframeAnimation> ANIMATIONS = GeckoLibSerializer.serialize((JsonObject) JsonParser.parseReader(new InputStreamReader(Objects.requireNonNull(SpinjitzuAnimation.class.getClassLoader().getResourceAsStream("assets/minejago/animations/spinjitzu.animation.json")), StandardCharsets.UTF_8)));

    public static void startAnimation()
    {
        //Get the player from Minecraft, using the chat profile ID. From network packets, you'll receive entity IDs instead of UUIDs
        var player = Minecraft.getInstance().level.getPlayerByUUID(Minecraft.getInstance().player.getUUID());

        if (player == null) return; //The player can be null because it was a system message or because it is not loaded by this player.

        var animation = MinejagoPlayerAnimator.animationData.get(Minecraft.getInstance().player);
        //Get the animation for that player
        if (animation != null) {
            //You can set an animation from anywhere ON THE CLIENT
            //Do not attempt to do this on a server, that will only fail
            animation.setAnimation(new KeyframeAnimationPlayer(ANIMATIONS.get(0)));
            animation.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(30, Ease.CONSTANT), new KeyframeAnimationPlayer(ANIMATIONS.get(1)));
        }
    }

    public static void stopAnimation()
    {
        var animation = MinejagoPlayerAnimator.animationData.get(Minecraft.getInstance().player);
        animation.setAnimation(null);
        Minecraft.getInstance().player.getPersistentData().putBoolean("StartedSpinjitzu", false);
    }
}
