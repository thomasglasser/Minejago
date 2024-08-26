package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.focus;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.spinjitzu.SpinjitzuCategoryProvider;
import dev.thomasglasser.minejago.world.focus.FocusConstants;

public class UsingEntry extends IndexModeEntryProvider {
    private static final String ID = "using";

    public UsingEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("spinjitzu", () -> BookTextPageModel.create()
                .withAnchor("spinjitzu")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Spinjitzu");
        add(context().pageText(), String.format("""
                Learning Spinjitzu requires %s focus and will consume %s focus.
                Using Spinjitzu requires %s focus and will consume %s focus.
                """, FocusConstants.LEARN_SPINJITZU_LEVEL, FocusConstants.EXHAUSTION_LEARN_SPINJITZU,
                FocusConstants.SPINJITZU_LEVEL, FocusConstants.EXHAUSTION_SPINJITZU));

        page("dragon", () -> BookTextPageModel.create()
                .withAnchor("dragon")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Dragon Bonding and Taming");
        add(context().pageText(), String.format("""
                Bonding with a dragon requires %s focus and will consume %s focus.
                Taming a dragon requires %s focus and will consume %s focus.
                """, FocusConstants.DRAGON_TALK_LEVEL, FocusConstants.EXHAUSTION_DRAGON_TALK,
                FocusConstants.DRAGON_TAME_LEVEL, FocusConstants.EXHAUSTION_DRAGON_TAME));

        page("golden_weapon", () -> BookTextPageModel.create()
                .withAnchor("golden_weapon")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Using a Golden Weapon");
        add(context().pageText(), String.format("""
                Using a Golden Weapon requires %s focus and will consume %s focus.
                """, FocusConstants.GOLDEN_WEAPON_LEVEL, FocusConstants.EXHAUSTION_GOLDEN_WEAPON));

        page("insomnia", () -> BookTextPageModel.create()
                .withAnchor("insomnia")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Insomnia");
        add(context().pageText(), String.format("""
                If a player hasn't slept for 1 day, they will start to lose focus rapidly.
                This will consume %s focus per tick.
                """, FocusConstants.EXHAUSTION_INSOMNIA));
    }

    @Override
    protected String entryName() {
        return "Using Focus";
    }

    @Override
    protected String entryDescription() {
        return "Focus is decreased by using mod abilities. There are also level requirements for some abilities.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(SpinjitzuCategoryProvider.SPINJITZU_TEXTURE, 32, 32);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
