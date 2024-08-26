package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.powers;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import net.minecraft.world.item.Items;

public class ConfigurationEntry extends IndexModeEntryProvider {
    private static final String ID = "configuration";

    public ConfigurationEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("values", () -> BookTextPageModel.create()
                .withAnchor("values")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Config Values");
        add(context().pageText(), """
                There are four config values for powers:
                - "allow_choose" (default: false): Whether players can choose their power.
                - "allow_change" (default: false): Whether players can change their power.
                - "drain_pool" (default: true): Whether Wu can only give a power once before all powers are given.
                - "enable_no_power" (default: true): Whether players can be given no power.
                """);

        page("command", () -> BookTextPageModel.create()
                .withAnchor("command")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Power Command");
        add(context().pageText(), """
                The "/power" command is used to query, give, and clear powers.
                It has 3 functions:
                - "/power <entity>": Query the power of an entity.
                - "/power <power> <entity>": Give a power to an entity.
                - "/power clear <entity>": Clear the power of an entity.
                The entity argument can be omitted to apply the command to the sender.
                """);
    }

    @Override
    protected String entryName() {
        return "Configuration";
    }

    @Override
    protected String entryDescription() {
        return "Values changeable via the config screen that alter gameplay.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.COMMAND_BLOCK);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
