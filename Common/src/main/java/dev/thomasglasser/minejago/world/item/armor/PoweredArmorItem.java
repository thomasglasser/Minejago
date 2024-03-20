package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.tommylib.api.world.item.ModeledItem;
import dev.thomasglasser.tommylib.api.world.item.armor.BaseGeoArmorItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class PoweredArmorItem extends BaseGeoArmorItem implements GiGeoArmorItem, ModeledItem
{
    public PoweredArmorItem(ArmorMaterial pMaterial, Type type, Properties pProperties) {
        super(pMaterial, type, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        if (level != null && stack.getOrCreateTag().contains("Power"))
        {
            ResourceLocation location = ResourceLocation.of(stack.getOrCreateTag().getString("Power"), ':');
            Power power = level.registryAccess().registry(MinejagoRegistries.POWER).orElseThrow().get(location);
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
        return Minejago.getBewlr();
    }
}
