package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.skills;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.Minejago;

public class DexterityEntry extends IndexModeEntryProvider {
    public static final String ID = "dexterity";

    public DexterityEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("image", () -> BookImagePageModel.create()
                .withImages(Minejago.modLoc("textures/gui/skill/dexterity.png"))
                .withTitle(context().pageTitle()));

        add(context().pageTitle(), "Dexterity");

        page("description", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Dexterity");
        add(context().pageText(), """
                When breaking blocks or attacking with your hands, you train dexterity.
                Every 100 damage points or blocks broken (can be less depending on break time), you gain 1 dexterity level.
                As you level up, you will be able to break blocks and attack quicker and deal more damage while using your hands.
                """);
    }

    @Override
    protected String entryName() {
        return "Dexterity";
    }

    @Override
    protected String entryDescription() {
        return "Dexterity allows you to train your hand speed and damage by breaking blocks and attacking with your hands.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Minejago.modLoc("textures/gui/skill/dexterity.png"), 32, 39);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
