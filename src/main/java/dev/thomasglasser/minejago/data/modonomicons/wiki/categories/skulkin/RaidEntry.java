package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.skulkin;

import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import dev.thomasglasser.minejago.world.item.MinejagoItems;

public class RaidEntry extends IndexModeEntryProvider {
    private static final String ID = "raid";

    public RaidEntry(SkulkinCategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("objective", () -> BookTextPageModel.create()
                .withAnchor("objective")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Objective");
        add(context().pageText(), """
                The main objective of the Skulkin Raid is to find the [Golden Weapons Map](entry://locations/four_weapons@golden_weapons_map) hidden in the [Four Weapons Blacksmith](entry://locations/four_weapons).
                They will enter the structure and search block by block for the map.
                They are rather stupid, so they may search the same block multiple times and take a while to find it.
                If found, they will take it and exit into a portal, leaving the structure without a map.
                After they take it, they will be found in the locations with the weapons and will try to take them.
                """);

        page("vehicles", () -> BookImagePageModel.create()
                .withAnchor("vehicles")
                .withImages(WikiBookSubProvider.wikiTexture("skulkin/raid/vehicles.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Vehicles");
        add(context().pageText(), """
                During raids,
                if the [tech config option](entry://skulkin/raid@configuration) is enabled,
                Skulkin will ride on Skull Motorbikes,
                and in the final wave,
                Samukai, Nuckal, and Kruncha will ride on a Skull Truck.
                If the [tech config option](entry://skulkin/raid@configuration) is disabled,
                they will ride Skulkin Horses.
                """);

        page("samukai", () -> BookImagePageModel.create()
                .withAnchor("samukai")
                .withImages(WikiBookSubProvider.wikiTexture("skulkin/raid/samukai.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Samukai");
        add(context().pageText(), """
                Samukai is the leader of the Skulkin and will lead the final wave of the raid.
                He throws knives when he is at 25% health and will always drop his chestplate on death.
                """);

        page("configuration", () -> BookTextPageModel.create()
                .withAnchor("configuration")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Configuration");
        add(context().pageText(), """
                There are two configuration options for the Skulkin Raid:
                - "enable_tech" (default: true): Whether the Skulkin will ride on Skull Motorbikes and Skull Trucks or Skulkin Horses.
                - "enable_skulkin_raids" (default: true): Whether the Skulkin will raid the [Four Weapons Blacksmith](entry://locations/four_weapons).
                """);
    }

    @Override
    protected String entryName() {
        return "Skulkin Raid";
    }

    @Override
    protected String entryDescription() {
        return """
                A raid on the [Four Weapons Blacksmith](entry://locations/four_weapons) by the Skulkin searching for the [Golden Weapons Map](entry://locations/four_weapons@golden_weapons_map).
                There are 4 waves of Skulkin, with Samukai leading the final wave.
                """;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(MinejagoItems.BONE_KNIFE);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
