package dev.thomasglasser.minejago.mixin.net.minecraft.world.item;

import dev.thomasglasser.minejago.world.item.ITeapotLiquidHolder;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BucketItem.class)
public class MinejagoBucketItemMixin implements ITeapotLiquidHolder {
    @Override
    public int getCups() {
        return 6;
    }

    @Override
    public ItemStack getDrained(ItemStack stack) {
        return new ItemStack(Items.BUCKET);
    }

    @Override
    public Potion getPotion(ItemStack stack) {
        if (stack.is(Items.WATER_BUCKET))
        {
            return Potions.WATER;
        }
        return null;
    }
}
