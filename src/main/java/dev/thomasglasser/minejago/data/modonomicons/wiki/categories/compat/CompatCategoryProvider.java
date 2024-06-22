package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.compat;

import com.klikli_dev.modonomicon.api.datagen.IndexModeCategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import dev.thomasglasser.minejago.world.item.MinejagoItems;

public class CompatCategoryProvider extends IndexModeCategoryProvider
{

    public static final String ID = "compat";

    public CompatCategoryProvider(SingleBookSubProvider parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        add(new VisualEntry(this).generate());
    }

    @Override
    public String categoryId()
    {
        return ID;
    }

    @Override
    protected String categoryName()
    {
        return "Mod Compatibilities & Enhancements";
    }

    @Override
    protected String categoryDescription()
    {
        return "Optional mods that enhance the Minejago experience.";
    }

    @Override
    protected BookIconModel categoryIcon()
    {
        return BookIconModel.create(MinejagoItems.SCYTHE_OF_QUAKES);
    }
}
