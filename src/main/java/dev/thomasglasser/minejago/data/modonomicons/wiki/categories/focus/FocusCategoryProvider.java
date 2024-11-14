package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.focus;

import com.klikli_dev.modonomicon.api.datagen.IndexModeCategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;

public class FocusCategoryProvider extends IndexModeCategoryProvider {
    public static final String ID = "focus";

    public FocusCategoryProvider(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        add(new UsingEntry(this).generate());
        add(new MeditationEntry(this).generate());
        add(new TeaEntry(this).generate());
    }

    @Override
    public String categoryId() {
        return ID;
    }

    @Override
    protected String categoryName() {
        return "Focus";
    }

    @Override
    protected String categoryDescription() {
        return """
                A new gameplay mechanic that allows you to focus your energy to gain powerful abilities.
                The bar is tinted to your power color.
                By default, the bar is placed above your hunger bar, but this can be changed in the client config.
                """;
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(modLoc("textures/mob_effect/hyperfocus.png"), 18, 18);
    }
}
