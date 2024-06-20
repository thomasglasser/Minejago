package dev.thomasglasser.minejago.world.entity.component;

import dev.thomasglasser.minejago.plugins.MinejagoWailaPlugin;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.impl.ui.ElementHelper;

public enum PaintingComponentProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
    INSTANCE;
    
    @Override
    public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        if (((Painting)entityAccessor.getEntity()).getVariant().is(MinejagoPaintingVariants.FOUR_WEAPONS) && !entityAccessor.getServerData().getBoolean("MapTaken"))
        {
            IElement icon = ElementHelper.INSTANCE.item(Items.MAP.getDefaultInstance(), 0.5f).translate(new Vec2(0, -2));
            iTooltip.add(icon);
            iTooltip.append(Component.translatable("entity.minejago.painting.waila.map"));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return MinejagoWailaPlugin.PAINTING;
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, EntityAccessor accessor) {
        if (accessor.getEntity() instanceof Painting painting)
        {
            CompoundTag tag = TommyLibServices.ENTITY.getPersistentData(painting);
            compoundTag.putBoolean("MapTaken", tag.getBoolean("MapTaken"));
        }
    }
}
