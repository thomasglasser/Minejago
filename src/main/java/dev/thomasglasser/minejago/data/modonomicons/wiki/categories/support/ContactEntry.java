package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.support;

import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import net.minecraft.world.item.Items;

public class ContactEntry extends IndexModeEntryProvider {
    private static final String ID = "contact";

    public ContactEntry(SupportCategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("github", () -> BookImagePageModel.create()
                .withAnchor("github")
                .withImages(ModPagesEntry.GITHUB_LOCATION))
                        .withTitle(context().pageTitle())
                        .withText(context().pageText());

        add(context().pageTitle(), "GitHub");
        add(context().pageText(), """
                For issues and feature requests,
                [GitHub](https://github.com/thomasglasser/Minejago/issues) is the best place to go.
                """);

        page("discord", () -> BookImagePageModel.create()
                .withAnchor("discord")
                .withImages(WikiBookSubProvider.wikiTexture("support/contact/discord.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Discord");
        add(context().pageText(), """
                For general questions and discussions,
                the [Discord](https://discord.gg/U6Qf4P9YW4) is the best place to go.
                """);

        page("email", () -> BookImagePageModel.create()
                .withAnchor("email")
                .withImages(WikiBookSubProvider.wikiTexture("support/contact/email.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Email");
        add(context().pageText(), """
                For business inquiries and other matters,
                you can reach out to at [minejago@thomasglasser.dev](mailto:minejago@thomasglasser.dev).
                """);
    }

    @Override
    protected String entryName() {
        return "Contact";
    }

    @Override
    protected String entryDescription() {
        return "Locations to contact the mod and its developers.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.GOAT_HORN);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
