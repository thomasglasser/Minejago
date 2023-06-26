package dev.thomasglasser.minejago.core;

import com.klikli_dev.modonomicon.client.render.page.BookProcessingRecipePageRenderer;
import com.klikli_dev.modonomicon.client.render.page.PageRendererRegistry;
import com.klikli_dev.modonomicon.data.LoaderRegistry;
import dev.thomasglasser.minejago.data.modonomicons.pages.BookTeapotBrewingRecipePage;
import dev.thomasglasser.minejago.network.MinejagoMainChannel;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotionBrewing;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class MinejagoForgeCoreEvents {
    public static void onCommonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            MinejagoMainChannel.register();

            MinejagoPotionBrewing.addMixes();

            LoaderRegistry.registerPageLoader(BookTeapotBrewingRecipePage.ID, BookTeapotBrewingRecipePage::fromJson, BookTeapotBrewingRecipePage::fromNetwork);
            PageRendererRegistry.registerPageRenderer(BookTeapotBrewingRecipePage.ID,  page -> new BookProcessingRecipePageRenderer<>((BookTeapotBrewingRecipePage) page) {});
        });
    }
}
