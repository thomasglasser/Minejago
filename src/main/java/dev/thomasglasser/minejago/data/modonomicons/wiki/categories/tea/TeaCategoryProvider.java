package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.tea;

import com.klikli_dev.modonomicon.api.datagen.IndexModeCategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;

public class TeaCategoryProvider extends IndexModeCategoryProvider {
    public static final String ID = "tea";

    public TeaCategoryProvider(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        add(new RegularEntry(this).generate());
        add(new MagicEntry(this).generate());
        add(new TeapotEntry(this).generate());
    }

    @Override
    protected String categoryName() {
        return "Tea";
    }

    @Override
    protected String categoryDescription() {
        return """
                The drink of choice for many Masters of Spinjitzu, tea can be used as a refreshing drink or a powerful elixir.
                Teas are made in the teapot with [data driven recipes](entry://apis/teapot_brewing_recipe) and can provide a variety of effects.
                """;
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(MinejagoBlocks.TEAPOT);
    }

    @Override
    public String categoryId() {
        return ID;
    }
}
