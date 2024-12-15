package dev.thomasglasser.minejago.datamaps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record PotionFillable(int cups, ItemStack filled) {
    public static final Codec<PotionFillable> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("cups").forGetter(PotionFillable::cups),
            ItemStack.CODEC.fieldOf("filled").forGetter(PotionFillable::filled)).apply(instance, PotionFillable::new));

    public PotionFillable(int cups, Item filled) {
        this(cups, new ItemStack(filled));
    }
}
