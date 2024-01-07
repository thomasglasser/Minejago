package dev.thomasglasser.minejago.world.level.storage;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;

public class SpinjitzuComponent implements ComponentV3, AutoSyncedComponent, PlayerComponent<SpinjitzuComponent>
{
	private SpinjitzuData spinjitzuData = new SpinjitzuData();

	public SpinjitzuData getSpinjitzuData()
	{
		return spinjitzuData;
	}

	public void setSpinjitzuData(SpinjitzuData spinjitzuData)
	{
		this.spinjitzuData = spinjitzuData;
	}

	@Override
	public void readFromNbt(CompoundTag tag)
	{
		spinjitzuData = SpinjitzuData.CODEC.decode(NbtOps.INSTANCE, tag.get("SpinjitzuData")).get().orThrow().getFirst();
	}

	@Override
	public void writeToNbt(CompoundTag tag)
	{
		tag.put("SpinjitzuData", SpinjitzuData.CODEC.encodeStart(NbtOps.INSTANCE, spinjitzuData).result().orElseThrow());
	}

	@Override
	public boolean shouldCopyForRespawn(boolean lossless, boolean keepInventory, boolean sameCharacter) {
		return lossless || keepInventory;
	}
}
