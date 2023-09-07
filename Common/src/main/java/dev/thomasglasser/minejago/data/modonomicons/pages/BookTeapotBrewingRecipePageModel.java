package dev.thomasglasser.minejago.data.modonomicons.pages;

import com.klikli_dev.modonomicon.api.datagen.book.page.BookRecipePageModel;
import org.jetbrains.annotations.NotNull;

public class BookTeapotBrewingRecipePageModel extends BookRecipePageModel {
    protected BookTeapotBrewingRecipePageModel(@NotNull String anchor) {
        super(BookTeapotBrewingRecipePage.ID, anchor);
    }

    public static BookTeapotBrewingRecipePageModel.Builder builder() {
        return new BookTeapotBrewingRecipePageModel.Builder();
    }

    public static class Builder extends BookRecipePageModel.Builder<BookTeapotBrewingRecipePageModel.Builder> {
        protected Builder() {
            super();
        }

        public BookTeapotBrewingRecipePageModel build() {
            BookTeapotBrewingRecipePageModel model = new BookTeapotBrewingRecipePageModel(this.anchor);
            model.title1 = this.title1;
            model.recipeId1 = this.recipeId1;
            model.title2 = this.title2;
            model.recipeId2 = this.recipeId2;
            model.text = this.text;
            return model;
        }

        @Override
        public BookTeapotBrewingRecipePageModel.Builder getThis() {
            return this;
        }
    }
}