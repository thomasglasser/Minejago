package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.focus;

import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import dev.thomasglasser.minejago.world.item.MinejagoItemUtils;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;

public class TeaEntry extends IndexModeEntryProvider {
    private static final String ID = "tea";

    public TeaEntry(FocusCategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("hyperfocus", () -> BookImagePageModel.create()
                .withAnchor("hyperfocus")
                .withImages(modLoc("textures/mob_effect/hyperfocus.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Hyperfocus");
        add(context().pageText(), """
                Hyperfocus is a new potion effect that increases the player's focus and concentration.
                The value depends on the amplifier of the potion effect.
                It is instantaneously applied when the player drinks Focus Tea.
                """);

        page("tree", () -> BookImagePageModel.create()
                .withAnchor("tree")
                .withImages(WikiBookSubProvider.wikiTexture("focus/tea/tree.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Focus Tree");
        add(context().pageText(), """
                Focus Trees can be found in meadows.
                They are a source of Enchanted Wood and Focus Leaves.
                Focus Leaves can be harvested with Shears and brewed in a [teapot](entry://tea/teapot) to make Focus Tea.
                """);

        page("advancements", () -> BookTextPageModel.create()
                .withAnchor("advancements")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Advancement Integration");
        add(context().pageText(), """
                If [Patched](entry://dependencies/patched) is installed,
                Focus Tea has been integrated into the vanilla advancements "A Furious Cocktail" and "How Did We Get Here?".
                The Hyperfocus effect is now a requirement for both of these advancements.
                """);
    }

    @Override
    protected String entryName() {
        return "Focus Tea";
    }

    @Override
    protected String entryDescription() {
        return "Focus Tea is a drink that helps you focus and concentrate.";
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
