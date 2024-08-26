package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.expansions;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import net.minecraft.world.level.block.Blocks;

public class ImmersionEntry extends IndexModeEntryProvider {
    private static final String ID = "immersion";

    public ImmersionEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("drum", () -> BookImagePageModel.create()
                .withAnchor("drum")
                .withImages(WikiBookSubProvider.wikiTexture("expansions/immersion/drum.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Drum");
        add(context().pageText(), "All references of the Note Block have been renamed to Drum and the block has been made to resemble the drum from the pilots.");
    }

    @Override
    protected String entryName() {
        return "Immersion Pack";
    }

    @Override
    protected String entryDescription() {
        return "The Immersion Pack is a resource pack that enhances the Minejago experience for aesthetic and roleplay purposes.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Blocks.NOTE_BLOCK);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
