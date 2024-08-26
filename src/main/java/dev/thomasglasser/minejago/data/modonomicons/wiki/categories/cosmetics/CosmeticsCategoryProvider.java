package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.cosmetics;

import com.klikli_dev.modonomicon.api.datagen.IndexModeCategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import net.minecraft.resources.ResourceLocation;

public class CosmeticsCategoryProvider extends IndexModeCategoryProvider {
    public static final String ID = "cosmetics";
    public static final ResourceLocation HAT_LOCATION = WikiBookSubProvider.wikiTexture("cosmetics/cosmetics.png");

    public CosmeticsCategoryProvider(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        add(new SnapshotEntry(this).generate());
        add(new DevEntry(this).generate());
    }

    @Override
    protected String categoryName() {
        return "Cosmetics";
    }

    @Override
    protected String categoryDescription() {
        return "Exclusive cosmetics for Minejago supporters.";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(HAT_LOCATION);
    }

    @Override
    public String categoryId() {
        return ID;
    }
}
