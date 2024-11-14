package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.expansions;

import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import dev.thomasglasser.minejago.world.item.MinejagoItemUtils;
import net.minecraft.world.item.alchemy.Potions;

public class PotionPotEntry extends IndexModeEntryProvider {
    private static final String ID = "potion_pot";

    public PotionPotEntry(ExpansionsCategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("usage_image", () -> BookImagePageModel.create()
                .withAnchor("usage_image")
                .withImages(WikiBookSubProvider.wikiTexture("expansions/potion_pot/usage.png"))
                .withTitle(context().pageTitle()));

        add(context().pageTitle(), "Usage");

        page("usage", () -> BookTextPageModel.create()
                .withAnchor("usage")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Usage");
        add(context().pageText(), """
                Making vanilla tea involves using the same ingredients as the brewing stand.
                For example, adding nether wart to water in a teapot will create Awkward Tea.
                Adding a fermented spider eye to Awkward Tea will create Weakness Tea.
                """);
    }

    @Override
    protected String entryName() {
        return "Potion Pot Pack";
    }

    @Override
    protected String entryDescription() {
        return "The Potion Pot Pack is a datapack that allows brewing vanilla potions in teapots";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(MinejagoItemUtils.fillTeacup(Potions.REGENERATION));
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
