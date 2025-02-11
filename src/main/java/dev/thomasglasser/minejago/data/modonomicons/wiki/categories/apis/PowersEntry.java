package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.apis;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import net.minecraft.world.item.ItemStack;

public class PowersEntry extends IndexModeEntryProvider {
    private static final String ID = "powers";

    public PowersEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("generator", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Generator");
        add(context().pageText(), """
                A generator for these powers is available online [here](https://snapshot-jsons.thomasglasser.dev/minejago/power/).
                """);

        page("guide", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Guide");
        add(context().pageText(), """
                A guide for creating powers can be found [here](https://snapshot-jsons.thomasglasser.dev/guides/power/).
                """);
    }

    @Override
    protected String entryName() {
        return "Powers";
    }

    @Override
    protected String entryDescription() {
        return """
                Powers are data-driven, requiring a datapack and resource pack with a few simple files.
                A generator for these powers is available [here](https://jsons.thomasglasser.dev/minejago/power/).
                """;
    }

    @Override
    protected BookIconModel entryIcon() {
        ItemStack icon = MinejagoArmors.TRAINEE_GI_SET.CHEST.toStack();
        icon.set(MinejagoDataComponents.POWER, MinejagoPowers.FIRE);
        return BookIconModel.create(icon);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
