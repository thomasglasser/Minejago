package dev.thomasglasser.minejago.world.level.block.component;

import dev.thomasglasser.minejago.plugins.MinejagoWailaPlugin;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.minejago.world.level.block.entity.TeapotBlockEntity;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.impl.ui.ElementHelper;

public enum TeapotBlockComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getBlockEntity() instanceof TeapotBlockEntity be && blockAccessor.getBlock() instanceof TeapotBlock)
        {
            Holder<Potion> potion = be.getPotion();
            if (potion != null)
            {
                ItemStack potionStack = PotionContents.createItemStack(MinejagoItems.FILLED_TEACUP.get(), potion);
                IElement icon = ElementHelper.INSTANCE.item(potionStack, 0.5f).translate(new Vec2(0, -2));
                iTooltip.add(icon);
                iTooltip.append(Component.translatable("block.minejago.teapot.waila.potion", Component.translatable(PotionContents.createItemStack(Items.POTION, be.getPotion()).getDescriptionId())));
            }

            ItemStack item = be.getInSlot(0);
            if (!item.isEmpty())
            {
                IElement icon = ElementHelper.INSTANCE.item(item, 0.5f).translate(new Vec2(0, -2));
                iTooltip.add(icon);
                iTooltip.append(Component.translatable("block.minejago.teapot.waila.item", Component.translatable(item.getDescriptionId())));
            }

            int cups = be.getCups();
            if (cups > 0)
            {
                IElement icon = ElementHelper.INSTANCE.item(MinejagoItems.TEACUP.get().getDefaultInstance(), 0.5f).translate(new Vec2(0, -2));
                iTooltip.add(icon);
                iTooltip.append(Component.translatable("block.minejago.teapot.waila.cups", cups));
            }

            int time = be.getBrewTime();
            if (time > 0)
            {
                IElement icon = ElementHelper.INSTANCE.item(Items.CLOCK.getDefaultInstance(), 0.5f).translate(new Vec2(0, -2));
                iTooltip.add(icon);
                iTooltip.append(Component.translatable("block.minejago.teapot.waila.time", time));
            }

            if (cups > 0)
            {
                float temp = be.getTemperature();
                IElement icon = ElementHelper.INSTANCE.item(Items.CAMPFIRE.getDefaultInstance(), 0.5f).translate(new Vec2(0, -2));
                iTooltip.add(icon);
                iTooltip.append(Component.translatable("block.minejago.teapot.waila.temp", temp));
            }
            else
            {
                iTooltip.add(Component.translatable("block.minejago.teapot.waila.empty"));
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return MinejagoWailaPlugin.TEAPOT_BLOCK;
    }
}
