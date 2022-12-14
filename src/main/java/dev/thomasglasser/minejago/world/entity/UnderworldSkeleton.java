package dev.thomasglasser.minejago.world.entity;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import net.minecraft.ChatFormatting;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class UnderworldSkeleton extends MeleeSkeleton {
    private final Variant variant;

    public UnderworldSkeleton(EntityType<? extends UnderworldSkeleton> entityType, Level level) {
        super(entityType, level);
        variant = Variant.values()[(int) (Math.random() * Variant.values().length)];
    }

    @Nullable
    @Override
    public double getAttributeValue(Attribute pAttribute) {
        double base = super.getAttributeValue(pAttribute);
        if (pAttribute == Attributes.ATTACK_DAMAGE && this.variant == Variant.STRENGTH)
        {
            base *= 2.0;
        } else if (pAttribute == Attributes.MOVEMENT_SPEED && this.variant == Variant.SPEED) {
            base *= 2.0;
        }
        return base;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
        this.setItemSlot(EquipmentSlot.CHEST, MinejagoArmor.SKELETAL_CHESTPLATE_SET.getForVariant(this.variant).get().getDefaultInstance());
        this.setItemSlot(EquipmentSlot.LEGS, ItemStack.EMPTY);
        switch (variant)
        {
            case BOW -> this.setItemSlot(EquipmentSlot.MAINHAND, Items.BOW.getDefaultInstance());
            case KNIFE -> this.setItemSlot(EquipmentSlot.MAINHAND, MinejagoItems.BONE_KNIFE.get().getDefaultInstance());
            default -> this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
    }

    public enum Variant
    {
        STRENGTH(ChatFormatting.RED),
        SPEED(ChatFormatting.BLUE),
        BOW(ChatFormatting.WHITE),
        KNIFE(ChatFormatting.BLACK);

        private final ChatFormatting color;

        Variant(ChatFormatting color)
        {
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
