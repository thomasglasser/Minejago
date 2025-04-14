package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.apis;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import net.minecraft.world.item.Items;

public class TagsEntry extends IndexModeEntryProvider {
    private static final String ID = "tags";

    public TagsEntry(CategoryProviderBase parent) {
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
                A generator for vanilla tags is available online [here](https://snapshot-jsons.thomasglasser.dev/generators/) and for mod tags [here](https://snapshot-jsons.thomasglasser.dev/partners/).
                """);

        page("biome", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Biomes");
        add(context().pageText(), """
                There are biome tags for the mod's structures and trees. You can add other biomes to these tags for compatibility with biome mods.
                """);

        page("block", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Blocks");
        add(context().pageText(), """
                There is a block tag for the mod's teapots. This tag can be used to add teapot variants.
                """);

        page("damage_type", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Damage Types");
        add(context().pageText(), """
                There is a damage type tag for Golden Weapons to determine if weapons can be damaged by certain damage types.
                """);

        page("entity_type", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Entity Types");
        add(context().pageText(), """
                There are entity type tags for the mod's entity categories, such as Skulkins and Dragons.
                These can be used to add more variants of these entities.
                """);

        page("item", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Items");
        add(context().pageText(), """
                There are item tags for the mod's items, such as Golden Weapons, Dragon Foods, and Gi.
                These can be used to add more variants of these items.
                """);

        page("element", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Powers");
        add(context().pageText(), """
                There are element tags for the elemental powers used by Golden Weapons.
                These can be used to allow custom powers to use a Golden Weapon.
                """);

        page("structure", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Structures");
        add(context().pageText(), """
                There are structure tags for the mod's structures, such as Ninjago City or Golden Weapon structures.
                These can be used to add more variants of these structures.
                """);
    }

    @Override
    protected String entryName() {
        return "Tags";
    }

    @Override
    protected String entryDescription() {
        return "Minejago adds a few tags that can expand functionality of addons.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.NAME_TAG);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
