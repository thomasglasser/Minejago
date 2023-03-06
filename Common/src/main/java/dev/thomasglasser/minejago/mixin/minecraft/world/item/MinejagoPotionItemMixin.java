package dev.thomasglasser.minejago.mixin.minecraft.world.item;

import dev.thomasglasser.minejago.world.item.ITeapotLiquidHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PotionItem.class)
public class MinejagoPotionItemMixin implements ITeapotLiquidHolder {
    @Override
    public int getCups() {
        return 2;
    }

    @Override
    public ItemStack getDrained(ItemStack stack) {
        return new ItemStack(Items.GLASS_BOTTLE);
    }

    @Override
    public Potion getPotion(ItemStack stack) {
        return PotionUtils.getPotion(stack);
    }
}
