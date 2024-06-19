package dev.thomasglasser.minejago.world.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ScrollItem extends Item {
    public ScrollItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }
}
