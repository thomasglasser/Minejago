package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.skills;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import dev.thomasglasser.minejago.data.modonomicons.MinejagoBookProvider;
import net.minecraft.world.item.Items;

public class AgilityEntry extends IndexModeEntryProvider {
    public static final String ID = "agility";

    public AgilityEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("agility", () -> BookImagePageModel.create()
                // TODO: Agility icon
                .withImages(MinejagoBookProvider.itemLoc(Items.DIAMOND_BOOTS))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Agility");
        add(context().pageText(), """
                When sprinting and/or jumping, you train agility.
                Every 100 seconds of sprinting or jumping (or 50 seconds if both sprinting and jumping), you gain 1 agility level.
                As you level up, you will be able to sprint faster and jump higher.
                """);
    }

    @Override
    protected String entryName() {
        return "Agility";
    }

    @Override
    protected String entryDescription() {
        return "Agility allows you to train your speed and jump height by running and jumping.";
    }

    @Override
    protected BookIconModel entryIcon() {
        // TODO: Agility icon
        return BookIconModel.create(Items.DIAMOND_BOOTS);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
