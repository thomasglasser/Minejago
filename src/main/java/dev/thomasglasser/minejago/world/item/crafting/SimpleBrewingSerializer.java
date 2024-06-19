package dev.thomasglasser.minejago.world.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SimpleBrewingSerializer<T extends TeapotBrewingRecipe> implements RecipeSerializer<T> {
    private final TeapotBrewingRecipe.Factory<T> factory;
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public SimpleBrewingSerializer(TeapotBrewingRecipe.Factory<T> factory, IntProvider i) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(abstractCookingRecipe -> abstractCookingRecipe.group),
                BuiltInRegistries.POTION.holderByNameCodec().fieldOf("base").forGetter(abstractCookingRecipe -> abstractCookingRecipe.base),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(abstractCookingRecipe -> abstractCookingRecipe.ingredient),
                BuiltInRegistries.POTION.holderByNameCodec().fieldOf("result").forGetter(abstractCookingRecipe -> abstractCookingRecipe.result),
                Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(abstractCookingRecipe -> abstractCookingRecipe.experience),
                IntProvider.CODEC.fieldOf("brewing_time").orElse(i).forGetter(abstractCookingRecipe -> abstractCookingRecipe.cookingTime))
                .apply(instance, factory::create));
        this.streamCodec = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, recipe -> recipe.group,
                ByteBufCodecs.holder(Registries.POTION, ByteBufCodecs.registry(Registries.POTION)), recipe -> recipe.base,
                Ingredient.CONTENTS_STREAM_CODEC, recipe -> recipe.ingredient,
                ByteBufCodecs.holder(Registries.POTION, ByteBufCodecs.registry(Registries.POTION)), recipe -> recipe.result,
                ByteBufCodecs.FLOAT, recipe -> recipe.experience,
                ByteBufCodecs.fromCodec(IntProvider.CODEC), recipe -> recipe.cookingTime,
                factory::create);
    }

    public TeapotBrewingRecipe create(String group,
                                      Holder<Potion> base,
                                      Ingredient ingredient,
                                      Holder<Potion> result,
                                      float xp,
                                      IntProvider i) {
        return this.factory.create(group, base, ingredient, result, xp, i);
    }

    @Override
    public MapCodec<T> codec()
    {
        return codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec()
    {
        return streamCodec;
    }
}
