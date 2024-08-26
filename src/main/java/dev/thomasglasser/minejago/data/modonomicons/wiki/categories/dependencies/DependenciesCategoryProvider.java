package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.dependencies;

import com.klikli_dev.modonomicon.api.datagen.IndexModeCategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import dev.thomasglasser.minejago.world.item.MinejagoItems;

public class DependenciesCategoryProvider extends IndexModeCategoryProvider {
    public static final String ID = "dependencies";

    public DependenciesCategoryProvider(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        add(new JadeEntry(this).generate());
        add(new JeiEntry(this).generate());
        add(new ModonomiconEntry(this).generate());
        add(new PatchedEntry(this).generate());
        add(new PlayerAnimatorEntry(this).generate());
        add(new RyoamicLightsEntry(this).generate());
        add(new TslatEntityStatusEntry(this).generate());
    }

    @Override
    public String categoryId() {
        return ID;
    }

    @Override
    protected String categoryName() {
        return "Optional Dependencies";
    }

    @Override
    protected String categoryDescription() {
        return "Mods that are not required but enhance the Minejago experience.";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(MinejagoItems.SCYTHE_OF_QUAKES);
    }
}
