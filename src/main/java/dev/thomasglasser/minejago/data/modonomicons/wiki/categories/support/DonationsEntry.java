package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.support;

import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import net.minecraft.world.item.Items;

public class DonationsEntry extends IndexModeEntryProvider {
    private static final String ID = "donations";

    public DonationsEntry(SupportCategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("buy_me_a_coffee", () -> BookImagePageModel.create()
                .withAnchor("buy_me_a_coffee")
                .withImages(WikiBookSubProvider.wikiTexture("support/donations/buy_me_a_coffee.png")));

        page("links", () -> BookTextPageModel.create()
                .withAnchor("links")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Links");
        add(context().pageText(), """
                If you would like to support the mod, you can do so [here](https://www.buymeacoffee.com/Minejago).
                These funds will be put directly towards the development of the mod.
                \\
                If you would like to support the developer, you can do so [here](https://www.buymeacoffee.com/thomasglasser).
                These funds allow me to devote more time to the mod and other projects.
                """);
    }

    @Override
    protected String entryName() {
        return "Donations";
    }

    @Override
    protected String entryDescription() {
        return "Locations to support the mod and its developers.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.EMERALD);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
