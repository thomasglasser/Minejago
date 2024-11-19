package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.powers;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import net.minecraft.world.item.Items;

public class DragonsEntry extends IndexModeEntryProvider {
    private static final String ID = "dragons";

    public DragonsEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("about", () -> BookTextPageModel.create()
                .withAnchor("about")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "About");
        add(context().pageText(), """
                Dragons can be found protecting the [Golden Weapons locations](category://locations).
                They will defend themselves and their [Golden Weapon](entry://powers/golden_weapons) if provoked.
                They will also fight monsters and other hostile mobs.
                They can shoot projectiles and fight up close.
                """);

        page("bonding_and_taming", () -> BookTextPageModel.create()
                .withAnchor("bonding_and_taming")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Bonding and Taming");
        add(context().pageText(), """
                To bond with and tame a dragon, you can feed it food and treats and interact with it.
                Any dragon can be bonded with, even if tamed or of another power.
                Bonding and taming dragons requires [focus](category://focus) and patience.
                Once the bond is high enough,
                you can tame a dragon with a matching power.
                Once tamed, you can equip the dragon with a saddle and chest and fly it around.
                """);
    }

    @Override
    protected String entryName() {
        return "Dragons";
    }

    @Override
    protected String entryDescription() {
        return "Powerful creatures that protect the Golden Weapons and their wielders and can travel between worlds";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.SADDLE);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
