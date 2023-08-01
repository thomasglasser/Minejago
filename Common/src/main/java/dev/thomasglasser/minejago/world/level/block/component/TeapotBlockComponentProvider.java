package dev.thomasglasser.minejago.world.level.block.component;

import dev.thomasglasser.minejago.client.MinejagoWailaPlugin;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.minejago.world.level.block.entity.TeapotBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public enum TeapotBlockComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getBlockEntity() instanceof TeapotBlockEntity be && blockAccessor.getBlock() instanceof TeapotBlock block)
        {
            IElementHelper elementHelper = iTooltip.getElementHelper();
            Potion potion = be.getPotion();
            if (potion != Potions.EMPTY)
            {
                ItemStack potionStack = PotionUtils.setPotion(MinejagoItems.FILLED_TEACUP.get().getDefaultInstance(), potion);
                IElement icon = elementHelper.item(potionStack, 0.5f).translate(new Vec2(0, -2));
                iTooltip.add(icon);
                iTooltip.append(Component.translatable("block.minejago.teapot.waila.potion", potionStack.getItem().getName(potionStack)));
            }

            ItemStack item = be.getInSlot(0);
            if (!item.isEmpty())
            {
                IElement icon = elementHelper.item(item, 0.5f).translate(new Vec2(0, -2));
                iTooltip.add(icon);
                iTooltip.append(Component.translatable("block.minejago.teapot.waila.item", Component.translatable(item.getDescriptionId())));
            }

            int cups = be.getCups();
            if (cups > 0)
            {
                IElement icon = elementHelper.item(MinejagoItems.TEACUP.get().getDefaultInstance(), 0.5f).translate(new Vec2(0, -2));
                iTooltip.add(icon);
                iTooltip.append(Component.translatable("block.minejago.teapot.waila.cups", cups));
            }

            int time = be.getBrewTime();
            if (time > 0)
            {
                IElement icon = elementHelper.item(Items.CLOCK.getDefaultInstance(), 0.5f).translate(new Vec2(0, -2));
                iTooltip.add(icon);
                iTooltip.append(Component.translatable("block.minejago.teapot.waila.time", time));
            }

            if (cups > 0)
            {
                float temp = be.getTemperature();
                IElement icon = elementHelper.item(Items.CAMPFIRE.getDefaultInstance(), 0.5f).translate(new Vec2(0, -2));
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
