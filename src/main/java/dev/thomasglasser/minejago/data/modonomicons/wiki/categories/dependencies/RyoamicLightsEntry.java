package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.dependencies;

import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.spinjitzu.AbilitiesEntry;
import net.minecraft.world.item.Items;

public class RyoamicLightsEntry extends IndexModeEntryProvider {
    private static final String ID = "ryoamiclights";

    public RyoamicLightsEntry(DependenciesCategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("spinjitzu", () -> BookImagePageModel.create()
                .withAnchor("spinjitzu")
                .withImages(AbilitiesEntry.ENVIRONMENTAL_LOCATION)
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Spinjitzu");
        add(context().pageText(), "[Spinjitzu](category://spinjitzu) emits light when [RyoamicLights](https://modrinth.com/mod/ryoamiclights) is installed.");

        page("golden_weapons", () -> BookImagePageModel.create()
                .withAnchor("golden_weapons")
                .withImages(WikiBookSubProvider.wikiTexture("dependencies/ryoamiclights/golden_weapons.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Golden Weapons");
        add(context().pageText(), "[Golden Weapons](entry://powers/golden_weapons) emit light when [RyoamicLights](https://modrinth.com/mod/ryoamiclights) is installed.");
    }

    @Override
    protected String entryName() {
        return "RyoamicLights";
    }

    @Override
    protected String entryDescription() {
        return "RyoamicLights is a dynamic lights mod.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.TORCH);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
