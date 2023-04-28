package dev.thomasglasser.minejago.world.entity;

import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.Level;

public class Nuckal extends MeleeSkeleton
{
    public Nuckal(EntityType<? extends Nuckal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        this.setItemSlot(EquipmentSlot.CHEST, MinejagoArmor.SKELETAL_CHESTPLATE_SET.getForVariant(UnderworldSkeleton.Variant.SPEED).get().getDefaultInstance());
    }
}
