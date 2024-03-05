package dev.thomasglasser.minejago.world.entity.component;

import dev.thomasglasser.minejago.client.MinejagoWailaPlugin;
import dev.thomasglasser.minejago.world.entity.dragon.Dragon;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum DragonComponentProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor>
{
	INSTANCE;

	@Override
	public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig)
	{
		if (entityAccessor.getEntity() instanceof Dragon dragon)
		{
			iTooltip.add(Component.translatable("entity.minejago.dragon.waila.bond", String.format("%.2f", entityAccessor.getServerData().getCompound("Bonds").getDouble(ClientUtils.getMainClientPlayer().getStringUUID()))));
		}
	}

	@Override
	public ResourceLocation getUid()
	{
		return MinejagoWailaPlugin.DRAGON;
	}

	@Override
	public void appendServerData(CompoundTag compoundTag, EntityAccessor accessor) {
		if (accessor.getEntity() instanceof Dragon dragon)
		{
			dragon.getBondMap().forEach((player, aDouble) ->
			{
				CompoundTag tag = new CompoundTag();
				tag.putDouble(player.getStringUUID(), aDouble);
				compoundTag.put("Bonds", tag);
			});
		}
	}
}
