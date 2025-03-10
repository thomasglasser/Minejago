package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.tea;

import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import net.minecraft.world.item.alchemy.PotionContents;

public class RegularEntry extends IndexModeEntryProvider {
    private static final String ID = "regular";

    public RegularEntry(TeaCategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("vanilla", () -> BookTextPageModel.create()
                .withAnchor("vanilla")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Vanilla Teas");
        add(context().pageText(), """
                Minejago adds regular tea for every vanilla leaf type.
                These teas are made by combining the leaf with water in a teapot.
                The teas are:
                - Acacia Tea
                - Oak Tea
                - Cherry Tea
                - Spruce Tea
                - Mangrove Tea
                - Jungle Tea
                - Dark Oak Tea
                - Birch Tea
                - Azalea Tea
                - Flowering Azalea Tea
                """);
    }

    @Override
    protected String entryName() {
        return "Regular Tea";
    }

    @Override
    protected String entryDescription() {
        return "The most basic tea, made from normal leaves and water. These do not provide any special effects.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(PotionContents.createItemStack(MinejagoItems.FILLED_TEACUP.get(), MinejagoPotions.OAK_TEA.asReferenceFrom(registries())));
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
