package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.dependencies;

import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import net.minecraft.world.item.Items;

public class JeiEntry extends IndexModeEntryProvider {
    private static final String ID = "jei";

    public JeiEntry(DependenciesCategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("teapot_recipe", () -> BookImagePageModel.create()
                .withAnchor("teapot_recipe")
                .withImages(WikiBookSubProvider.wikiTexture("dependencies/jei/teapot_recipe.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Teapot Recipes");
        add(context().pageText(), "[Just Enough Items](https://modrinth.com/mod/jei) is able to show the recipes of a teapot.");
    }

    @Override
    protected String entryName() {
        return "Just Enough Items";
    }

    @Override
    protected String entryDescription() {
        return "Just Enough Items is an item and recipe viewing mod.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.COMPASS);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
