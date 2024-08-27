package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.powers;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.resources.ResourceLocation;

public class GoldenWeaponsEntry extends IndexModeEntryProvider {
    private static final String ID = "golden_weapons";
    public static final ResourceLocation SCYTHE_LOCATION = WikiBookSubProvider.wikiTexture("powers/golden_weapons/scythe_of_quakes.png");

    public GoldenWeaponsEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("use", () -> BookTextPageModel.create()
                .withAnchor("use")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "How to Use");
        add(context().pageText(), """
                Each Golden Weapon has unique abilities that can be used by interacting with the air, the ground, or other entities.
                Depending on [config values](entry://powers/golden_weapons@configuration),
                you must have a matching power to use the weapon.
                If you do not, it may malfunction and cause chaos.
                """);

        page("scythe_of_quakes", () -> BookImagePageModel.create()
                .withAnchor("scythe_of_quakes")
                .withImages(SCYTHE_LOCATION)
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Scythe of Quakes");
        add(context().pageText(), """
                The Scythe of Quakes is a powerful weapon of the Earth element found in the [Cave of Despair](entry://locations/cave_of_despair).
                It can be used to manipulate the ground.
                When it malfunctions, the Scythe of Quakes will cause a massive explosion and kill you.
                """);

        page("scythe_of_quakes_abilities", () -> BookTextPageModel.create()
                .withAnchor("scythe_of_quakes_abilities")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Scythe of Quakes Abilities");
        add(context().pageText(), """
                It currently has three unique abilities:
                - By right-clicking solid ground,
                the scythe will send a beam of rocks to make a path into the Earth.
                You are unable to move during this time.
                - By crouching and right-clicking solid ground,
                the scythe will send a circle of explosions that will destroy the ground around you.
                This will damage nearby entities, but may also damage you.
                - By right-clicking ground with air below it,
                the scythe will pull nearby blocks down to the ground and make them fall.
                This can be useful for mining, creating traps, or making a quick escape.
                """);

        page("configuration", () -> BookTextPageModel.create()
                .withAnchor("configuration")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Configuration");
        add(context().pageText(), """
                There are currently two config values that affect the Golden Weapons:
                - "require_compatible_power" (default: true): Whether users must have a matching power to use the weapon successfully.
                - "enable_malfunction" (default:true): Whether a malfunction occurs if someone with an incompatible power uses a weapon.
                """);
    }

    @Override
    protected String entryName() {
        return "Golden Weapons";
    }

    @Override
    protected String entryDescription() {
        return "Weapons created by the First Spinjitzu Master to harness the power of the elements";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(MinejagoItems.SCYTHE_OF_QUAKES);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
