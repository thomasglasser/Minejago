package dev.thomasglasser.minejago.world.item.crafting.display;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record TeapotRecipeDisplay(PotionSlotDisplay base, SlotDisplay ingredient, PotionSlotDisplay result, SlotDisplay craftingStation, float experience, IntProvider brewingTime) implements RecipeDisplay {

    public static final MapCodec<TeapotRecipeDisplay> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            PotionSlotDisplay.MAP_CODEC.fieldOf("base").forGetter(TeapotRecipeDisplay::base),
            SlotDisplay.CODEC.fieldOf("ingredient").forGetter(TeapotRecipeDisplay::ingredient),
            PotionSlotDisplay.MAP_CODEC.fieldOf("result").forGetter(TeapotRecipeDisplay::result),
            SlotDisplay.CODEC.fieldOf("crafting_station").forGetter(TeapotRecipeDisplay::craftingStation),
            Codec.FLOAT.fieldOf("experience").forGetter(TeapotRecipeDisplay::experience),
            IntProvider.CODEC.fieldOf("brewing_time").forGetter(TeapotRecipeDisplay::brewingTime)).apply(instance, TeapotRecipeDisplay::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, TeapotRecipeDisplay> STREAM_CODEC = StreamCodec.composite(
            PotionSlotDisplay.STREAM_CODEC, TeapotRecipeDisplay::base,
            SlotDisplay.STREAM_CODEC, TeapotRecipeDisplay::ingredient,
            PotionSlotDisplay.STREAM_CODEC, TeapotRecipeDisplay::result,
            SlotDisplay.STREAM_CODEC, TeapotRecipeDisplay::craftingStation,
            ByteBufCodecs.FLOAT, TeapotRecipeDisplay::experience,
            ByteBufCodecs.fromCodec(IntProvider.POSITIVE_CODEC), TeapotRecipeDisplay::brewingTime,
            TeapotRecipeDisplay::new);
    public static final Type<TeapotRecipeDisplay> TYPE = new Type<>(MAP_CODEC, STREAM_CODEC);
    @Override
    public Type<? extends RecipeDisplay> type() {
        return TYPE;
    }

    @Override
    public boolean isEnabled(FeatureFlagSet p_379560_) {
        return base.isEnabled(p_379560_) && ingredient.isEnabled(p_379560_) && result.isEnabled(p_379560_) && craftingStation.isEnabled(p_379560_);
    }
}
