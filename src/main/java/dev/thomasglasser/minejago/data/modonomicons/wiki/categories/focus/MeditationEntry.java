package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.focus;

import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import net.minecraft.world.item.Items;

public class MeditationEntry extends IndexModeEntryProvider {
    private static final String ID = "meditation";

    public MeditationEntry(FocusCategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("modifiers_image", () -> BookImagePageModel.create()
                .withAnchor("modifiers_image")
                .withImages(WikiBookSubProvider.wikiTexture("focus/meditation/modifiers.png"))
                .withTitle(context().pageTitle()));

        add(context().pageTitle(), "Modifiers");

        page("modifiers", () -> BookTextPageModel.create()
                .withAnchor("modifiers")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Modifiers");
        add(context().pageText(), """
                Focus Modifiers are a [data-driven feature](entry://apis/focus_modifiers) that increase or decrease the amount of focused gained while meditating.
                They can be based on the biome, structure, or dimension the player is in;
                nearby blockstates, entities, or itemstacks;
                potion effects the player has applied;
                and world data including time, weather, height, and temperature (in Celsius).
                The modifications stack,
                so if multiple modifiers are present (such as being in the meadow biome with lit candles)
                all modifiers will be applied.
                If multiple are registered for the same criteria (i.e. if two datapacks add modifiers for the meadow),
                they will also stack.
                """);

        page("modifiers_list_1", () -> BookTextPageModel.create()
                .withAnchor("modifiers_list_1")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Default Modifiers");
        add(context().pageText(), """
                The following is a list of default modifiers that are included with the mod:
                - Biomes
                    - The Meadow adds 0.2 focus
                - Blocks
                    - Lit Candles add 0.25 focus per candle nearby
                    - Water adds 0.05 focus per block nearby
                    - Enchanted Wood adds 0.25 focus per block nearby
                    - Scroll Shelves add 0.2 focus per block nearby
                - Dimensions
                    - The End adds 1 focus
                    - The Nether subtracts 1 focus
                """);

        page("modifiers_list_2", () -> BookTextPageModel.create()
                .withAnchor("modifiers_list_2")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Default Modifiers");
        add(context().pageText(), """
                - Items
                    - The Golden Weapons add 0.5 focus per item in the inventory or nearby
                - World Properties
                    - Night adds 0.5 focus
                    - Dawn and Dusk add 1 focus
                    - Being at a Y level above 100 adds 1 focus
                    - Thunderstorms multiply all focus gained by 1.5
                    - Rain multiplies all focus gained by 1.25
                """);

        page("mega_image", () -> BookImagePageModel.create()
                .withAnchor("mega_image")
                .withImages(WikiBookSubProvider.wikiTexture("focus/meditation/mega.png"))
                .withTitle(context().pageTitle()));

        add(context().pageTitle(), "Mega Meditation");

        page("mega", () -> BookTextPageModel.create()
                .withAnchor("mega")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Mega Meditation");
        add(context().pageText(), """
                When in peak physical and mental condition, you can enter a state of Mega Meditation.
                This state is achieved by having full health, hunger, and focus.
                While in this state, you will gain focus at a decreased rate,
                but the focus gained will last longer.
                \\
                Mega Meditation can not be achieved with the [Hyperfocus effect](entry://focus/tea@hyperfocus).
                """);
    }

    @Override
    protected String entryName() {
        return "Meditation";
    }

    @Override
    protected String entryDescription() {
        return "Meditation is a new mechanic that increases your focus over time.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.CANDLE);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
