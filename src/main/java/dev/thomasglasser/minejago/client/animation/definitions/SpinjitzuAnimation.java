package dev.thomasglasser.minejago.client.animation.definitions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.data.gson.GeckoLibSerializer;
import dev.kosmx.playerAnim.core.util.Ease;
import dev.thomasglasser.minejago.client.animation.MinejagoPlayerAnimator;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class SpinjitzuAnimation {

    public static final List<KeyframeAnimation> ANIMATIONS = GeckoLibSerializer.serialize((JsonObject) JsonParser.parseReader(new InputStreamReader(Objects.requireNonNull(SpinjitzuAnimation.class.getClassLoader().getResourceAsStream("assets/minejago/animations/spinjitzu.animation.json")), StandardCharsets.UTF_8)));

    public static void startAnimation(Player player)
    {
        if (player instanceof AbstractClientPlayer clientPlayer)
        {
            var animation = MinejagoPlayerAnimator.animationData.get(clientPlayer);
            //Get the animation for that player
            if (animation != null) {
                //You can set an animation from anywhere ON THE CLIENT
                //Do not attempt to do this on a server, that will only fail
                animation.setAnimation(new KeyframeAnimationPlayer(ANIMATIONS.get(0)));
                animation.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(30, Ease.CONSTANT), new KeyframeAnimationPlayer(ANIMATIONS.get(1)));
            }
        }
    }

    public static void stopAnimation(Player player)
    {
        if (player instanceof AbstractClientPlayer clientPlayer)
        {
            var animation = MinejagoPlayerAnimator.animationData.get(clientPlayer);
            animation.setAnimation(null);
            clientPlayer.getPersistentData().putBoolean("StartedSpinjitzu", false);
        }    }
}
