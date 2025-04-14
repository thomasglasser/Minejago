package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.elements;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;

public class DiscoveryEntry extends IndexModeEntryProvider {
    private static final String ID = "discovery";

    public DiscoveryEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("find_wu", () -> BookTextPageModel.create()
                .withAnchor("find_wu")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Element Discovery");
        add(context().pageText(), """
                To discover one's element,
                the player must first find [Wu](entry://characters/ninja_team@wu) in the [Monastery of Spinjitzu](entry://locations/monastery_of_spinjitzu) and present him the [Golden Weapons map](entry://locations/four_weapons@golden_weapons_map).
                Wu will then spinjitzu over to the player and tell them their element.
                He will also give them their [Training Gi](entry://elements/gi@training).
                """);

        page("choices", () -> BookTextPageModel.create()
                .withAnchor("choices")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Choices");
        add(context().pageText(), """
                Depending on [config values](entry://elements/configuration) set, players may have the option to choose or change their element.
                Choosing one's element involves interacting with [Wu](entry://characters/ninja_team@wu) and selecting from the Element Selection Screen.
                Changing one's element involves interacting with [Wu](entry://characters/ninja_team@wu) after already having a element to get a new one.
                """);
    }

    @Override
    protected String entryName() {
        return "Discovery";
    }

    @Override
    protected String entryDescription() {
        return "The process of discovering one's element.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(modLoc("textures/element/fire.png"), 32, 32);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
