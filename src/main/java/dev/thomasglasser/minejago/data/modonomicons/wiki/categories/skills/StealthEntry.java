package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.skills;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import dev.thomasglasser.minejago.data.modonomicons.MinejagoBookProvider;
import net.minecraft.world.item.Items;

public class StealthEntry extends IndexModeEntryProvider {
    public static final String ID = "stealth";

    public StealthEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("stealth", () -> BookImagePageModel.create()
                // TODO: Stealth icon
                .withImages(MinejagoBookProvider.itemLoc(Items.GRAY_WOOL))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Stealth");
        add(context().pageText(), """
                When sneaking around mobs or vibration sensors, you train stealth.
                Every 100 seconds of sneaking, you gain 1 stealth level.
                As you level up, you will be able to sneak faster and become less detectable by mobs.
                Once you reach level 10, you will become undetectable to vibration sensors.
                """);
    }

    @Override
    protected String entryName() {
        return "Stealth";
    }

    @Override
    protected String entryDescription() {
        return "Stealth allows you to train your sneaking ability by sneaking around mobs and vibration sensors.";
    }

    @Override
    protected BookIconModel entryIcon() {
        // TODO: Stealth icon
        return BookIconModel.create(Items.GRAY_WOOL);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
