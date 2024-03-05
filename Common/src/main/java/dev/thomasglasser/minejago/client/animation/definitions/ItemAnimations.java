package dev.thomasglasser.minejago.client.animation.definitions;

import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.data.gson.AnimationSerializing;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class ItemAnimations
{
    private static final List<KeyframeAnimation> SCYTHE_OF_QUAKES = AnimationSerializing.deserializeAnimation(new InputStreamReader(Objects.requireNonNull(ItemAnimations.class.getClassLoader().getResourceAsStream("assets/minejago/animations/player/scythe_of_quakes.animation.json"))));

    public enum ScytheOfQuakes
    {
        EMPTY(null),
        SLAM_START(SCYTHE_OF_QUAKES.get(0)),
        SLAM_RUMBLE(SCYTHE_OF_QUAKES.get(1)),
        BEAM_START(SCYTHE_OF_QUAKES.get(2)),
        BEAM_ACTIVE(SCYTHE_OF_QUAKES.get(3));

        final KeyframeAnimation animation;

        ScytheOfQuakes(KeyframeAnimation animation) {
            this.animation = animation;
        }

        public KeyframeAnimation getAnimation() {
            return animation;
        }
    }

}
