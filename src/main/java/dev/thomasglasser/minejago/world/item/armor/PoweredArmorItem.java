package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.tommylib.api.world.item.ModeledItem;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public abstract class PoweredArmorItem extends GiGeoArmorItem implements ModeledItem {
    public PoweredArmorItem(Holder<ArmorMaterial> pMaterial, Type type, Properties pProperties) {
        super(pMaterial, type, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        HolderLookup.Provider registries = tooltipContext.registries();
        if (registries != null && itemStack.has(MinejagoDataComponents.POWER.get())) {
            ResourceLocation key = itemStack.get(MinejagoDataComponents.POWER.get());
            Optional<Holder.Reference<Power>> power = registries.lookupOrThrow(MinejagoRegistries.POWER).get(ResourceKey.create(MinejagoRegistries.POWER, key));
            if (power.isPresent()) {
                MutableComponent component = Component.translatable(key.toLanguageKey("power"));
                component.setStyle(component.getStyle().withColor(power.get().value().getColor()).withItalic(true));
                list.add(component);
            }
        }
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
    }

    @Override
    public BlockEntityWithoutLevelRenderer getBEWLR() {
        return MinejagoClientUtils.getBewlr();
    }
}
