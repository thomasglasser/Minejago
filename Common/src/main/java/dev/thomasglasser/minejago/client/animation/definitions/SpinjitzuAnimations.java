package dev.thomasglasser.minejago.client.animation.definitions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.data.gson.AnimationSerializing;
import dev.kosmx.playerAnim.core.data.gson.GeckoLibSerializer;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class SpinjitzuAnimations {
    private static final List<KeyframeAnimation> SPINJITZU = AnimationSerializing.deserializeAnimation(new InputStreamReader(Objects.requireNonNull(ItemAnimations.class.getClassLoader().getResourceAsStream("assets/minejago/animations/spinjitzu.animation.json"))));

    public enum Animations
    {
        SPINJITZU_START(SPINJITZU.get(0)),
        SPINJITZU_ACTIVE(SPINJITZU.get(1)),
        SPINJITZU_WOBBLE(SPINJITZU.get(2));

        private final KeyframeAnimation animation;

        Animations(KeyframeAnimation animation)
        {
            this.animation = animation;
        }

        public KeyframeAnimation getAnimation() {
            return animation;
        }
    }
}
