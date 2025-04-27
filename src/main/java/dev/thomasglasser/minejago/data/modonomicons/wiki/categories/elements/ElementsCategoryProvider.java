package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.elements;

import com.klikli_dev.modonomicon.api.datagen.IndexModeCategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.minejago.world.entity.element.Elements;

public class ElementsCategoryProvider extends IndexModeCategoryProvider {
    private static final String ID = "elements";

    public ElementsCategoryProvider(ModonomiconProviderBase parent) {
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
        return "Elements";
    }

    @Override
    protected String categoryDescription() {
        return "Super abilities relating to an element that can be discovered and used by the player.";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(Element.getIcon(Elements.LIGHTNING), 32, 32);
    }

    @Override
    public String categoryId() {
        return ID;
    }
}
