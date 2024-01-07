package dev.thomasglasser.minejago.world.level.storage;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import dev.thomasglasser.minejago.world.focus.FocusData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;

public class FocusComponent implements ComponentV3, AutoSyncedComponent, PlayerComponent<FocusComponent>
{
	private FocusData focusData = new FocusData();

	public FocusData getFocusData()
	{
		return focusData;
	}

	public void setFocusData(FocusData focusData)
	{
		this.focusData = focusData;
	}

	@Override
	public void readFromNbt(CompoundTag tag)
	{
		focusData = FocusData.CODEC.decode(NbtOps.INSTANCE, tag.get("FocusData")).get().orThrow().getFirst();
	}

	@Override
	public void writeToNbt(CompoundTag tag)
	{
		tag.put("FocusData", FocusData.CODEC.encodeStart(NbtOps.INSTANCE, focusData).result().orElseThrow());
	}

	@Override
	public boolean shouldCopyForRespawn(boolean lossless, boolean keepInventory, boolean sameCharacter) {
		return lossless;
	}
}