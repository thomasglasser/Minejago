package dev.thomasglasser.minejago.datamaps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record PotionFillable(int cups, ItemStack filled) {
    public static final Codec<PotionFillable> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("cups").forGetter(PotionFillable::cups),
            ItemStack.SINGLE_ITEM_CODEC.fieldOf("filled").forGetter(PotionFillable::filled)).apply(instance, PotionFillable::new));

    public PotionFillable(int cups, Item filled) {
        this(cups, new ItemStack(filled));
    }
}
