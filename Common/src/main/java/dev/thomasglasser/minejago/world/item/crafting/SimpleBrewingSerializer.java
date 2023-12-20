package dev.thomasglasser.minejago.world.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SimpleBrewingSerializer<T extends TeapotBrewingRecipe> implements RecipeSerializer<T> {
    private final CookieBaker<T> factory;

    public SimpleBrewingSerializer(CookieBaker<T> cookieBaker) {
        this.factory = cookieBaker;
    }

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject json) {
        String string = GsonHelper.getAsString(json, "group", "");
        String base = GsonHelper.getAsString(json, "base");
        ResourceLocation baseRl = new ResourceLocation(base);
        Potion basePotion = BuiltInRegistries.POTION.getOptional(baseRl).orElseThrow(() -> new IllegalStateException("Potion: " + baseRl + " does not exist"));
        JsonElement jsonElement = GsonHelper.isArrayNode(json, "ingredient")
                ? GsonHelper.getAsJsonArray(json, "ingredient")
                : GsonHelper.getAsJsonObject(json, "ingredient");
        Ingredient ingredient = Ingredient.fromJson(jsonElement);
        String string2 = GsonHelper.getAsString(json, "result");
        ResourceLocation resourceLocation = new ResourceLocation(string2);
        Potion potion = BuiltInRegistries.POTION.getOptional(resourceLocation).orElseThrow(() -> new IllegalStateException("Potion: " + string2 + " does not exist"));
        float f = GsonHelper.getAsFloat(json, "experience", 0.0F);
        IntProvider i = IntProvider.CODEC.parse(JsonOps.INSTANCE, json.get("brewingtime")).result().orElse(ConstantInt.of(1200));
        return this.factory.create(recipeId, string, basePotion, ingredient, potion, f, i);
    }

    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        String string = buffer.readUtf();
        Potion base = BuiltInRegistries.POTION.get(buffer.readResourceLocation());
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        Potion potion = BuiltInRegistries.POTION.get(buffer.readResourceLocation());
        float f = buffer.readFloat();
        IntProvider i = buffer.readJsonWithCodec(IntProvider.CODEC);
        return this.factory.create(recipeId, string, base, ingredient, potion, f, i);
    }

    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        buffer.writeUtf(recipe.group);
        buffer.writeResourceLocation(BuiltInRegistries.POTION.getKey(recipe.base));
        recipe.ingredient.toNetwork(buffer);
        buffer.writeResourceLocation(BuiltInRegistries.POTION.getKey(recipe.result));
        buffer.writeFloat(recipe.experience);
        buffer.writeJsonWithCodec(IntProvider.CODEC, recipe.cookingTime);
    }

    interface CookieBaker<T extends TeapotBrewingRecipe> {
        T create(
                ResourceLocation resourceLocation, String string, Potion base, Ingredient ingredient, Potion result, float f, IntProvider i
        );
    }
}
