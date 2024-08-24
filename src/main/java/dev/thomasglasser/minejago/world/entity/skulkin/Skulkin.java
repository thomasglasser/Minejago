package dev.thomasglasser.minejago.world.entity.skulkin;

import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaider;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Skulkin extends SkulkinRaider {
    private Variant variant;

    public Skulkin(EntityType<? extends Skulkin> entityType, Level level) {
        super(entityType, level);
        variant = Variant.values()[(int) (Math.random() * (Variant.values().length - 1))];
    }

    @Override
    public double getAttributeBaseValue(Holder<Attribute> attribute) {
        double base = super.getAttributeValue(attribute);
        if (attribute == Attributes.ATTACK_DAMAGE && this.variant == Variant.STRENGTH) {
            base *= 2.0;
        } else if (attribute == Attributes.MOVEMENT_SPEED && this.variant == Variant.SPEED) {
            base *= 2.0;
        }
        return base;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
        this.setItemSlot(EquipmentSlot.CHEST, MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(this.variant).get().getDefaultInstance());
        this.setItemSlot(EquipmentSlot.LEGS, ItemStack.EMPTY);
        switch (variant) {
            case BOW -> this.setItemSlot(EquipmentSlot.MAINHAND, Items.BOW.getDefaultInstance());
            case KNIFE -> this.setItemSlot(EquipmentSlot.MAINHAND, MinejagoItems.BONE_KNIFE.get().getDefaultInstance());
            default -> this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public enum Variant {
        STRENGTH(ChatFormatting.RED),
        SPEED(ChatFormatting.BLUE),
        BOW(ChatFormatting.WHITE),
        KNIFE(ChatFormatting.BLACK),
        BONE(ChatFormatting.GRAY);

        private final ChatFormatting color;

        Variant(ChatFormatting color) {
            if (color.isColor())
                this.color = color;
            else
                throw new IllegalArgumentException("Skeleton armor variant color must be a color!");
        }

        public ChatFormatting getColor() {
            return color;
        }
    }
}
