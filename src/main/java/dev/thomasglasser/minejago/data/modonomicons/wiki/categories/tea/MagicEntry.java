package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.tea;

import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.world.item.MinejagoItemUtils;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;

public class MagicEntry extends IndexModeEntryProvider {
    private static final String ID = "magic";

    public MagicEntry(TeaCategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("minejago", () -> BookTextPageModel.create()
                .withAnchor("minejago")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Mod Teas");
        add(context().pageText(), """
                Focus Leaves can be brewed in water to make [Focus Tea](entry://focus/tea).
                This tea gives you the [Hyperfocus effect](entry://focus/tea@hyperfocus).
                """);

        page("milk", () -> BookTextPageModel.create()
                .withAnchor("milk")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Boiled Milk");
        add(context().pageText(), """
                Milk can be boiled in a [teapot](entry://tea/teapot) to make Boiled Milk.
                This drink gives you the Instant Cure effect that weakens all potion effects.
                """);

        page("vanilla", () -> BookTextPageModel.create()
                .withAnchor("vanilla")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Vanilla Teas");
        add(context().pageText(), """
                If the [Potion Pot Pack](entry://expansions/potion_pot) is enabled,
                you can brew vanilla teas in a [teapot](entry://tea/teapot) using vanilla ingredients.
                Potions brewed in this way are less effective than potions,
                but they hold six cups of tea and can be drank by multiple players.
                """);
    }

    @Override
    protected String entryName() {
        return "Magic Tea";
    }

    @Override
    protected String entryDescription() {
        return "Teas that provide magical effects, made with magic leaves or other materials.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(MinejagoItemUtils.fillTeacup(MinejagoPotions.FOCUS_TEA.asReferenceFrom(registries())));
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
