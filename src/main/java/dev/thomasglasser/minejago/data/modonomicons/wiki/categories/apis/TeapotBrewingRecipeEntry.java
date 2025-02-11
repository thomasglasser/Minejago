package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.apis;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;

public class TeapotBrewingRecipeEntry extends IndexModeEntryProvider {
    private static final String ID = "teapot_brewing_recipe";

    public TeapotBrewingRecipeEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("generator", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Generator");
        add(context().pageText(), """
                A generator for recipes is available online [here](https://snapshot-jsons.thomasglasser.dev/recipe/).
                """);

        page("format", () -> BookTextPageModel.create()
                .withAnchor("format")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "JSON Format");
        add(context().pageText(), """
                Teapot brewing recipes have the following fields:
                - type: The type of recipe. In this case, it is "minejago:teapot_brewing".
                - base: The base potion for the recipe.
                - brewing_time: The time it takes to brew the tea in ticks.
                - experience: The experience gained from brewing the tea.
                - group: The recipe group. This value is useful for grouping similar recipes together.
                - ingredient: The ingredient required to brew the tea.
                - result: The resulting potion from brewing the tea.
                """);
    }

    @Override
    protected String entryName() {
        return "Teapot Brewing Recipes";
    }

    @Override
    protected String entryDescription() {
        return """
                Datapacks and mods are able to add new recipes to the teapot.
                """;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(MinejagoBlocks.TEAPOT);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
