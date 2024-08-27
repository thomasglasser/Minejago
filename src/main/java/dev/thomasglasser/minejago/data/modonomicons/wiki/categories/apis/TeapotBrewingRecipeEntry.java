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
        page("example", () -> BookTextPageModel.create()
                .withAnchor("example")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "JSON Format");
        add(context().pageText(), """
                An example JSON recipe for brewing tea in the teapot is shown below:
                \\
                ```json
                {
                   "type": "minejago:teapot_brewing",
                   "base": "minecraft:water",
                   "brewing_time": {
                     "type": "minecraft:uniform",
                     "max_inclusive": 2400,
                     "min_inclusive": 1200
                   },
                   "experience": 0.5,
                   "group": "focus_tea",
                   "ingredient": {
                     "item": "minejago:focus_leaves"
                   },
                   "result": "minejago:focus_tea"
                 }
                ```
                \\
                Now, let's break it down.
                """);

        page("format_1", () -> BookTextPageModel.create()
                .withAnchor("format_1")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "JSON Format");
        add(context().pageText(), """
                The "type" field specifies the type of recipe. In this case, it is "minejago:teapot_brewing".
                \\
                The "base" field specifies the base potion for the recipe. In this case, it is "minecraft:water".
                \\
                The "brewing_time" field specifies the time it takes to brew the tea in ticks. In this case, it is a random time between 1200 and 2400.
                """);

        page("format_2", () -> BookTextPageModel.create()
                .withAnchor("format_2")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "JSON Format");
        add(context().pageText(), """
                The "experience" field specifies the experience gained from brewing the tea. In this case, it is 0.5.
                \\
                The "group" field specifies the recipe group. In this case, it is "focus_tea". This value is useful for grouping similar recipes together.
                \\
                The "ingredient" field specifies the ingredient required to brew the tea. In this case, it is "minejago:focus_leaves".
                \\
                The "result" field specifies the resulting potion from brewing the tea. In this case, it is "minejago:focus_tea".
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
                A generator for these recipes is available [here](https://jsons.thomasglasser.dev/minejago/mod_recipe/).
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
