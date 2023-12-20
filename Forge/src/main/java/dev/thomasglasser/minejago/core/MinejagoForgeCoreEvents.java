package dev.thomasglasser.minejago.core;

import com.klikli_dev.modonomicon.client.render.page.BookProcessingRecipePageRenderer;
import com.klikli_dev.modonomicon.client.render.page.PageRendererRegistry;
import com.klikli_dev.modonomicon.data.LoaderRegistry;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.modonomicons.pages.BookTeapotBrewingRecipePage;
import dev.thomasglasser.minejago.network.MinejagoMainChannel;
import dev.thomasglasser.minejago.packs.MinejagoPacks;
import dev.thomasglasser.minejago.packs.PackHolder;
import dev.thomasglasser.minejago.world.focus.modifier.biome.BiomeFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.blockstate.BlockStateFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.dimension.DimensionFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.effect.MobEffectFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.entity.EntityFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.itemstack.ItemStackFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.structure.StructureFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.world.WorldFocusModifiers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.resource.PathPackResources;

public class MinejagoForgeCoreEvents {
    public static void onCommonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            MinejagoMainChannel.register();

            LoaderRegistry.registerPageLoader(BookTeapotBrewingRecipePage.ID, BookTeapotBrewingRecipePage::fromJson, BookTeapotBrewingRecipePage::fromNetwork);
            PageRendererRegistry.registerPageRenderer(BookTeapotBrewingRecipePage.ID,  page -> new BookProcessingRecipePageRenderer<>((BookTeapotBrewingRecipePage) page) {});
        });
    }

    public static void onAddPackFinders(AddPackFindersEvent event)
    {
        for (PackHolder holder : MinejagoPacks.getPacks())
        {
            if (event.getPackType() == holder.type())
            {
                var resourcePath = ModList.get().getModFileById(Minejago.MOD_ID).getFile().findResource("resourcepacks/" + holder.id().getPath());
                var pack = Pack.readMetaAndCreate("builtin/" + holder.id().getPath(), Component.translatable(holder.titleKey()), holder.required(),
                        (path) -> new PathPackResources(path, true, resourcePath), holder.type(), Pack.Position.BOTTOM, PackSource.FEATURE);
                event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));
            }
        }
    }

    public static void onAddReloadListeners(AddReloadListenerEvent event)
    {
        event.addListener((ResourceManagerReloadListener) resourceManager ->
        {
            BiomeFocusModifiers.load(resourceManager);
            BlockStateFocusModifiers.load(resourceManager);
            DimensionFocusModifiers.load(resourceManager);
            EntityFocusModifiers.load(resourceManager);
            ItemStackFocusModifiers.load(resourceManager);
            StructureFocusModifiers.load(resourceManager);
            MobEffectFocusModifiers.load(resourceManager);
            WorldFocusModifiers.load(resourceManager);
        });
    }
}
