package dev.thomasglasser.minejago.data.modonomicons.wiki;

import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.CompatCategoryProvider;
import dev.thomasglasser.minejago.data.modonomicons.wiki.categories.ExpansionsCategoryProvider;
import dev.thomasglasser.minejago.world.item.MinejagoCreativeModeTabs;
import net.minecraft.data.PackOutput;

public class MinejagoWikiBookProvider extends BookProvider {
    public static final String ID = "wiki";

    /**
     * @param packOutput
     * @param defaultLang  The LanguageProvider to fill with this book provider. IMPORTANT: the Languag Provider needs to be added to the DataGenerator AFTER the BookProvider.
     */
    public MinejagoWikiBookProvider(PackOutput packOutput, ModonomiconLanguageProvider defaultLang) {
        super(ID, packOutput, Minejago.MOD_ID, defaultLang);
    }

    @Override
    protected BookModel generateBook() {
        lang.add(context.bookName(), "Minejago Wiki");
        lang.add(context.bookTooltip(), "Your guide to the Realms of Minejago!");

        return BookModel.create(modLoc(context.book()), context.bookName())
                .withTooltip(context.bookTooltip())
                .withGenerateBookItem(true)
                .withModel(Minejago.Dependencies.MODONOMICON.modLoc("modonomicon_red"))
                .withCreativeTab(MinejagoCreativeModeTabs.MINEJAGO.getId())
                .withCategories(
                        new CompatCategoryProvider(this).generate(),
                        new ExpansionsCategoryProvider(this).generate()
                );
    }

    @Override
    protected void registerDefaultMacros() {
        // None right now
    }
}
