package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.locations;

import com.klikli_dev.modonomicon.api.datagen.IndexModeCategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.minecraft.world.item.Items;

public class LocationsCategoryProvider extends IndexModeCategoryProvider {
    public static final String ID = "locations";

    public LocationsCategoryProvider(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        add(new FourWeaponsEntry(this).generate());
        add(new MonasteryOfSpinjitzuEntry(this).generate());
        add(new CaveOfDespairEntry(this).generate());
        add(new NinjagoCityEntry(this).generate());
    }

    @Override
    protected String categoryName() {
        return "Locations";
    }

    @Override
    protected String categoryDescription() {
        return "The key places of the world of Minejago.";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(Items.FILLED_MAP);
    }

    @Override
    public String categoryId() {
        return ID;
    }
}
