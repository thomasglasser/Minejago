package dev.thomasglasser.minejago.world.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.world.item.MinejagoItemUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record TeapotBrewingRecipe(String group, Holder<Potion> base, Ingredient ingredient, Holder<Potion> result, float experience, IntProvider brewingTime) implements Recipe<TeapotBrewingRecipe.TeapotBrewingRecipeInput> {

    @Override
    public boolean matches(TeapotBrewingRecipeInput input, Level level) {
        return ingredient().test(input.ingredient()) && base().equals(input.base());
    }

    @Override
    public ItemStack assemble(TeapotBrewingRecipeInput input, HolderLookup.Provider registries) {
        return getResultItem(registries);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return MinejagoItemUtils.fillTeacup(result());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MinejagoRecipeSerializers.TEAPOT_BREWING_RECIPE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return MinejagoRecipeTypes.TEAPOT_BREWING.get();
    }
    public static class Factory implements RecipeSerializer<TeapotBrewingRecipe> {
        public static final MapCodec<TeapotBrewingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.STRING.fieldOf("group").forGetter(TeapotBrewingRecipe::group),
                Potion.CODEC.fieldOf("base").forGetter(TeapotBrewingRecipe::base),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(TeapotBrewingRecipe::ingredient),
                Potion.CODEC.fieldOf("result").forGetter(TeapotBrewingRecipe::result),
                Codec.FLOAT.fieldOf("experience").forGetter(TeapotBrewingRecipe::experience),
                IntProvider.POSITIVE_CODEC.fieldOf("brewing_time").forGetter(TeapotBrewingRecipe::brewingTime)).apply(instance, TeapotBrewingRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, TeapotBrewingRecipe> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, TeapotBrewingRecipe::group,
                Potion.STREAM_CODEC, TeapotBrewingRecipe::base,
                Ingredient.CONTENTS_STREAM_CODEC, TeapotBrewingRecipe::ingredient,
                Potion.STREAM_CODEC, TeapotBrewingRecipe::result,
                ByteBufCodecs.FLOAT, TeapotBrewingRecipe::experience,
                ByteBufCodecs.fromCodec(IntProvider.POSITIVE_CODEC), TeapotBrewingRecipe::brewingTime,
                TeapotBrewingRecipe::new);

        @Override
        public MapCodec<TeapotBrewingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, TeapotBrewingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public record TeapotBrewingRecipeInput(Holder<Potion> base, ItemStack ingredient) implements RecipeInput {
        @Override
        public ItemStack getItem(int index) {
            return switch (index) {
                case 0 -> MinejagoItemUtils.fillTeacup(base);
                case 1 -> ingredient;
                default -> ItemStack.EMPTY;
            };
        }

        @Override
        public int size() {
            return 2;
        }
    }
}
