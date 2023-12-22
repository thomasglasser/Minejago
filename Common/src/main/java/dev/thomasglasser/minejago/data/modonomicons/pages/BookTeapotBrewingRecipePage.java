package dev.thomasglasser.minejago.data.modonomicons.pages;

import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.page.BookProcessingRecipePage;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.crafting.MinejagoRecipeTypes;
import dev.thomasglasser.minejago.world.item.crafting.TeapotBrewingRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeType;

public class BookTeapotBrewingRecipePage extends BookProcessingRecipePage<TeapotBrewingRecipe> {
    public static final ResourceLocation ID = Minejago.modLoc("teapot_brewing_recipe");

    public BookTeapotBrewingRecipePage(BookTextHolder title1, ResourceLocation recipeId1, BookTextHolder title2, ResourceLocation recipeId2, BookTextHolder text, String anchor) {
        super((RecipeType<TeapotBrewingRecipe>) MinejagoRecipeTypes.TEAPOT_BREWING.get(), title1, recipeId1, title2, recipeId2, text, anchor);
    }

    public static BookTeapotBrewingRecipePage fromJson(JsonObject json) {
        var common = BookRecipePage.commonFromJson(json);
        var anchor = GsonHelper.getAsString(json, "anchor", "");
        return new BookTeapotBrewingRecipePage(common.title1(), common.recipeId1(), common.title2(), common.recipeId2(), common.text(), anchor);
    }

    public static BookTeapotBrewingRecipePage fromNetwork(FriendlyByteBuf buffer) {
        var common = BookRecipePage.commonFromNetwork(buffer);
        var anchor = buffer.readUtf();
        return new BookTeapotBrewingRecipePage(common.title1(), common.recipeId1(), common.title2(), common.recipeId2(), common.text(), anchor);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer) {
        super.toNetwork(buffer);
        buffer.writeUtf(this.anchor);
    }

    @Override
    public ResourceLocation getType() {
        return ID;
    }
}
