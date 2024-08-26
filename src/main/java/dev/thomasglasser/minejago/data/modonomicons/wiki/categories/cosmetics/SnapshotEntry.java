package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.cosmetics;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;

public class SnapshotEntry extends IndexModeEntryProvider {
    private static final String ID = "snapshot";

    public SnapshotEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("pilots", () -> BookImagePageModel.create()
                .withAnchor("pilots")
                .withImages(WikiBookSubProvider.wikiTexture("cosmetics/snapshot/icon_s0.png"), WikiBookSubProvider.wikiTexture("cosmetics/snapshot/pilots.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Pilots");
        add(context().pageText(), "Testers for the pilot episodes get access to a Bamboo Hat cosmetic.");

        page("configuration", () -> BookTextPageModel.create()
                .withAnchor("configuration")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Configuration");
        add(context().pageText(), """
                Snapshot Cosmetics have two client configuration options:
                - "display_snapshot_tester_cosmetic" - Whether or not to display the Snapshot Tester cosmetic.
                - "snapshot_tester_cosmetic_choice" - The cosmetic to display for the Snapshot Tester.
                """);
    }

    @Override
    protected String entryName() {
        return "Snapshot Testers";
    }

    @Override
    protected String entryDescription() {
        return "Exclusive cosmetics for members of the Snapshot Program.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(CosmeticsCategoryProvider.HAT_LOCATION);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
