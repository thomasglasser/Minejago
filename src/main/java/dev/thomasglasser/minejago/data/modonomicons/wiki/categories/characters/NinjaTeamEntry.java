package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.characters;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import dev.thomasglasser.minejago.world.item.MinejagoItems;

public class NinjaTeamEntry extends IndexModeEntryProvider {
    private static final String ID = "ninja_team";

    public NinjaTeamEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("wu_image", () -> BookImagePageModel.create()
                .withAnchor("wu_image")
                .withImages(WikiBookSubProvider.wikiTexture("characters/ninja_team/wu.png"))
                .withTitle(context().pageTitle()));

        add(context().pageTitle(), "Wu");

        page("wu", () -> BookTextPageModel.create()
                .withAnchor("wu")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Wu");
        add(context().pageText(), """
                Master Wu is the leader of the Ninja Team.
                He is wise and powerful, with maxed out stats.
                He can be found in the [Monastery of Spinjitzu](entry://locations/monastery_of_spinjitzu).
                He has the power of Creation and carries a Bamboo Staff.
                This is a new item that can be crafted with bamboo and can be thrown.
                He is responsible for giving players their [elemental powers](category://powers) and helping them unlock [Spinjitzu](category://spinjitzu).
                """);

        page("kai", () -> BookImagePageModel.create()
                .withAnchor("kai")
                .withImages(WikiBookSubProvider.wikiTexture("characters/ninja_team/kai.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Kai");
        add(context().pageText(), """
                Kai can be found in the [Four Weapons Blacksmith](entry://locations/four_weapons).
                He has the power of Fire.
                When fighting, he wil don his iron armor and wield his iron sword.
                """);

        page("nya", () -> BookImagePageModel.create()
                .withAnchor("nya")
                .withImages(WikiBookSubProvider.wikiTexture("characters/ninja_team/nya.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Nya");
        add(context().pageText(), """
                Nya can be found in the [Four Weapons Blacksmith](entry://locations/four_weapons).
                She currently has no power and is an unskilled fighter.
                """);

        page("jay", () -> BookImagePageModel.create()
                .withAnchor("jay")
                .withImages(WikiBookSubProvider.wikiTexture("characters/ninja_team/jay.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Jay");
        add(context().pageText(), """
                Jay can be found in [Ninjago City](entry://locations/ninjago_city).
                He has the power of Lightning and wears an Elytra that he doesn't know how to use.
                """);

        page("cole", () -> BookImagePageModel.create()
                .withAnchor("cole")
                .withImages(WikiBookSubProvider.wikiTexture("characters/ninja_team/cole.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Cole");
        add(context().pageText(), """
                Cole can be found in Mountain biomes.
                He has the power of Earth.
                He is a skilled climber,
                so he is stronger, more durable, and can climb walls.
                """);

        page("zane", () -> BookImagePageModel.create()
                .withAnchor("zane")
                .withImages(WikiBookSubProvider.wikiTexture("characters/ninja_team/zane.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Zane");
        add(context().pageText(), """
                Zane can be found in the Frozen River biome meditating underwater.
                He has the power of Ice.
                """);
    }

    @Override
    protected String entryName() {
        return "Ninja Team";
    }

    @Override
    protected String entryDescription() {
        return """
                The Ninja Team is a group of NPCs that will help you in your journey.
                They will fight back and defend other NPCs, so be careful!
                They will also seek out and fight hostile mobs.
                """;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(MinejagoItems.BAMBOO_STAFF);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
