package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.decorations;

import com.klikli_dev.modonomicon.api.datagen.IndexModeCategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;

public class DecorationsCategoryProvider extends IndexModeCategoryProvider {
    public static final String ID = "decorations";

    public DecorationsCategoryProvider(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        add(new PaintingsEntry(this).generate());
        add(new ScrollEntry(this).generate());
    }

    @Override
    protected String categoryName() {
        return "Decorations";
    }

    @Override
    protected String categoryDescription() {
        return "Decorative blocks to make your world look nice.";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(MinejagoBlocks.SCROLL_SHELF);
    }

    @Override
    public String categoryId() {
        return ID;
    }
}
