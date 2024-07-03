package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.expansions;

import com.klikli_dev.modonomicon.api.datagen.IndexModeCategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;

public class ExpansionsCategoryProvider extends IndexModeCategoryProvider {
    public static final String ID = "expansions";

    public ExpansionsCategoryProvider(SingleBookSubProvider parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        add(new ImmersionPackEntry(this).generate());
        add(new PotionPotEntry(this).generate());
    }

    @Override
    public String categoryId() {
        return ID;
    }

    @Override
    protected String categoryName() {
        return "Expansions";
    }

    @Override
    protected String categoryDescription() {
        return "Official mods and packs that add optional improvements to gameplay.";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(MinejagoBlocks.TEAPOT);
    }
}
