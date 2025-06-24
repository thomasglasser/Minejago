package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.cosmetics;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;

public class BetaEntry extends IndexModeEntryProvider {
    private static final String ID = "beta";

    public BetaEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("pilots", () -> BookImagePageModel.create()
                .withAnchor("pilots")
                .withImages(WikiBookSubProvider.wikiTexture("cosmetics/beta/icon_s0.png"), WikiBookSubProvider.wikiTexture("cosmetics/beta/pilots.png"))
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
                Beta Cosmetics have two client configuration options:
                - "display_beta_tester_cosmetic" - Whether or not to display the Beta Tester cosmetic.
                - "beta_tester_cosmetic_choice" - The cosmetic to display for the Beta Tester.
                """);
    }

    @Override
    protected String entryName() {
        return "Beta Testers";
    }

    @Override
    protected String entryDescription() {
        return "Exclusive cosmetics for members of the Beta Program.";
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
