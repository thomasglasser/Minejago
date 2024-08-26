package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.tea;

import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;

public class TeapotEntry extends IndexModeEntryProvider {
    private static final String ID = "teapot";

    public TeapotEntry(TeaCategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("capacity", () -> BookTextPageModel.create()
                .withAnchor("capacity")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Capacity");
        add(context().pageText(), """
                Teapots can hold up to six cups of tea.
                This is equivalent to two bottles or one bucket of tea.
                It can be used as long as it has at least one cup of tea in it.
                """);
    }

    @Override
    protected String entryName() {
        return "Teapot";
    }

    @Override
    protected String entryDescription() {
        return """
                Used to brew tea,
                the teapot can be carried around and placed wherever you need it while keeping its contents.
                There is no screen or recipe book, so making tea requires a bit of experimentation.
                """;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(MinejagoBlocks.TEAPOT);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
