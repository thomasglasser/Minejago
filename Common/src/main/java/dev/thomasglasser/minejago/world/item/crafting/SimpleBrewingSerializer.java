package dev.thomasglasser.minejago.world.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SimpleBrewingSerializer<T extends TeapotBrewingRecipe> implements RecipeSerializer<T> {
    private final int defaultCookingTime;
    private final CookieBaker<T> factory;

    public SimpleBrewingSerializer(CookieBaker<T> cookieBaker, int i) {
        this.defaultCookingTime = i;
        this.factory = cookieBaker;
    }
    public T fromJson(ResourceLocation recipeId, JsonObject json) {
        String string = GsonHelper.getAsString(json, "group", "");
        JsonElement jsonElement = (JsonElement)(GsonHelper.isArrayNode(json, "ingredient")
                ? GsonHelper.getAsJsonArray(json, "ingredient")
                : GsonHelper.getAsJsonObject(json, "ingredient"));
        Ingredient ingredient = Ingredient.fromJson(jsonElement);
        String string2 = GsonHelper.getAsString(json, "result");
        ResourceLocation resourceLocation = new ResourceLocation(string2);
        Potion potion = BuiltInRegistries.POTION.getOptional(resourceLocation).orElseThrow(() -> new IllegalStateException("Potion: " + string2 + " does not exist"));
        float f = GsonHelper.getAsFloat(json, "experience", 0.0F);
        int i = GsonHelper.getAsInt(json, "cookingtime", this.defaultCookingTime);
        return this.factory.create(recipeId, string, ingredient, potion, f, i);
    }

    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        String string = buffer.readUtf();
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        Potion potion = BuiltInRegistries.POTION.get(buffer.readResourceLocation());
        float f = buffer.readFloat();
        int i = buffer.readVarInt();
        return this.factory.create(recipeId, string, ingredient, potion, f, i);
    }

    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        buffer.writeUtf(recipe.group);
        recipe.ingredient.toNetwork(buffer);
        buffer.writeResourceLocation(BuiltInRegistries.POTION.getKey(recipe.result));
        buffer.writeFloat(recipe.experience);
        buffer.writeVarInt(recipe.cookingTime);
    }

    interface CookieBaker<T extends TeapotBrewingRecipe> {
        T create(
                ResourceLocation resourceLocation, String string, Ingredient ingredient, Potion result, float f, int i
        );
    }
}
