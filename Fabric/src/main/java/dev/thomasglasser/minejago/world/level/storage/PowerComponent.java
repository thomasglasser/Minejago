package dev.thomasglasser.minejago.world.level.storage;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;

public class PowerComponent implements ComponentV3, AutoSyncedComponent, PlayerComponent<PowerComponent>
{
	private PowerData powerData = new PowerData();

	public PowerData getPowerData()
	{
		return powerData;
	}

	public void setPowerData(PowerData powerData)
	{
		this.powerData = powerData;
	}

	@Override
	public void readFromNbt(CompoundTag tag)
	{
		powerData = PowerData.CODEC.decode(NbtOps.INSTANCE, tag.get("PowerData")).get().orThrow().getFirst();
	}

	@Override
	public void writeToNbt(CompoundTag tag)
	{
		tag.put("PowerData", PowerData.CODEC.encodeStart(NbtOps.INSTANCE, powerData).result().orElseThrow());
	}

	@Override
	public boolean shouldCopyForRespawn(boolean lossless, boolean keepInventory, boolean sameCharacter) {
		return lossless || keepInventory;
	}
}
