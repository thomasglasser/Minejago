package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.spinjitzu;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import net.minecraft.world.item.Items;

public class AbilitiesEntry extends IndexModeEntryProvider {
    private static final String ID = "abilities";

    public AbilitiesEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("attributes", () -> BookTextPageModel.create()
                .withAnchor("attributes")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Attributes");
        add(context().pageText(), """
                While doing Spinjitzu,
                user speed and knockback are increased,
                allowing users to use momentum to their advantage.
                """);

        page("passive_knockback", () -> BookTextPageModel.create()
                .withAnchor("passive_knockback")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Passive Knockback");
        add(context().pageText(), """
                While doing Spinjitzu,
                any entity that comes into contact with the user
                will be knocked back,
                forming a protective barrier around the user.
                """);

        page("environmental", () -> BookImagePageModel.create()
                .withAnchor("environmental")
                .withImages(WikiBookSubProvider.wikiTexture("spinjitzu/abilities/environmental.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Environmental Effects");
        add(context().pageText(), """
                While doing Spinjitzu,
                users emit light and vibrations.
                This make them more visible to mobs and sculk sensors.
                Wearing a Gi will reduce visibility.
                """);
    }

    @Override
    protected String entryName() {
        return "Abilities";
    }

    @Override
    protected String entryDescription() {
        return "The abilities of Spinjitzu";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.WIND_CHARGE);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
