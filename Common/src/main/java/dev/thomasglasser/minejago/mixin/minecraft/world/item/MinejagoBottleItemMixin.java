package dev.thomasglasser.minejago.mixin.minecraft.world.item;

import dev.thomasglasser.minejago.world.item.PotionCupHolder;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BottleItem.class)
public class MinejagoBottleItemMixin implements PotionCupHolder
{

    @Override
    public int getCups() {
        return 2;
    }

    @Override
    public ItemStack getDrained(ItemStack stack) {
        return null;
    }

    @Override
    public boolean canBeDrained(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack getFilled(Potion potion) {
        return PotionUtils.setPotion(Items.POTION.getDefaultInstance(), potion);
    }
}
