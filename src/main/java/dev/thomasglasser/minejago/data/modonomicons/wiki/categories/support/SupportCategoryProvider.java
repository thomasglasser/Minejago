package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.support;

import com.klikli_dev.modonomicon.api.datagen.IndexModeCategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class SupportCategoryProvider extends IndexModeCategoryProvider {
    public static final String ID = "support";

    public SupportCategoryProvider(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        add(new ContactEntry(this).generate());
        add(new DonateEntry(this).generate());
    }

    @Override
    protected String categoryName() {
        return "Support";
    }

    @Override
    protected String categoryDescription() {
        return "Support for the mod and supporting the mod.";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(Items.ENCHANTED_BOOK);
    }

    @Override
    public String categoryId() {
        return ID;
    }
}
