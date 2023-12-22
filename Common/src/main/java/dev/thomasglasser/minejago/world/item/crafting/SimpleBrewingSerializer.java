package dev.thomasglasser.minejago.world.item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SimpleBrewingSerializer<T extends TeapotBrewingRecipe> implements RecipeSerializer<T> {
    private final TeapotBrewingRecipe.Factory<T> factory;
    private final Codec<T> codec;

    public SimpleBrewingSerializer(TeapotBrewingRecipe.Factory<T> factory, IntProvider i) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.create(instance -> instance.group(
                ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(abstractCookingRecipe -> abstractCookingRecipe.group),
                BuiltInRegistries.POTION.byNameCodec().fieldOf("base").forGetter(abstractCookingRecipe -> abstractCookingRecipe.base),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(abstractCookingRecipe -> abstractCookingRecipe.ingredient),
                BuiltInRegistries.POTION.byNameCodec().fieldOf("result").forGetter(abstractCookingRecipe -> abstractCookingRecipe.result),
                Codec.FLOAT.fieldOf("experience").orElse(Float.valueOf(0.0f)).forGetter(abstractCookingRecipe -> Float.valueOf(abstractCookingRecipe.experience)),
                IntProvider.CODEC.fieldOf("brewing_time").orElse(i).forGetter(abstractCookingRecipe -> abstractCookingRecipe.cookingTime))
                .apply(instance, factory::create));
    }

    @Override
    public Codec<T> codec() {
        return this.codec;
    }

    @Override
    public T fromNetwork(FriendlyByteBuf buffer) {
        String group = buffer.readUtf();
        Potion base = BuiltInRegistries.POTION.get(buffer.readResourceLocation());
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        Potion result = BuiltInRegistries.POTION.get(buffer.readResourceLocation());
        float xp = buffer.readFloat();
        IntProvider i = buffer.readJsonWithCodec(IntProvider.CODEC);
        return this.factory.create(group, base, ingredient, result, xp, i);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        buffer.writeUtf(recipe.group);
        buffer.writeResourceLocation(BuiltInRegistries.POTION.getKey(recipe.base));
        (recipe).ingredient.toNetwork(buffer);
        buffer.writeResourceLocation(BuiltInRegistries.POTION.getKey((recipe).result));
        buffer.writeFloat((recipe).experience);
        buffer.writeJsonWithCodec(IntProvider.CODEC, recipe.cookingTime);
    }

    public TeapotBrewingRecipe create(String group,
                                      Potion base,
                                      Ingredient ingredient,
                                      Potion result,
                                      float xp,
                                      IntProvider i) {
        return this.factory.create(group, base, ingredient, result, xp, i);
    }
}
