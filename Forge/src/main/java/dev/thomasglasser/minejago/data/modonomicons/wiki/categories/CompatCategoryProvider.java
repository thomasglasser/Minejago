package dev.thomasglasser.minejago.data.modonomicons.wiki.categories;

import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.world.item.Items;

public class CompatCategoryProvider extends CategoryProvider {
    public CompatCategoryProvider(BookProvider parent) {
        super(parent, "compat");
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[]
                {
                        "_____v_______________",
                        "_____________________",
                        "_____________________",
                        "_____________________",
                        "_____________________",
                        "_____________________",
                        "_____________________",
                        "_____________________"
                };
    }

    @Override
    protected void generateEntries() {
        add(generateVisualEntry().withLocation(entryMap.get('v')));
    }

    @Override
    protected BookCategoryModel generateCategory() {
        lang().add(context().categoryName(), "Mod Compatibilities & Enhancements");

        return BookCategoryModel.create(this.modLoc(this.context().categoryId()), this.context().categoryName())
                .withIcon(MinejagoItems.IRON_SPEAR.getId());
    }

    protected BookEntryModel generateVisualEntry()
    {
        this.context().entry("visual");
        lang().add(context().entryName(), "Visual");
        lang().add(context().entryDescription(), "Mods that make the world of Minejago more visually appealing or add more viewing details");

        this.context().page("playeranimator_text");
        var playerAnimatorText = BookTextPageModel.builder()
                .withTitle(context().pageTitle())
                .withText(context().pageText())
                .build();
        lang().add(context().pageTitle(), "playerAnimator");
        lang().add(context().pageText(), "The playerAnimator mod adds animations for actions such as performing spinjitzu and using the golden weapons, making your moves look much cooler.");

        this.context().page("playeranimator_image");
        var playerAnimatorImage = BookImagePageModel.builder()
                .withImages(modLoc("textures/modonomicon/wiki/compat/visual/playeranimator.png"))
                .withText(context().pageText())
                .build();
        lang().add(context().pageText(), "Using the Scythe of Quakes with playerAnimator installed");

        return BookEntryModel.create(
                        this.modLoc(this.context().categoryId() + "/" + this.context().entryId()),
                        this.context().entryName()
                )
                .withDescription(this.context().entryDescription())
                .withIcon(Items.SPYGLASS)
                .withPages(
                        playerAnimatorText,
                        playerAnimatorImage
                );
    }
}
