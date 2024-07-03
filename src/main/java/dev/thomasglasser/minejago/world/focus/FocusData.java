package dev.thomasglasser.minejago.world.focus;

import static dev.thomasglasser.minejago.world.focus.FocusConstants.EXHAUSTION_DROP;
import static dev.thomasglasser.minejago.world.focus.FocusConstants.MAX_FOCUS;
import static dev.thomasglasser.minejago.world.focus.FocusConstants.MAX_MEGA_FOCUS;
import static dev.thomasglasser.minejago.world.focus.FocusConstants.MIN_MEGA_FOCUS;
import static dev.thomasglasser.minejago.world.focus.FocusConstants.START_SATURATION;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;

public class FocusData {
    public static final Codec<FocusData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("focus_level").forGetter(FocusData::getFocusLevel),
            Codec.FLOAT.fieldOf("saturation_level").forGetter(FocusData::getSaturationLevel),
            Codec.FLOAT.fieldOf("exhaustion_level").forGetter(FocusData::getExhaustionLevel)).apply(instance, FocusData::new));

    private int focusLevel = MAX_FOCUS;
    private float saturationLevel = START_SATURATION;
    private float exhaustionLevel;
    private int lastFocusLevel = MAX_FOCUS;
    private MeditationType meditationType = MeditationType.NONE;

    public FocusData() {}

    public FocusData(int focusLevel, float saturationLevel, float exhaustionLevel) {
        this.focusLevel = focusLevel;
        this.saturationLevel = saturationLevel;
        this.exhaustionLevel = exhaustionLevel;
    }

    private void add(boolean mega, int newFocus, float newSaturation) {
        this.focusLevel = Math.min(newFocus + this.focusLevel, mega ? MAX_MEGA_FOCUS : MAX_FOCUS);
        this.saturationLevel = Math.min((mega ? (newSaturation * 2.0F) : newSaturation) + this.saturationLevel, (float) this.focusLevel);
    }

    public void meditate(boolean mega, int focusLevelModifier, float saturationLevelModifier) {
        float saturation = FocusConstants.saturationByModifier(mega, focusLevelModifier, saturationLevelModifier);
        this.add(mega, focusLevelModifier, saturation);
    }

    public void tick(Player player) {
        Difficulty difficulty = player.level().getDifficulty();
        this.lastFocusLevel = this.focusLevel;
        if (this.exhaustionLevel > EXHAUSTION_DROP) {
            this.exhaustionLevel -= EXHAUSTION_DROP;
            if (this.saturationLevel > 0) {
                this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
            } else if (difficulty != Difficulty.PEACEFUL) {
                this.focusLevel = Math.max(this.focusLevel - 1, 0);
            }
        }
    }

    public boolean canMegaMeditate(Player player) {
        return focusLevel >= MIN_MEGA_FOCUS && player.getHealth() >= player.getMaxHealth() && !player.getFoodData().needsFood() && isMeditating();
    }

    public int getFocusLevel() {
        return this.focusLevel;
    }

    public int getLastFocusLevel() {
        return this.lastFocusLevel;
    }

    public boolean needsFocus() {
        return this.focusLevel < MAX_FOCUS;
    }

    public void addExhaustion(float exhaustion) {
        this.exhaustionLevel = Math.min(this.exhaustionLevel + exhaustion, 40.0F);
    }

    public float getExhaustionLevel() {
        return this.exhaustionLevel;
    }

    public float getSaturationLevel() {
        return this.saturationLevel;
    }

    public void setFocusLevel(int focusLevel) {
        this.focusLevel = focusLevel;
    }

    public void setSaturation(float saturationLevel) {
        this.saturationLevel = saturationLevel;
    }

    public void setExhaustion(float exhaustionLevel) {
        this.exhaustionLevel = exhaustionLevel;
    }

    public void setMeditationType(MeditationType meditationType) {
        this.meditationType = meditationType;
    }

    public boolean isMeditating() {
        return meditationType != MeditationType.NONE;
    }

    public boolean isMegaMeditating() {
        return meditationType == MeditationType.MEGA;
    }

    public boolean isNormalMeditating() {
        return meditationType == MeditationType.NORMAL;
    }

    public void startMeditating() {
        setMeditationType(MeditationType.NORMAL);
    }

    public void startMegaMeditating() {
        setMeditationType(MeditationType.MEGA);
    }

    public void stopMeditating() {
        setMeditationType(MeditationType.NONE);
    }

    public enum MeditationType {
        NONE,
        NORMAL,
        MEGA
    }
}
