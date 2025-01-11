package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.skulkin;

import com.klikli_dev.modonomicon.api.datagen.IndexModeCategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import dev.thomasglasser.minejago.world.entity.skulkin.Skulkin;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;

public class SkulkinCategoryProvider extends IndexModeCategoryProvider {
    public static final String ID = "skulkin";

    public SkulkinCategoryProvider(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        add(new NormalEntry(this).generate());
        add(new RaidEntry(this).generate());
    }

    @Override
    protected String categoryName() {
        return "Skulkin";
    }

    @Override
    protected String categoryDescription() {
        return """
                Skeletal creatures of the Underworld.
                They can drop their armor and weapons on death.
                """;
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.SPEED));
    }

    @Override
    public String categoryId() {
        return ID;
    }
}
