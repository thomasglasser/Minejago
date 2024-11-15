package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.skills;

import com.klikli_dev.modonomicon.api.datagen.IndexModeCategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import dev.thomasglasser.minejago.Minejago;

public class SkillsCategoryProvider extends IndexModeCategoryProvider {
    public static final String ID = "skills";

    public SkillsCategoryProvider(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        add(new AgilityEntry(this).generate());
        add(new StealthEntry(this).generate());
        add(new DexterityEntry(this).generate());
        add(new ToolProficiencyEntry(this).generate());
    }

    @Override
    public String categoryId() {
        return ID;
    }

    @Override
    protected String categoryName() {
        return "Skills";
    }

    @Override
    protected String categoryDescription() {
        return """
                A new gameplay mechanic that increases your stats as you train and level up.
                Upon leveling up, you will hear a chime and see a message in the action bar.
                Your stats will then increase by a small amount.
                """;
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(Minejago.modLoc("textures/gui/skill/agility.png"), 32, 39);
    }
}
