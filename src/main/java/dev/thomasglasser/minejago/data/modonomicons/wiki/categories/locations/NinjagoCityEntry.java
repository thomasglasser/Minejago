package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.locations;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import net.minecraft.world.item.Items;

public class NinjagoCityEntry extends IndexModeEntryProvider {
    private static final String ID = "ninjago_city";

    public NinjagoCityEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("fateful_findings", () -> BookImagePageModel.create()
                .withAnchor("fateful_findings")
                .withImages(WikiBookSubProvider.wikiTexture("locations/ninjago_city/fateful_findings.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Fateful Findings");
        add(context().pageText(), """
                Ninjago City is currently the home of [Jay](entry://characters/ninja_team@jay).
                He can be found on top of a skyscraper with a wrecked billboard.
                """);
    }

    @Override
    protected String entryName() {
        return "Ninjago City";
    }

    @Override
    protected String entryDescription() {
        return """
                Currently a small building in the wild,
                Ninjago City will be a bustling metropolis in the future,
                home to the characters of Minejago and their allies.
                """;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.ELYTRA);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
