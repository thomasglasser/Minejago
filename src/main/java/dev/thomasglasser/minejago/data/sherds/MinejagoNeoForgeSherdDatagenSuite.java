package dev.thomasglasser.minejago.data.sherds;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.sherdsapi.api.data.NeoForgeSherdDatagenSuite;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class MinejagoNeoForgeSherdDatagenSuite extends NeoForgeSherdDatagenSuite
{
	public MinejagoNeoForgeSherdDatagenSuite(GatherDataEvent event)
	{
		super(event, Minejago.MOD_ID);
	}

	@Override
	public void generate()
	{
		MinejagoItems.SHERDS.forEach(string ->
				makeSherdSuite(string, BuiltInRegistries.ITEM.get(modLoc(string + "_pottery_sherd")), string + "_pottery_pattern"));
	}
}
