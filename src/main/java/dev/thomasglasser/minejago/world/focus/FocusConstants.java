package dev.thomasglasser.minejago.world.focus;

public class FocusConstants {
    public static final int MAX_FOCUS = 20;
    public static final int MIN_MEGA_FOCUS = 20;
    public static final int MAX_MEGA_FOCUS = 40;
    public static final float MAX_SATURATION = 20.0F;
    public static final float START_SATURATION = 5.0F;
    public static final float SATURATION_FLOOR = 2.5F;
    public static final float EXHAUSTION_DROP = 4.0F;
    public static final int DRAGON_TAME_LEVEL = 18;
    public static final int DRAGON_TALK_LEVEL = 10;
    public static final int SPINJITZU_LEVEL = 6;
    public static final int LEARN_SPINJITZU_LEVEL = 6; // TODO: Add where learn
    public static final int GOLDEN_WEAPON_LEVEL = 4;
    public static final int DISARRAYED_LEVEL = 0;
    public static final float FOCUS_SATURATION_POOR = 0.1F;
    public static final float FOCUS_SATURATION_LOW = 0.3F;
    public static final float FOCUS_SATURATION_NORMAL = 0.6F;
    public static final float FOCUS_SATURATION_GOOD = 0.8F;
    public static final float FOCUS_SATURATION_MAX = 1.0F;
    public static final float FOCUS_SATURATION_SUPERNATURAL = 1.2F;
    public static final float EXHAUSTION_DRAGON_TAME = 6.0F;
    public static final float EXHAUSTION_DRAGON_TALK = 3.0F;
    public static final float EXHAUSTION_SPINJITZU = 0.1F;
    public static final float EXHAUSTION_INSOMNIA = 1.0F;
    public static final float EXHAUSTION_LEARN_SPINJITZU = 2.5F; // TODO: Add where learn
    public static final float EXHAUSTION_GOLDEN_WEAPON = 2.0F;

    public static float saturationByModifier(boolean mega, int i, float f) {
        float saturation = i * f * 2.0F;
        if (mega) {
            saturation *= 2.0F;
        }
        return saturation;
    }
}
