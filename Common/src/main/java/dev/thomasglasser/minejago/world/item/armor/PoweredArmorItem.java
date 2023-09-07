package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.client.renderer.MinejagoBlockEntityWithoutLevelRenderer;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.item.ModeledItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class PoweredArmorItem extends ArmorItem implements GeoArmorItem, ModeledItem
{
    BlockEntityWithoutLevelRenderer bewlr;

    public PoweredArmorItem(ArmorMaterial pMaterial, Type type, Properties pProperties) {
        super(pMaterial, type, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        if (level != null && stack.getOrCreateTag().contains("Power"))
        {
            ResourceLocation location = ResourceLocation.of(stack.getOrCreateTag().getString("Power"), ':');
            Power power = MinejagoPowers.POWERS.get(level.registryAccess()).get(location);
            if (power != null)
            {
                MutableComponent component = Component.translatable(location.toLanguageKey("power"));
                component.setStyle(component.getStyle().withColor(power.getColor()).withItalic(true));
                tooltipComponents.add(component);
            }
        }
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    @Override
    public BlockEntityWithoutLevelRenderer getBEWLR() {
        if (bewlr == null) bewlr = new MinejagoBlockEntityWithoutLevelRenderer();
        return bewlr;
    }

    @Override
    public boolean isGi() {
        return true;
    }
}
