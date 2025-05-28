package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.apis;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import net.minecraft.world.item.Items;

public class DataMapsEntry extends IndexModeEntryProvider {
    private static final String ID = "data_maps";

    public DataMapsEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("generator", () -> BookTextPageModel.create()
                .withAnchor("generator")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Generator");
        add(context().pageText(), """
                A generator for these maps is available online [here](https://beta-jsons.thomasglasser.dev/partners/).
                """);

        page("potion_fillables", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        pageTitle("Potion Fillables");
        pageText("""
                The potion fillables item map is used to define the fillability of items containing potions.
                It stores the amount of cups and the filled itemstack.
                Note that the item should function as a potion, with the potion_contents component.
                When adding entries, ensure the item works as intended.
                """);

        page("potion_drainables", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        pageTitle("Potion Drainables");
        pageText("""
                The potion drainables item map is used to define the drainability of items containing potions.
                It stores the potion, the amount of cups, and the drained itemstack.
                Note that the item should function as a potion, with the potion_contents component.
                When adding entries, ensure the item works as intended.
                """);
    }

    @Override
    protected String entryName() {
        return "Data Maps";
    }

    @Override
    protected String entryDescription() {
        return "Maps used for mod functionality";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.WATER_BUCKET);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
