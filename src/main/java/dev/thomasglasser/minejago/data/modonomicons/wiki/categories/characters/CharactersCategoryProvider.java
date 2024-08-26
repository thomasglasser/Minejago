package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.characters;

import com.klikli_dev.modonomicon.api.datagen.IndexModeCategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import dev.thomasglasser.minejago.world.item.MinejagoItems;

public class CharactersCategoryProvider extends IndexModeCategoryProvider {
    public static final String ID = "characters";

    public CharactersCategoryProvider(SingleBookSubProvider parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        add(new NinjaTeamEntry(this).generate());
    }

    @Override
    protected String categoryName() {
        return "Characters";
    }

    @Override
    protected String categoryDescription() {
        return """
                NPCs and other characters you'll meet in the world of Minejago.
                They will fight back and defend other NPCs, so be careful!
                They will also seek out and fight hostile mobs.
                """;
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(MinejagoItems.BAMBOO_STAFF);
    }

    @Override
    public String categoryId() {
        return ID;
    }
}
