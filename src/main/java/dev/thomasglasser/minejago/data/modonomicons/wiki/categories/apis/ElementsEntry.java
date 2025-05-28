package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.apis;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.world.entity.element.Elements;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import net.minecraft.world.item.ItemStack;

public class ElementsEntry extends IndexModeEntryProvider {
    private static final String ID = "elements";

    public ElementsEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("generator", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Generator");
        add(context().pageText(), """
                A generator for these elements is available online [here](https://beta-jsons.thomasglasser.dev/minejago/element/).
                """);

        page("guide", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Guide");
        add(context().pageText(), """
                A guide for creating elements can be found [here](https://beta-jsons.thomasglasser.dev/guides/element/).
                """);
    }

    @Override
    protected String entryName() {
        return "Elements";
    }

    @Override
    protected String entryDescription() {
        return """
                Elements are data-driven, requiring a datapack and resource pack with a few simple files.
                A generator for these elements is available [here](https://jsons.thomasglasser.dev/minejago/element/).
                """;
    }

    @Override
    protected BookIconModel entryIcon() {
        ItemStack icon = MinejagoArmors.TRAINEE_GI_SET.CHEST.toStack();
        icon.set(MinejagoDataComponents.ELEMENT, Elements.FIRE);
        return BookIconModel.create(icon);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
