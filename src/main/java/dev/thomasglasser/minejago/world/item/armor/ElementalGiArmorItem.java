package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.tommylib.api.client.renderer.BewlrProvider;
import dev.thomasglasser.tommylib.api.world.item.ModeledItem;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public abstract class ElementalGiArmorItem extends GiGeoArmorItem implements ModeledItem {
    public ElementalGiArmorItem(Holder<ArmorMaterial> pMaterial, ArmorItem.Type type, Properties pProperties) {
        super(pMaterial, type, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        HolderLookup.Provider registries = tooltipContext.registries();
        ResourceKey<Element> elementKey = itemStack.get(MinejagoDataComponents.ELEMENT.get());
        if (registries != null && elementKey != null) {
            registries.lookupOrThrow(MinejagoRegistries.ELEMENT).get(elementKey).ifPresent(element -> {
                MutableComponent component = Component.translatable(elementKey.location().toLanguageKey(elementKey.registry().getPath()));
                component.setStyle(component.getStyle().withColor(element.value().color()).withItalic(true));
                list.add(component);
            });
        }
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
    }

    @Override
    public void createBewlrProvider(Consumer<BewlrProvider> consumer) {
        consumer.accept(new BewlrProvider() {
            @Override
            public BlockEntityWithoutLevelRenderer getBewlr() {
                return MinejagoClientUtils.getBewlr();
            }
        });
    }
}
