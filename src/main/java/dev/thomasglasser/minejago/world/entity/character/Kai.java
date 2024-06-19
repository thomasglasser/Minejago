package dev.thomasglasser.minejago.world.entity.character;

import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Kai extends Character {
    public Kai(EntityType<? extends Kai> entityType, Level level) {
        super(entityType, level);
        new PowerData(MinejagoPowers.FIRE, true).save(this);
    }

    @Override
    public void onTargetOrRetaliate(Character character) {
        super.onTargetOrRetaliate(character);
        this.setItemSlot(EquipmentSlot.HEAD, Items.IRON_HELMET.getDefaultInstance());
        this.setItemSlot(EquipmentSlot.CHEST, Items.IRON_CHESTPLATE.getDefaultInstance());
        this.setItemSlot(EquipmentSlot.MAINHAND, Items.IRON_SWORD.getDefaultInstance());
    }
}
