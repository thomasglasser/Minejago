package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.powers;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import net.minecraft.world.item.ItemStack;

public class GiEntry extends IndexModeEntryProvider {
    private static final String ID = "gi";

    public GiEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("abilities", () -> BookTextPageModel.create()
                .withAnchor("abilities")
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Abilities");
        add(context().pageText(), """
                Gi are made for stealth,
                so wearing them will make you undetectable around vibration detectors.
                You will also be able to walk on powdered snow.
                """);

        page("black", () -> BookImagePageModel.create()
                .withAnchor("black")
                .withImages(WikiBookSubProvider.wikiTexture("powers/gi/black.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Black Gi");
        add(context().pageText(), """
                The Black Gi is the most basic Gi.
                It is not associated with any power and is given by [Wu](entry://characters/ninja_team@wu) when training begins.
                """);

        page("training", () -> BookImagePageModel.create()
                .withAnchor("training")
                .withImages(WikiBookSubProvider.wikiTexture("powers/gi/training.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Training Gi");
        add(context().pageText(), """
                The Training Gi is the first Gi you will receive after discovering your elemental power.
                It is functionally no different from the Black Gi.
                """);

        page("creative_mode_tab", () -> BookImagePageModel.create()
                .withAnchor("creative_mode_tab")
                .withImages(WikiBookSubProvider.wikiTexture("powers/gi/creative_mode_tab.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Creative Mode Tab");
        add(context().pageText(), "All mod and datapack Gi can be found in the Gi tab");
    }

    @Override
    protected String entryName() {
        return "Gi";
    }

    @Override
    protected String entryDescription() {
        return "Garments worn by ninja to increase their stealth and defense.";
    }

    @Override
    protected BookIconModel entryIcon() {
        ItemStack fireHead = MinejagoArmors.TRAINEE_GI_SET.HEAD.get().getDefaultInstance();
        fireHead.set(MinejagoDataComponents.POWER.get(), MinejagoPowers.FIRE);
        return BookIconModel.create(fireHead);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
