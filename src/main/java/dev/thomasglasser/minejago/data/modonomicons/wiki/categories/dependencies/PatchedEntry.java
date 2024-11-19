package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.dependencies;

import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import net.minecraft.world.item.Items;

public class PatchedEntry extends IndexModeEntryProvider {
    private static final String ID = "patched";

    public PatchedEntry(DependenciesCategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("advancements", () -> BookTextPageModel.create()
                .withAnchor("advancements")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Advancements");
        add(context().pageText(), """
                [Patched](https://modrinth.com/mod/patched) provides the ability to insert Minejago elements into vanilla advancements.
                Examples of this include inserting modded monsters into the Monster Hunter and Monsters Hunted advancements.
                """);
    }

    @Override
    protected String entryName() {
        return "Patched";
    }

    @Override
    protected String entryDescription() {
        return "Patched is a mod that provides the ability to modify parts of json files for data/resource packs to use.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.ANVIL);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
