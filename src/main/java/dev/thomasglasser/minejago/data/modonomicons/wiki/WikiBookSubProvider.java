package dev.thomasglasser.minejago.data.modonomicons.wiki;

import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import com.klikli_dev.modonomicon.book.BookDisplayMode;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.apis.ApisCategoryProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.characters.CharactersCategoryProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.cosmetics.CosmeticsCategoryProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.decorations.DecorationsCategoryProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.dependencies.DependenciesCategoryProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.elements.ElementsCategoryProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.expansions.ExpansionsCategoryProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.focus.FocusCategoryProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.locations.LocationsCategoryProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.skills.SkillsCategoryProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.skulkin.SkulkinCategoryProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.spinjitzu.SpinjitzuCategoryProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.support.SupportCategoryProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.tea.TeaCategoryProvider;
import dev.thomasglasser.minejago.world.item.MinejagoCreativeModeTabs;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;

public class WikiBookSubProvider extends SingleBookSubProvider {
    public static final String ID = "wiki";

    public WikiBookSubProvider(BiConsumer<String, String> lang) {
        super(ID, Minejago.MOD_ID, lang);
    }

    protected void registerDefaultMacros() {}

    @Override
    protected void generateCategories() {
        add(new ElementsCategoryProvider(this).generate());
        add(new SpinjitzuCategoryProvider(this).generate());
        add(new SkillsCategoryProvider(this).generate());
        add(new FocusCategoryProvider(this).generate());
        add(new TeaCategoryProvider(this).generate());
        add(new LocationsCategoryProvider(this).generate());
        add(new CharactersCategoryProvider(this).generate());
        add(new SkulkinCategoryProvider(this).generate());
        add(new DecorationsCategoryProvider(this).generate());
        add(new CosmeticsCategoryProvider(this).generate());
        add(new ExpansionsCategoryProvider(this).generate());
        add(new DependenciesCategoryProvider(this).generate());
        add(new ApisCategoryProvider(this).generate());
        add(new SupportCategoryProvider(this).generate());
    }

    @Override
    protected BookModel additionalSetup(BookModel book) {
        book = super.additionalSetup(book);
        return book
                .withDisplayMode(BookDisplayMode.INDEX)
                .withModel(Minejago.Dependencies.MODONOMICON.modLoc("modonomicon_red"))
                .withCreativeTab(MinejagoCreativeModeTabs.MINEJAGO.getId())
                .withGenerateBookItem(true);
    }

    @Override
    protected String bookName() {
        return "Minejago Wiki";
    }

    @Override
    protected String bookTooltip() {
        return "Your guide to the world of Minejago";
    }

    @Override
    protected String bookDescription() {
        return """
                Thank you for installing Minejago! Within these pages is everything you'll ever need to know about the mod, from how to tame a dragon to how to fight the Overlord. Check back here when new updates come out to see the changes!
                """;
    }

    public static ResourceLocation wikiTexture(String path) {
        return Minejago.modLoc("textures/modonomicon/wiki/" + path);
    }
}
