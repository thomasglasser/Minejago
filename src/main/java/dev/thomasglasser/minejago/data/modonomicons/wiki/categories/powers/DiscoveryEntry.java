package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.powers;

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

        add(context().pageTitle(), "Power Discovery");
        add(context().pageText(), """
                To discover one's powers,
                the player must first find [Wu](entry://characters/ninja_team@wu) in the [Monastery of Spinjitzu](entry://locations/monastery_of_spinjitzu) and present him the [Golden Weapons map](entry://locations/four_weapons@golden_weapons_map).
                Wu will then spinjitzu over to the player and tell them their power.
                He will also give them their [Training Gi](entry://powers/gi@training).
                """);

        page("choices", () -> BookTextPageModel.create()
                .withAnchor("choices")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Choices");
        add(context().pageText(), """
                Depending on [config values](entry://powers/configuration) set, players may have the option to choose or change their power.
                Choosing one's power involves interacting with [Wu](entry://characters/ninja_team@wu) and selecting from the Power Selection Screen.
                Changing one's power involves interacting with [Wu](entry://characters/ninja_team@wu) after already having a power to get a new one.
                """);
    }

    @Override
    protected String entryName() {
        return "Discovery";
    }

    @Override
    protected String entryDescription() {
        return "The process of discovering one's powers.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(modLoc("textures/power/fire.png"), 32, 32);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
