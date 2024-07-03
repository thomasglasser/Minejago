package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.expansions;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.packs.MinejagoPacks;
import dev.thomasglasser.minejago.world.item.MinejagoItemUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.phys.Vec2;

public class PotionPotEntry extends IndexModeEntryProvider {
    public static final String ID = "potion_pot";

    public PotionPotEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        // Summary Text
        page("summary_text", () -> BookTextPageModel.create()
                .withTitle(entryName()))
                        .withText(context().pageText());

        pageText("""
                The Potion Pot Pack is a data pack that allows the player to brew vanilla potions in teapots using the vanilla brewing recipes.
                These recipes may be overridden by a datapack, which will have no effect on the brewing stand, but if the brewing recipes have been changed by a mod, the teapot recipes will remain the same as vanilla unless also overridden by a data pack.
                """);

        // Summary Image
        page("summary_image", () -> BookImagePageModel.create()
                .withImages(modLoc("textures/modonomicon/wiki/expansions/potion_pot_pack/regeneration_pot.png")));
    }

    @Override
    public BookEntryModel generate(Vec2 location) {
        this.context().entry(this.entryId());

        var entry = BookEntryModel.create(
                this.modLoc(this.context().categoryId() + "/" + this.context().entryId()),
                entryName())
                .withDescription(entryDescription());

        entry.withIcon(this.entryIcon());
        entry.withLocation(location);
        entry.withEntryBackground(this.entryBackground());

        this.entry = this.additionalSetup(entry);

        this.generatePages();

        this.parent.add(this.entry);
        return this.entry;
    }

    @Override
    protected String entryName() {
        return MinejagoPacks.POTION_POT.titleKey();
    }

    @Override
    protected String entryDescription() {
        return MinejagoPacks.POTION_POT.descriptionKey();
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(MinejagoItemUtils.fillTeacup(Potions.REGENERATION));
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
