package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.dependencies;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import net.minecraft.world.item.Items;

public class JadeEntry extends IndexModeEntryProvider {
    private static final String ID = "jade";

    public JadeEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("teapot", () -> BookImagePageModel.create()
                .withAnchor("teapot")
                .withImages(WikiBookSubProvider.wikiTexture("dependencies/jade/teapot.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Teapot");
        add(context().pageText(), "[Jade](https://modrinth.com/mod/jade) is able to show the contents and brewing information of a teapot.");

        page("golden_weapons_map", () -> BookImagePageModel.create()
                .withAnchor("golden_weapons_map")
                .withImages(WikiBookSubProvider.wikiTexture("dependencies/jade/golden_weapons_map.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Golden Weapons Map");
        add(context().pageText(), "[Jade](https://modrinth.com/mod/jade) is able to show whether a Four Weapons sign contains a Golden Weapons Map.");

        page("element", () -> BookImagePageModel.create()
                .withAnchor("element")
                .withImages(WikiBookSubProvider.wikiTexture("dependencies/jade/element.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Element");
        add(context().pageText(), "[Jade](https://modrinth.com/mod/jade) is able to show the element information of living entities.");
    }

    @Override
    protected String entryName() {
        return "Jade";
    }

    @Override
    protected String entryDescription() {
        return "Jade is the information HUD mod.";
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
