package dev.thomasglasser.minejago.world.entity.skulkin;

import dev.thomasglasser.minejago.world.entity.MeleeSkeleton;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Kruncha extends MeleeSkeleton
{
    public Kruncha(EntityType<? extends Kruncha> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        this.setItemSlot(EquipmentSlot.HEAD, Items.IRON_HELMET.getDefaultInstance());
        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
    }
}
