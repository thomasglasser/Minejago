package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.dependencies;

import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.powers.GoldenWeaponsEntry;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.spinjitzu.SpinjitzuCategoryProvider;

public class PlayerAnimatorEntry extends IndexModeEntryProvider {
    private static final String ID = "playeranimator";

    public PlayerAnimatorEntry(DependenciesCategoryProvider parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("spinjitzu", () -> BookImagePageModel.create()
                .withAnchor("spinjitzu")
                .withImages(WikiBookSubProvider.wikiTexture("dependencies/playeranimator/spinjitzu.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Spinjitzu");
        add(context().pageText(), "There are special animations for [Spinjitzu](category://spinjitzu) that are shown with playerAnimator installed.");

        page("golden_weapons", () -> BookImagePageModel.create()
                .withAnchor("golden_weapons")
                .withImages(GoldenWeaponsEntry.SCYTHE_LOCATION)
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Golden Weapons");
        add(context().pageText(), "There are special animations for using the [Golden Weapons](entry://powers/golden_weapons) with playerAnimator installed.");
    }

    @Override
    protected String entryName() {
        return "playerAnimator";
    }

    @Override
    protected String entryDescription() {
        return "[playerAnimator](https://modrinth.com/mod/playeranimator) is a player animation library.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(SpinjitzuCategoryProvider.SPINJITZU_TEXTURE);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
