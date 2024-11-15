package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.skills;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.Minejago;

public class AgilityEntry extends IndexModeEntryProvider {
    public static final String ID = "agility";

    public AgilityEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("image", () -> BookImagePageModel.create()
                .withImages(Minejago.modLoc("textures/gui/skill/agility.png"))
                .withTitle(context().pageTitle()));

        add(context().pageTitle(), "Agility");

        page("description", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Agility");
        add(context().pageText(), """
                When sprinting and/or jumping, you train agility.
                Every 100 seconds of sprinting or jumping (or 50 seconds if both sprinting and jumping), you gain 1 agility level.
                As you level up, you will be able to sprint faster and jump higher.
                """);
    }

    @Override
    protected String entryName() {
        return "Agility";
    }

    @Override
    protected String entryDescription() {
        return "Agility allows you to train your speed and jump height by running and jumping.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Minejago.modLoc("textures/gui/skill/agility.png"), 32, 39);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
