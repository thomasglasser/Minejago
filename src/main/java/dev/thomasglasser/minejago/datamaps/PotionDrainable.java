package dev.thomasglasser.minejago.datamaps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;

public record PotionDrainable(Optional<Holder<Potion>> potion, int cups, ItemStack remainder) {

    public static final Codec<PotionDrainable> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.POTION.holderByNameCodec().optionalFieldOf("potion").forGetter(PotionDrainable::potion),
            ExtraCodecs.POSITIVE_INT.fieldOf("cups").forGetter(PotionDrainable::cups),
            ItemStack.SINGLE_ITEM_CODEC.fieldOf("remainder").forGetter(PotionDrainable::remainder)).apply(instance, PotionDrainable::new));
    public PotionDrainable(int cups, ItemStack remainder) {
        this(Optional.empty(), cups, remainder);
    }

    public PotionDrainable(Holder<Potion> potion, int cups, ItemStack remainder) {
        this(Optional.of(potion), cups, remainder);
    }
}
