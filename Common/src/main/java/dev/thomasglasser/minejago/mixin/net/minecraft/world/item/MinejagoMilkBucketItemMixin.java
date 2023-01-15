package dev.thomasglasser.minejago.mixin.net.minecraft.world.item;

import dev.thomasglasser.minejago.world.item.ITeapotLiquidHolder;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.item.alchemy.Potion;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MilkBucketItem.class)
public class MinejagoMilkBucketItemMixin implements ITeapotLiquidHolder {
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
        return MinejagoPotions.MILK.get();
    }
}
