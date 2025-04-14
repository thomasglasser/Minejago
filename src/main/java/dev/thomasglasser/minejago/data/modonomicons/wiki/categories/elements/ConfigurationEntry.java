package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.elements;

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
                There are four config values for elements:
                - "allow_choose" (default: false): Whether players can choose their element.
                - "allow_change" (default: false): Whether players can change their element.
                - "drain_pool" (default: true): Whether Wu can only give a element once before all elements are given.
                - "enable_no_element" (default: true): Whether players can be given no element.
                """);

        page("command", () -> BookTextPageModel.create()
                .withAnchor("command")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Element Command");
        add(context().pageText(), """
                The "/element" command is used to query, give, and clear elements.
                It has 3 functions:
                - "/element <entity>": Query the element of an entity.
                - "/element <element> <entity>": Give a element to an entity.
                - "/element clear <entity>": Clear the element of an entity.
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
