package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.cosmetics;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import net.minecraft.world.item.Items;

public class DevEntry extends IndexModeEntryProvider {
    private static final String ID = "dev";

    public DevEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("legacy", () -> BookImagePageModel.create()
                .withAnchor("legacy")
                .withImages(WikiBookSubProvider.wikiTexture("cosmetics/dev/legacy.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Legacy Development Team");
        add(context().pageText(), "Members of the development team that have been with the project since the beginning get access to an exclusive beard cosmetic.");

        page("configuration", () -> BookTextPageModel.create()
                .withAnchor("configuration")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Configuration");
        add(context().pageText(), """
                The Development Team cosmetics have two client configuration options:
                - "display_dev_team_cosmetic" - Whether or not to display the Development Team cosmetic.
                - "display_legacy_dev_team_cosmetic" - Whether or not to display the Legacy Development Team cosmetic.
                """);
    }

    @Override
    protected String entryName() {
        return "Development Team";
    }

    @Override
    protected String entryDescription() {
        return "Exclusive cosmetics for members of the Development Team.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.STRUCTURE_BLOCK);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
