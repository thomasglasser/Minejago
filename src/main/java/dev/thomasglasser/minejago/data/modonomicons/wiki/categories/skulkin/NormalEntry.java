package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.skulkin;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import dev.thomasglasser.minejago.world.entity.skulkin.Skulkin;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;

public class NormalEntry extends IndexModeEntryProvider {
    private static final String ID = "normal";

    public NormalEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("variants_image", () -> BookImagePageModel.create()
                .withAnchor("variants_image")
                .withImages(WikiBookSubProvider.wikiTexture("skulkin/normal/variants.png"))
                .withTitle(context().pageTitle()));

        add(context().pageTitle(), "Variants");

        page("variants", () -> BookTextPageModel.create()
                .withAnchor("variants")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Variants");
        add(context().pageText(), """
                There are four variants of the Normal Skulkin:
                - The Knife variant wears black armor and wields a Bone Knife.
                - The Bow variant wears white armor and wields a Bow.
                - The Speed variant wears blue armor and runs faster than the other variants.
                - The Strength variant wears red armor and is stronger than the other variants.
                """);
    }

    @Override
    protected String entryName() {
        return "Normal Skulkin";
    }

    @Override
    protected String entryDescription() {
        return """
                The most common type of Skulkin.
                Lackeys of Samukai.
                """;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.SPEED));
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
