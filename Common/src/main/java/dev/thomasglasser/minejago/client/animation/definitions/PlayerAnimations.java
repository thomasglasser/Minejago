package dev.thomasglasser.minejago.client.animation.definitions;

import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.data.gson.AnimationSerializing;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class PlayerAnimations
{
    private static final List<KeyframeAnimation> SPINJITZU = AnimationSerializing.deserializeAnimation(new InputStreamReader(Objects.requireNonNull(PlayerAnimations.class.getClassLoader().getResourceAsStream("assets/minejago/animations/player/spinjitzu.animation.json"))));
    // TODO: Meditation file
//    private static final List<KeyframeAnimation> MEDITATION = AnimationSerializing.deserializeAnimation(new InputStreamReader(Objects.requireNonNull(PlayerAnimations.class.getClassLoader().getResourceAsStream("assets/minejago/animations/player/meditation.animation.json"))));

    public enum Spinjitzu
    {
        START(SPINJITZU.get(0)),
        ACTIVE(SPINJITZU.get(1)),
        WOBBLE(SPINJITZU.get(2))/*,
        STOP(SPINJITZU.get(3))*/; // TODO: Stop anim

        private final KeyframeAnimation animation;

        Spinjitzu(KeyframeAnimation animation)
        {
            this.animation = animation;
        }

        public KeyframeAnimation getAnimation() {
            return animation;
        }
    }

    public enum Meditation // TODO: Meditation animations
    {
        /*START(MEDITATION.get(0)),
        ACTIVE(MEDITATION.get(1)),
        WOBBLE(MEDITATION.get(2)),
        STOP(SPINJITZU.get(3))*/;

        private final KeyframeAnimation animation;

        Meditation(KeyframeAnimation animation)
        {
            this.animation = animation;
        }

        public KeyframeAnimation getAnimation() {
            return animation;
        }
    }
}
