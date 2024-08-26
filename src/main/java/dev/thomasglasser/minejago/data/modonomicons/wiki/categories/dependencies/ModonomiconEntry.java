package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.dependencies;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class ModonomiconEntry extends IndexModeEntryProvider {
    private static final String ID = "modonomicon";

    public ModonomiconEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("wiki", () -> BookSpotlightPageModel.create()
                .withAnchor("wiki")
                .withItem(Ingredient.of(Items.ENCHANTED_BOOK))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Wiki");
        add(context().pageText(), "Modonomicon provides an in-game wiki for Minejago.");
    }

    @Override
    protected String entryName() {
        return "Modonomicon";
    }

    @Override
    protected String entryDescription() {
        return "[Modonomicon](https://modrinth.com/mod/modonomicon) is an in-game documentation mod.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.ENCHANTED_BOOK);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
