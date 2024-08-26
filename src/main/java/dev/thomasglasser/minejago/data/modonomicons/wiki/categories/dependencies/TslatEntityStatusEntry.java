package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.dependencies;

import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import net.minecraft.world.item.Items;

public class TslatEntityStatusEntry extends IndexModeEntryProvider {
    private static final String ID = "tslatentitystatus";

    public TslatEntityStatusEntry(DependenciesCategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("power", () -> BookImagePageModel.create()
                .withAnchor("power")
                .withImages(WikiBookSubProvider.wikiTexture("dependencies/tslatentitystatus/power.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Power");
        add(context().pageText(), "TslatEntityStatus is able to show the power information of living entities.");
    }

    @Override
    protected String entryName() {
        return "TslatEntityStatus";
    }

    @Override
    protected String entryDescription() {
        return "[TslatEntityStatus](https://modrinth.com/mod/tslatentitystatus) is a mod to view entity health and other statuses.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.SPYGLASS);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
