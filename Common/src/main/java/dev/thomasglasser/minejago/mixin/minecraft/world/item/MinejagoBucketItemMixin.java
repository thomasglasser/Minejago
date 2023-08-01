package dev.thomasglasser.minejago.mixin.minecraft.world.item;

import dev.thomasglasser.minejago.world.item.PotionCupHolder;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BucketItem.class)
public class MinejagoBucketItemMixin implements PotionCupHolder {
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

    @Override
    public boolean canBeFilled(ItemStack stack, Potion potion, int cups) {
        return (stack.is(Items.BUCKET) && (potion == Potions.WATER || potion == MinejagoPotions.MILK.get())) && cups >= getCups();
    }

    @Override
    public ItemStack getFilled(Potion potion) {
        return potion == Potions.WATER ? Items.WATER_BUCKET.getDefaultInstance() : potion == MinejagoPotions.MILK.get() ? Items.MILK_BUCKET.getDefaultInstance() : null;
    }

    @Override
    public boolean canBeDrained(ItemStack stack) {
        return stack.is(Items.WATER_BUCKET);
    }
}
