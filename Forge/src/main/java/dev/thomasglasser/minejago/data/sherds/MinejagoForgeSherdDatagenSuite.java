package dev.thomasglasser.minejago.data.sherds;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.sherdsapi.api.data.ForgeSherdDatagenSuite;
import dev.thomasglasser.sherdsapi.impl.Sherd;
import dev.thomasglasser.sherdsapi.impl.SherdsApiRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.data.event.GatherDataEvent;

public class MinejagoForgeSherdDatagenSuite extends ForgeSherdDatagenSuite
{
	public MinejagoForgeSherdDatagenSuite(GatherDataEvent event)
	{
		super(event, Minejago.MOD_ID);
	}

	@Override
	public void generate()
	{
		MinejagoItems.SHERDS.forEach(rl ->
				makeSherdSuite(key(rl), new Sherd(rl, rl)));
	}

	private ResourceKey<Sherd> key(ResourceLocation rl)
	{
		return ResourceKey.create(SherdsApiRegistries.SHERD, rl);
	}
}
