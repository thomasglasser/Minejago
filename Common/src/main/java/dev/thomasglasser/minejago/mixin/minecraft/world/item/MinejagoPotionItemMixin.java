package dev.thomasglasser.minejago.mixin.minecraft.world.item;

import dev.thomasglasser.minejago.world.item.PotionCupHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PotionItem.class)
public class MinejagoPotionItemMixin implements PotionCupHolder {
    @Override
    public int getCups() {
        return 2;
    }

    @Override
    public ItemStack getDrained(ItemStack stack) {
        return new ItemStack(Items.GLASS_BOTTLE);
    }

    @Override
    public Holder<Potion> getPotion(ItemStack stack) {
        return stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).potion().orElse(null);
    }

    @Override
    public ItemStack getFilled(Holder<Potion> potion) {
        return null;
    }

    @Override
    public boolean canBeFilled(ItemStack stack, Holder<Potion> potion, int cups) {
        return false;
    }

    @Override
    public boolean canBeDrained(ItemStack stack) {
        return true;
    }
}
