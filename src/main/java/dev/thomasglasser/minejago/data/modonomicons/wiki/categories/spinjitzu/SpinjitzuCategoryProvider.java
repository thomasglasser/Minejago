package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.spinjitzu;

import com.klikli_dev.modonomicon.api.datagen.IndexModeCategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import net.minecraft.resources.ResourceLocation;

public class SpinjitzuCategoryProvider extends IndexModeCategoryProvider {
    public static final String ID = "spinjitzu";
    public static final ResourceLocation SPINJITZU_TEXTURE = WikiBookSubProvider.wikiTexture("spinjitzu/spinjitzu.png");

    public SpinjitzuCategoryProvider(SingleBookSubProvider parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        add(new LearningEntry(this).generate());
        add(new AbilitiesEntry(this).generate());
    }

    @Override
    public String categoryId() {
        return ID;
    }

    @Override
    protected String categoryName() {
        return "Spinjitzu";
    }

    @Override
    protected String categoryDescription() {
        return "A martial art that involves the user spinning rapidly to create a tornado of energy. Toggled with 'N' by default.";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(SPINJITZU_TEXTURE, 32, 32);
    }
}
