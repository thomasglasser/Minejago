package dev.thomasglasser.minejago.world.focus;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;

import static dev.thomasglasser.minejago.world.focus.FocusConstants.EXHAUSTION_DROP;
import static dev.thomasglasser.minejago.world.focus.FocusConstants.FOCUS_SATURATION_MAX;
import static dev.thomasglasser.minejago.world.focus.FocusConstants.MAX_FOCUS;
import static dev.thomasglasser.minejago.world.focus.FocusConstants.MAX_MEGA_FOCUS;
import static dev.thomasglasser.minejago.world.focus.FocusConstants.START_FOCUS;
import static dev.thomasglasser.minejago.world.focus.FocusConstants.START_SATURATION;

public class FocusData
{
	private int focusLevel = START_FOCUS;
	private float saturationLevel = START_SATURATION;
	private float exhaustionLevel;
	private int lastFocusLevel = START_FOCUS;
	private MeditationType meditationType = MeditationType.NONE;

	/**
	 * Add stats.
	 */
	public void increase(boolean mega, int focusLevelModifier, float saturationLevelModifier) {
		this.focusLevel = Math.min(focusLevelModifier + this.focusLevel, mega ? MAX_MEGA_FOCUS : MAX_FOCUS);
		this.saturationLevel = Math.min(this.saturationLevel + (float) focusLevelModifier * saturationLevelModifier * (mega ? 4.0F : 2.0F), (float) this.focusLevel);
	}

	/**
	 * Handles the focus game logic.
	 */
	public void tick(Player player) {
		Difficulty difficulty = player.level().getDifficulty();
		this.lastFocusLevel = this.focusLevel;
		if (this.exhaustionLevel > EXHAUSTION_DROP)
		{
			this.exhaustionLevel -= EXHAUSTION_DROP;
			if (this.saturationLevel > 0)
			{
				this.saturationLevel = Math.max(this.saturationLevel - FOCUS_SATURATION_MAX, 0);
			}
			else if (difficulty != Difficulty.PEACEFUL)
			{
				this.focusLevel = Math.max(this.focusLevel - 1, 0);
			}
		}
	}

	public boolean canMegaMeditate(Player player)
	{
		return focusLevel >= 20 && player.getHealth() >= player.getMaxHealth() && !player.getFoodData().needsFood() && isMeditating();
	}

	/**
	 * Reads the focus data for the player.
	 */
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		if (compoundTag.contains("focusLevel", 99)) {
			this.focusLevel = compoundTag.getInt("focusLevel");
			this.saturationLevel = compoundTag.getFloat("focusSaturationLevel");
			this.exhaustionLevel = compoundTag.getFloat("focusExhaustionLevel");
		}
	}

	/**
	 * Writes the focus data for the player.
	 */
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		compoundTag.putInt("focusLevel", this.focusLevel);
		compoundTag.putFloat("focusSaturationLevel", this.saturationLevel);
		compoundTag.putFloat("focusExhaustionLevel", this.exhaustionLevel);
	}

	/**
	 * Get the player's focus level.
	 */
	public int getFocusLevel() {
		return this.focusLevel;
	}

	public int getLastFocusLevel() {
		return this.lastFocusLevel;
	}

	/**
	 * Get whether the player must eat focus.
	 */
	public boolean needsFocus() {
		return this.focusLevel < MAX_FOCUS;
	}

	/**
	 * Adds input to {@code focusExhaustionLevel} to a max of 40.
	 */
	public void addExhaustion(float exhaustion) {
		this.exhaustionLevel = Math.min(this.exhaustionLevel + exhaustion, 40.0F);
	}

	public float getExhaustionLevel() {
		return this.exhaustionLevel;
	}

	/**
	 * Get the player's focus saturation level.
	 */
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

	public MeditationType getMeditationType()
	{
		return meditationType;
	}

	public void setMeditationType(MeditationType meditationType)
	{
		this.meditationType = meditationType;
	}

	public boolean isMeditating()
	{
		return meditationType != MeditationType.NONE;
	}

	public boolean isMegaMeditating()
	{
		return meditationType == MeditationType.MEGA;
	}

	public boolean isNormalMeditating()
	{
		return  meditationType == MeditationType.NORMAL;
	}

	public void startMeditating()
	{
		setMeditationType(MeditationType.NORMAL);
	}

	public void startMegaMeditating()
	{
		setMeditationType(MeditationType.MEGA);
	}

	public void stopMeditating()
	{
		setMeditationType(MeditationType.NONE);
	}

	public enum MeditationType
	{
		NONE,
		NORMAL,
		MEGA
	}
}
