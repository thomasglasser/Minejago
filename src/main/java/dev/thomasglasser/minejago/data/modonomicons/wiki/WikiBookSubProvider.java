package dev.thomasglasser.minejago.data.modonomicons.wiki;

import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import com.klikli_dev.modonomicon.book.BookDisplayMode;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.compat.CompatCategoryProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.expansions.ExpansionsCategoryProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.support.SupportCategoryProvider;
import dev.thomasglasser.minejago.world.item.MinejagoCreativeModeTabs;
import java.util.function.BiConsumer;

public class WikiBookSubProvider extends SingleBookSubProvider {
    public static final String ID = "wiki";

    public WikiBookSubProvider(BiConsumer<String, String> lang) {
        super(ID, Minejago.MOD_ID, lang);
    }

    protected void registerDefaultMacros() {}

    @Override
    protected void generateCategories() {
        add(new CompatCategoryProvider(this).generate());
        add(new ExpansionsCategoryProvider(this).generate());
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
}
