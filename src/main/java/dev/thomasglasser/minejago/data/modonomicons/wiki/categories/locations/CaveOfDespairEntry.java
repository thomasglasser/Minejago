package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.locations;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.world.item.MinejagoItems;

public class CaveOfDespairEntry extends IndexModeEntryProvider {
    private static final String ID = "cave_of_despair";

    public CaveOfDespairEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("scythe_of_quakes", () -> BookTextPageModel.create()
                .withAnchor("scythe_of_quakes")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Obtaining the Scythe of Quakes");
        add(context().pageText(), """
                To obtain the [Scythe of Quakes](entry://powers/golden_weapons@scythe_of_quakes) from the Cave of Despair,
                you must interact with the [Earth Dragon](entry://powers/dragons) in the ground.
                The Earth Dragon will then come out of the ground and try to protect the Scythe.
                You must either tame the [Earth Dragon](entry://powers/dragons) or escape it to obtain the Scythe.
                """);

        page("fancy_finds", () -> BookTextPageModel.create()
                .withAnchor("fancy_finds")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Fancy Finds");
        add(context().pageText(), """
                The Cave of Despair is also home to a variety of unique items.
                In suspicious red sand around the cave,
                there is a chance to find one or more of the following items:
                - Peaks Pottery Sherd
                - Terrain Armor Trim Smithing Template
                - Terracotta
                - Light Gray Terracotta
                - Clay Ball
                - Emerald
                """);
    }

    @Override
    protected String entryName() {
        return "Cave of Despair";
    }

    @Override
    protected String entryDescription() {
        return """
                The Cave of Despair is the location of the [Scythe of Quakes](entry://powers/golden_weapons@scythe_of_quakes) and the [Earth Dragon](entry://powers/dragons).
                It can be found in Badlands biomes.
                If a [Golden Weapons Map](entry://locations/four_weapons/golden_weapons_map) has been taken by the [Skulkin](category://skulkin),
                they will also be seeking the Scythe,
                and will try to stop you from reaching it.
                """;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(MinejagoItems.SCYTHE_OF_QUAKES);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
