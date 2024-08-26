package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.locations;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.data.modonomicons.MinejagoBookProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;

public class FourWeaponsEntry extends IndexModeEntryProvider {
    private static final String ID = "four_weapons";

    public FourWeaponsEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("golden_weapons_map", () -> BookTextPageModel.create()
                .withAnchor("golden_weapons_map")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Golden Weapons Map");
        add(context().pageText(), """
                The main purpose of the Four Weapons blacksmith is to store the Golden Weapons Map.
                This map is a key item in the story of Minejago, as it leads to the location of the Golden Weapons.
                It can be found in the sign above the entrance to the shop.
                Interacting with the sign will yield an empty map,
                which can be filled in any location and will lead to nearby Golden Weapons.
                There is one map per structure, so in order to find all four Golden Weapons,
                you may need to find multiple Four Weapons structures.
                """);

        page("fancy_finds", () -> BookImagePageModel.create()
                .withAnchor("fancy_finds")
                .withImages(MinejagoBookProvider.itemLoc(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN), MinejagoBookProvider.itemLoc(MinejagoItems.FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Fancy Finds");
        add(context().pageText(), """
                The Four Weapons blacksmith is also home to a variety of unique items.
                In a barrel by the entrance,
                there is a chance to find one or more of the following items:
                - Four Weapons Banner Pattern
                - Four Weapons Armor Trim Smithing Template
                """);

        page("fateful_findings", () -> BookImagePageModel.create()
                .withAnchor("fateful_findings")
                .withImages(WikiBookSubProvider.wikiTexture("locations/four_weapons/fateful_findings.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Fateful Findings");
        add(context().pageText(), """
                The Four Weapons blacksmith is also home to [Kai](entry://characters/ninja_team@kai) and [Nya](entry://characters/ninja_team@nya).
                They can be found in the shop walking around or outside defending the shop from enemies or [Skulkin Raids](entry://skulkin/raid).
                """);
    }

    @Override
    protected String entryName() {
        return "Four Weapons";
    }

    @Override
    protected String entryDescription() {
        return """
                The home of [Kai](entry://characters/ninja_team@kai) and [Nya](entry://characters/ninja_team@nya),
                Four Weapons is a blacksmith shop.
                It can be found in Plains biomes and it is the host of [Skulkin Raids](entry://skulkin/raid).
                """;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(MinejagoBlocks.TOP_POST);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
