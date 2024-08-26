package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.decorations;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookSpotlightPageModel;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.world.item.crafting.Ingredient;

public class ScrollEntry extends IndexModeEntryProvider {
    private static final String ID = "scroll";

    public ScrollEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("scroll_shelf", () -> BookSpotlightPageModel.create()
                .withAnchor("scroll_shelf")
                .withItem(Ingredient.of(MinejagoBlocks.SCROLL_SHELF))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Scroll Shelf");
        add(context().pageText(), "The scroll shelf is a decorative block that also amplifies focus gain.");

        page("writable", () -> BookSpotlightPageModel.create()
                .withAnchor("writable")
                .withItem(Ingredient.of(MinejagoItems.WRITABLE_SCROLL))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Writable Scroll");
        add(context().pageText(), """
                The writable scroll is an item that can be written on and read.
                It can be placed in an chiseled scroll shelf for decoration or in a lectern for reading.
                """);
    }

    @Override
    protected String entryName() {
        return "Scroll Family";
    }

    @Override
    protected String entryDescription() {
        return "The scroll item and block family can be a wonderful decoration and writing tool for your world.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(MinejagoItems.SCROLL);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
