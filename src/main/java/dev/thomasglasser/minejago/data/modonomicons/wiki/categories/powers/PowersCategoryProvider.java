package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.powers;

import com.klikli_dev.modonomicon.api.datagen.IndexModeCategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;

public class PowersCategoryProvider extends IndexModeCategoryProvider {
    private static final String ID = "powers";

    public PowersCategoryProvider(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        add(new DiscoveryEntry(this).generate());
        add(new GiEntry(this).generate());
        add(new GoldenWeaponsEntry(this).generate());
        add(new DragonsEntry(this).generate());
        add(new ConfigurationEntry(this).generate());
    }

    @Override
    protected String categoryName() {
        return "Powers";
    }

    @Override
    protected String categoryDescription() {
        return "Super abilities (elemental or otherwise) that can be discovered and used by the player.";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(modLoc("textures/power/lightning.png"), 32, 32);
    }

    @Override
    public String categoryId() {
        return ID;
    }
}
