package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.skills;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.Minejago;

public class ToolProficiencyEntry extends IndexModeEntryProvider {
    public static final String ID = "tool_proficiency";

    public ToolProficiencyEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("image", () -> BookImagePageModel.create()
                .withImages(Minejago.modLoc("textures/gui/skill/tool_proficiency.png"))
                .withTitle(context().pageTitle()));

        add(context().pageTitle(), "Tool Proficiency");

        page("description", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Tool Proficiency");
        add(context().pageText(), """
                When breaking blocks or attacking with the proper tool, you train tool proficiency.
                Every 100 damage points or blocks broken (can be less depending on break time), you gain 1 tool proficiency level.
                As you level up, you will be able to break blocks and attack quicker and deal more damage while using tools.
                """);
    }

    @Override
    protected String entryName() {
        return "Tool Proficiency";
    }

    @Override
    protected String entryDescription() {
        return "Tool Proficiency allows you to train your tool speed and damage by breaking blocks and attacking with the proper tool.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Minejago.modLoc("textures/gui/skill/tool_proficiency.png"), 32, 39);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
