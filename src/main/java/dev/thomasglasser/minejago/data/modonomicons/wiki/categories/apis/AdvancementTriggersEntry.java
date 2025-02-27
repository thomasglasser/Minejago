package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.apis;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import net.minecraft.world.item.alchemy.PotionContents;

public class AdvancementTriggersEntry extends IndexModeEntryProvider {
    private static final String ID = "advancement_triggers";

    public AdvancementTriggersEntry(CategoryProviderBase parent) {
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
                A generator for these triggers is available online [here](https://snapshot-jsons.thomasglasser.dev/advancement/).
                """);

        page("did_spinjitzu", () -> BookTextPageModel.create()
                .withAnchor("did_spinjitzu")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Did Spinjitzu");
        add(context().pageText(), """
                This trigger is called when a player activates spinjitzu. It has no parameters.
                """);

        page("got_power", () -> BookTextPageModel.create()
                .withAnchor("got_power")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Got Power");
        add(context().pageText(), """
                This trigger is called when a player gets a power. It has one parameter:
                - "power": The power ID.
                """);

        page("brewed_tea", () -> BookTextPageModel.create()
                .withAnchor("brewed_tea")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Brewed Tea");
        add(context().pageText(), """
                This trigger is called when a player brews tea. It has one parameter:
                - "potion": The potion ID.
                """);

        page("skulkin_raid_status_changed", () -> BookTextPageModel.create()
                .withAnchor("skulkin_raid_status_changed")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Skulkin Raid Status Changed");
        add(context().pageText(), """
                This trigger is called when the status of the Skulkin raid changes. It has one parameter:
                - "status": The status of the raid. This can be "started" or "won".
                """);
    }

    @Override
    protected String entryName() {
        return "Advancement Triggers";
    }

    @Override
    protected String entryDescription() {
        return "Minejago adds a few advancement triggers for mods and datapacks to detect and use.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(PotionContents.createItemStack(MinejagoItems.FILLED_TEACUP.get(), MinejagoPotions.ACACIA_TEA.asReferenceFrom(registries())));
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
