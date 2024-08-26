package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.decorations;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.Items;

public class PaintingsEntry extends IndexModeEntryProvider {
    private static final String ID = "paintings";

    public PaintingsEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("placeable", () -> BookImagePageModel.create()
                .withAnchor("placeable")
                .withImages(paintingLoc(MinejagoPaintingVariants.A_MORNING_BREW), paintingLoc(MinejagoPaintingVariants.AMBUSHED), paintingLoc(MinejagoPaintingVariants.BEFORE_THE_STORM), paintingLoc(MinejagoPaintingVariants.CREATION), paintingLoc(MinejagoPaintingVariants.EARTH), paintingLoc(MinejagoPaintingVariants.FIRE), paintingLoc(MinejagoPaintingVariants.FRUIT_COLORED_NINJA), paintingLoc(MinejagoPaintingVariants.ICE), paintingLoc(MinejagoPaintingVariants.IT_TAKES_A_VILLAGE), paintingLoc(MinejagoPaintingVariants.LIGHTNING), paintingLoc(MinejagoPaintingVariants.NEEDS_HAIR_GEL), paintingLoc(MinejagoPaintingVariants.NOT_FOR_FURNITURE))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Placeable");
        add(context().pageText(), """
                The mod adds a variety of placeable paintings that can be used to brighten up your world.
                """);

        page("structure", () -> BookImagePageModel.create()
                .withAnchor("structure")
                .withImages(paintingLoc(MinejagoPaintingVariants.FOUR_WEAPONS), paintingLoc(MinejagoPaintingVariants.THE_FOURTH_MOUNTAIN), paintingLoc(MinejagoPaintingVariants.IT_TAKES_A_VILLAGE_WRECKED))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Structure");
        add(context().pageText(), """
                There are also paintings that generate as part of structures in the world that can not be placed by the player.
                """);
    }

    @Override
    protected String entryName() {
        return "Paintings";
    }

    @Override
    protected String entryDescription() {
        return "Decorative paintings to hang on your walls.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.PAINTING);
    }

    @Override
    protected String entryId() {
        return ID;
    }

    public static ResourceLocation paintingLoc(ResourceKey<PaintingVariant> painting) {
        return painting.location().withPrefix("textures/painting/");
    }
}
