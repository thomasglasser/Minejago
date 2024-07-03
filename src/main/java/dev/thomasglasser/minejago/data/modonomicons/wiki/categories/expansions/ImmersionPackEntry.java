package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.expansions;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.packs.MinejagoPacks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec2;

public class ImmersionPackEntry extends IndexModeEntryProvider {
    public static final String ID = "immersion_pack";

    public ImmersionPackEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        // Summary Text
        page("summary_text", () -> BookTextPageModel.create()
                .withTitle(entryName()))
                        .withText(context().pageText());

        pageText("The Immersion Pack is a resource pack that makes the world of Ninjago feel more whole with small changes, such as turning Note Blocks into Drums.");

        // Summary Image
        // TODO: Get drums recreation pic
        page("summary_image", () -> BookImagePageModel.create()
                .withImages(modLoc("textures/modonomicon/wiki/expansions/immersion_pack/drum_scene.png"))
                .withText(context().pageText()));

        pageText("We are aware this image is missing. It will be added in the next update.");
    }

    @Override
    public BookEntryModel generate(Vec2 location) {
        this.context().entry(this.entryId());

        var entry = BookEntryModel.create(
                this.modLoc(this.context().categoryId() + "/" + this.context().entryId()),
                entryName())
                .withDescription(entryDescription());

        entry.withIcon(this.entryIcon());
        entry.withLocation(location);
        entry.withEntryBackground(this.entryBackground());

        this.entry = this.additionalSetup(entry);

        this.generatePages();

        this.parent.add(this.entry);
        return this.entry;
    }

    @Override
    protected String entryId() {
        return ID;
    }

    @Override
    protected String entryName() {
        return MinejagoPacks.IMMERSION.titleKey();
    }

    @Override
    protected String entryDescription() {
        return MinejagoPacks.IMMERSION.descriptionKey();
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Blocks.NOTE_BLOCK);
    }
}
