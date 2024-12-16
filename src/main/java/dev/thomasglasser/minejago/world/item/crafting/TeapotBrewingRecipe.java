package dev.thomasglasser.minejago.world.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.world.item.MinejagoItemUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
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

public class TeapotBrewingRecipe implements Recipe<TeapotBrewingRecipe.TeapotBrewingRecipeInput> {
    private final String group;
    private final Holder<Potion> base;
    private final Ingredient ingredient;
    private final Holder<Potion> result;
    private final float experience;
    private final IntProvider brewingTime;

    public TeapotBrewingRecipe(String group, Holder<Potion> base, Ingredient ingredient, Holder<Potion> result, float experience, IntProvider brewingTime) {
        this.group = group;
        this.base = base;
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
        this.brewingTime = brewingTime;
    }

    @Override
    public boolean matches(TeapotBrewingRecipeInput input, Level level) {
        return base == input.base && ingredient.test(input.ingredient);
    }

    @Override
    public ItemStack assemble(TeapotBrewingRecipeInput input, HolderLookup.Provider registries) {
        return getResultItem(registries);
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return false;
    }

    public String group() {
        return group;
    }

    public Holder<Potion> base() {
        return base;
    }

    public Ingredient ingredient() {
        return ingredient;
    }

    public Holder<Potion> result() {
        return result;
    }

    public float experience() {
        return experience;
    }

    public IntProvider brewingTime() {
        return brewingTime;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return MinejagoItemUtils.fillTeacup(result);
    }

    @Override
    public RecipeType<? extends Recipe<TeapotBrewingRecipeInput>> getType() {
        return MinejagoRecipeTypes.TEAPOT_BREWING.get();
    }

    @Override
    public RecipeSerializer<? extends Recipe<TeapotBrewingRecipeInput>> getSerializer() {
        return MinejagoRecipeSerializers.TEAPOT_BREWING.get();
    }

    public static class Serializer implements RecipeSerializer<TeapotBrewingRecipe> {
        private final MapCodec<TeapotBrewingRecipe> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, TeapotBrewingRecipe> streamCodec;

        protected Serializer() {
            this.codec = RecordCodecBuilder.mapCodec(
                    p_360076_ -> p_360076_.group(
                            Codec.STRING.optionalFieldOf("group", "").forGetter(TeapotBrewingRecipe::group),
                            BuiltInRegistries.POTION.holderByNameCodec().fieldOf("base").forGetter(TeapotBrewingRecipe::base),
                            Ingredient.CODEC.fieldOf("ingredient").forGetter(TeapotBrewingRecipe::ingredient),
                            BuiltInRegistries.POTION.holderByNameCodec().fieldOf("result").forGetter(TeapotBrewingRecipe::result),
                            Codec.FLOAT.fieldOf("experience").forGetter(TeapotBrewingRecipe::experience),
                            IntProvider.CODEC.fieldOf("brewing_time").forGetter(TeapotBrewingRecipe::brewingTime))
                            .apply(p_360076_, TeapotBrewingRecipe::new));
            this.streamCodec = StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8, TeapotBrewingRecipe::group,
                    Potion.STREAM_CODEC, TeapotBrewingRecipe::base,
                    Ingredient.CONTENTS_STREAM_CODEC, TeapotBrewingRecipe::ingredient,
                    Potion.STREAM_CODEC, TeapotBrewingRecipe::result,
                    ByteBufCodecs.FLOAT, TeapotBrewingRecipe::experience,
                    ByteBufCodecs.fromCodec(IntProvider.POSITIVE_CODEC), TeapotBrewingRecipe::brewingTime,
                    TeapotBrewingRecipe::new);
        }

        @Override
        public MapCodec<TeapotBrewingRecipe> codec() {
            return this.codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, TeapotBrewingRecipe> streamCodec() {
            return this.streamCodec;
        }
    }

    public record TeapotBrewingRecipeInput(Holder<Potion> base, ItemStack ingredient) implements RecipeInput {
        @Override
        public ItemStack getItem(int index) {
            return switch (index) {
                case 0 -> MinejagoItemUtils.fillTeacup(base);
                case 1 -> ingredient;
                default -> throw new IllegalArgumentException("Recipe does not contain slot " + index);
            };
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public boolean isEmpty() {
            return base == null && ingredient.isEmpty();
        }
    }
}
