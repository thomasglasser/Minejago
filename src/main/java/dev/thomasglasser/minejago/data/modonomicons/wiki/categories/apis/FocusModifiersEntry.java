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
        page("generator", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Generator");
        add(context().pageText(), """
                A generator for these modifiers is available online [here](https://beta-jsons.thomasglasser.dev/partners/).
                """);

        page("format", () -> BookTextPageModel.create()
                .withAnchor("format")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "JSON Format");
        add(context().pageText(), """
                Focus modifiers have a type that determines what they apply to.
                The following types are available:
                - block
                - entity
                - item
                - location
                - world\\
                The "modifier" field specifies the amount of focus to modify the base amount by. This can be a positive or negative decimal number.\\
                The "operation" field specifies how to modify the focus.
                The following operations are available:
                - addition
                - subtraction
                - multiplication
                - division
                """);
    }

    @Override
    protected String entryName() {
        return "Focus Modifiers";
    }

    @Override
    protected String entryDescription() {
        return """
                Focus modifiers are a data-driven way for data packs and mods to alter the amount of focus gained during meditation depending on a variety of factors.
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
