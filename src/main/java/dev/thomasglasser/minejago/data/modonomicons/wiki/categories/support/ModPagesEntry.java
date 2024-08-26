package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.support;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import net.minecraft.resources.ResourceLocation;

public class ModPagesEntry extends IndexModeEntryProvider {
    private static final String ID = "mod_pages";
    public static final ResourceLocation MODRINTH_TEXTURE = WikiBookSubProvider.wikiTexture("support/mod_pages/modrinth.png");
    public static final ResourceLocation GITHUB_LOCATION = WikiBookSubProvider.wikiTexture("support/mod_pages/github.png");

    public ModPagesEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("modrinth", () -> BookImagePageModel.create()
                .withAnchor("modrinth")
                .withImages(MODRINTH_TEXTURE)
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Modrinth");
        add(context().pageText(), "The main place to find and download the mod is on [Modrinth](https://modrinth.com/mod/minejago).");

        page("github", () -> BookImagePageModel.create()
                .withAnchor("github")
                .withImages(GITHUB_LOCATION)
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "GitHub");
        add(context().pageText(), """
                The source code for the mod can be found on [GitHub](https://github.com/thomasglasser/Minejago).
                This is also the place to report issues and suggest features.
                """);

        page("curseforge", () -> BookImagePageModel.create()
                .withAnchor("curseforge")
                .withImages(WikiBookSubProvider.wikiTexture("support/mod_pages/curseforge.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "CurseForge");
        add(context().pageText(), """
                Though not recommended,
                the mod can also be found on [CurseForge](https://www.curseforge.com/minecraft/mc-mods/minejago).
                """);
    }

    @Override
    protected String entryName() {
        return "Mod Pages";
    }

    @Override
    protected String entryDescription() {
        return "The locations the mod can be found and downloaded.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(MODRINTH_TEXTURE);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
