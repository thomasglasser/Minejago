package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.support;

import com.klikli_dev.modonomicon.api.datagen.IndexModeCategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class SupportCategoryProvider extends IndexModeCategoryProvider {
    public static final String ID = "support";

    public SupportCategoryProvider(SingleBookSubProvider parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        add(new ModPagesEntry(this).generate());
        add(new DonationsEntry(this).generate());
        add(new WikiEntry(this).generate());
        add(new ContactEntry(this).generate());
    }

    @Override
    public String categoryId() {
        return ID;
    }

    @Override
    protected String categoryName() {
        return "Support";
    }

    @Override
    protected String categoryDescription() {
        return "Finding, supporting, and getting help with Minejago.";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(Items.COMPASS);
    }
}
