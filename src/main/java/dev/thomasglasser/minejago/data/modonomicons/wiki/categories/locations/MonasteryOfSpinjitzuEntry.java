package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.locations;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.data.modonomicons.MinejagoBookProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import dev.thomasglasser.minejago.world.item.MinejagoItems;

public class MonasteryOfSpinjitzuEntry extends IndexModeEntryProvider {
    private static final String ID = "monastery_of_spinjitzu";

    public MonasteryOfSpinjitzuEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("spinjitzu_course_image", () -> BookImagePageModel.create()
                .withAnchor("spinjitzu_course_image")
                .withImages(WikiBookSubProvider.wikiTexture("locations/monastery_of_spinjitzu/spinjitzu_course.png"))
                .withTitle(context().pageTitle()));

        add(context().pageTitle(), "Spinjitzu Course");

        page("spinjitzu_course", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Spinjitzu Course");
        add(context().pageText(), """
                The Spinjitzu Course is a training ground for learning [Spinjitzu](category://spinjitzu).
                It is a series of obstacles that test the skills of a ninja.
                It must be activated using the Dragon Button, which can be found at the front of the Monastery.
                One can practice the course for as long as they like,
                but in order to unlock [Spinjitzu](category://spinjitzu),
                they must interact with [Master Wu](entry://characters/ninja_team@wu) and prompt him to begin drinking his tea.
                He will then track the player as they complete the course.
                If the player interacts with every element of the course and interacts with Wu again before he finishes his tea,
                they will unlock Spinjitzu.
                """);

        page("fancy_finds", () -> BookImagePageModel.create()
                .withAnchor("fancy_finds")
                .withImages(MinejagoBookProvider.itemLoc(MinejagoItems.POTTERY_SHERD_MASTER), MinejagoBookProvider.itemLoc(MinejagoItems.POTTERY_SHERD_YIN_YANG), MinejagoBookProvider.itemLoc(MinejagoItems.POTTERY_SHERD_DRAGONS_HEAD), MinejagoBookProvider.itemLoc(MinejagoItems.POTTERY_SHERD_DRAGONS_TAIL), MinejagoBookProvider.itemLoc(MinejagoItems.LOTUS_ARMOR_TRIM_SMITHING_TEMPLATE), MinejagoBookProvider.itemLoc(MinejagoItems.NINJA_BANNER_PATTERN))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Fancy Finds");
        add(context().pageText(), """
                The Monastery of Spinjitzu is also home to a variety of unique items.
                Throughout the Monastery, there are decorated pots using the Pottery Sherds Master, Yin Yang, Dragon's Head, and Dragon's Tail.
                In a chest in [Master Wu's](entry://characters/ninja_team@wu) room,
                there is a chance to find one or more of the following items:
                - Lotus Armor Trim Smithing Template
                - Ninja Banner Pattern
                """);

        page("fateful_findings_image", () -> BookImagePageModel.create()
                .withAnchor("fateful_findings_image")
                .withImages(WikiBookSubProvider.wikiTexture("locations/monastery_of_spinjitzu/fateful_findings.png"))
                .withTitle(context().pageTitle()));

        add(context().pageTitle(), "Fateful Findings");

        page("fateful_findings", () -> BookTextPageModel.create()
                .withAnchor("fateful_findings")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Fateful Findings");
        add(context().pageText(), """
                The Monastery of Spinjitzu is also home to [Master Wu](entry://characters/ninja_team@wu).
                He can be found in the Monastery walking around the Monastery or outside defending the Monastery from enemies.
                """);
    }

    @Override
    protected String entryName() {
        return "Monastery of Spinjitzu";
    }

    @Override
    protected String entryDescription() {
        return """
                The home of [Master Wu](entry://characters/ninja_team@wu),
                the Monastery of Spinjitzu is a place of training, meditation, and relaxation.
                It can be found in Mountain Peaks biomes.
                """;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(MinejagoItems.CENTER_SPINJITZU_COURSE_ELEMENT);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
