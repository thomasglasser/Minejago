package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.apis;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import net.minecraft.world.item.Items;

public class FocusModifiersEntry extends IndexModeEntryProvider {
    private static final String ID = "focus_modifiers";

    public FocusModifiersEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("example", () -> BookTextPageModel.create()
                .withAnchor("example")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "JSON Format");
        add(context().pageText(), """
                An example JSON focus modifier is shown below:
                ```json
                {
                  "block": "minejago:scroll_shelf",
                  "modifier": 0.2,
                  "operation": "addition"
                }
                ```
                Now, let's break it down.
                """);

        page("format", () -> BookTextPageModel.create()
                .withAnchor("format")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "JSON Format");
        add(context().pageText(), """
                The target field is different depending on what is being considered.
                In this case, the target is a block, so the "block" field is used.
                For all possible targets, see the next page.
                \\
                The "modifier" field specifies the amount of focus to modify the base amount by.
                \\
                The "operation" field specifies how to modify the focus.
                Possible values are "addition", "subtraction", "multiplication", and "division".
                """);

        page("targets_1", () -> BookTextPageModel.create()
                .withAnchor("targets_1")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Targets");
        add(context().pageText(), """
                The following targets are available for focus modifiers:
                - "block": Modifies the focus gained when meditating near the specified block with the default state.
                - "state": Modifies the focus gained when meditating near the specified block with the specified state.
                - "entity_type": Modifies the focus gained when meditating near the specified entity type.
                    - "nbt": Modifies the focus gained when meditating near the specified entity type with the specified NBT.
                - "item": Modifies the focus gained when meditating near the specified item with the default stack data in an item frame, on the ground, or in the inventory.
                - "stack": Modifies the focus gained when meditating near the specified item with the specified stack data in an item frame, on the ground, or in the inventory.
                """);

        page("targets_2", () -> BookTextPageModel.create()
                .withAnchor("targets_2")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Targets");
        add(context().pageText(), """
                - "day_time": Modifies the focus gained when meditating at the specified time of day.
                - "weather": Modifies the focus gained when meditating during the specified weather.
                - "y": Modifies the focus gained when meditating at the specified Y level.
                - "temperature": Modifies the focus gained when meditating at the specified temperature (in Celsius).
                \\
                The "key" argument is unique in that it is used with biomes, dimensions, mob effects, and structures.
                It determines the type of the key based on the file location.
                """);
    }

    @Override
    protected String entryName() {
        return "Focus Modifiers";
    }

    @Override
    protected String entryDescription() {
        return """
                Focus modifiers are a data-driven way for datapacks and mods to alter the amount of focus gained during meditation depending on a variety of factors.
                A generator for these modifiers is available [here](https://jsons.thomasglasser.dev/partners/minejago/focus-modifier/).
                """;
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
