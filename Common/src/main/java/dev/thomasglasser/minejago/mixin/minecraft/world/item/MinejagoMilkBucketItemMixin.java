package dev.thomasglasser.minejago.mixin.minecraft.world.item;

import dev.thomasglasser.minejago.world.item.PotionCupHolder;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.item.alchemy.Potion;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MilkBucketItem.class)
public abstract class MinejagoMilkBucketItemMixin implements PotionCupHolder {
    @Override
    public Potion getPotion(ItemStack stack) {
        return MinejagoPotions.MILK.get();
    }

    @Override
    public boolean canBeFilled(ItemStack stack, Potion potion, int cups) {
        return false;
    }

    @Override
    public ItemStack getFilled(Potion potion) {
        return null;
    }

    @Override
    public boolean canBeDrained(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getDrained(ItemStack stack) {
        return Items.BUCKET.getDefaultInstance();
    }
}
