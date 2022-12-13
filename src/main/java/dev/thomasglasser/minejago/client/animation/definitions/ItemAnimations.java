package dev.thomasglasser.minejago.client.animation.definitions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.data.gson.GeckoLibSerializer;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class ItemAnimations
{
    private static final List<KeyframeAnimation> SCYTHE_OF_QUAKES = GeckoLibSerializer.serialize((JsonObject) JsonParser.parseReader(new InputStreamReader(Objects.requireNonNull(ItemAnimations.class.getClassLoader().getResourceAsStream("assets/minejago/animations/scythe_of_quakes.animation.json")), StandardCharsets.UTF_8)));

    public enum Animations
    {
        EMPTY(null),
        SLAM_START(SCYTHE_OF_QUAKES.get(0)),
        SLAM_RUMBLE(SCYTHE_OF_QUAKES.get(1)),
        BEAM_START(SCYTHE_OF_QUAKES.get(2)),
        BEAM_ACTIVE(SCYTHE_OF_QUAKES.get(3));

        final KeyframeAnimation animation;

        Animations(KeyframeAnimation animation) {
            this.animation = animation;
        }

        public KeyframeAnimation getAnimation() {
            return animation;
        }
    }

}
