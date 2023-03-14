package dev.thomasglasser.minejago.mixin.minecraft.world.item;

import dev.thomasglasser.minejago.world.item.IModeledItem;
import dev.thomasglasser.minejago.world.item.armor.IGeoArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.function.Consumer;

@Mixin(Item.class)
public class ItemMixin
{
    @Inject(method = "initializeClient", at = @At("TAIL"), remap = false)
    void initializeClient(Consumer<IClientItemExtensions> consumer, CallbackInfo ci)
    {
        if (this instanceof IModeledItem iModeledItem && this instanceof IGeoArmorItem iGeoArmorItem)
        {
            consumer.accept(new IClientItemExtensions() {
                private BlockEntityWithoutLevelRenderer bewlr;

                @Override
                public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                    if (this.bewlr == null)
                        this.bewlr = iModeledItem.getBEWLR();

                    return this.bewlr;
                }

                GeoArmorRenderer<?> renderer;

                @Override
                public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                    if (renderer == null) renderer = iGeoArmorItem.newRenderer();

                    this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                    return renderer;
                }
            });
        }
        else if (this instanceof IModeledItem iModeledItem)
        {
            consumer.accept(new IClientItemExtensions() {
                private BlockEntityWithoutLevelRenderer bewlr;

                @Override
                public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                    if (this.bewlr == null)
                        this.bewlr = iModeledItem.getBEWLR();

                    return this.bewlr;
                }


            });
        }
        else if (this instanceof IGeoArmorItem iGeoArmorItem)
        {
            consumer.accept(new IClientItemExtensions() {
                GeoArmorRenderer<?> renderer;

                @Override
                public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                    if (renderer == null) renderer = iGeoArmorItem.newRenderer();

                    this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                    return renderer;
                }
            });
        }
    }
}
