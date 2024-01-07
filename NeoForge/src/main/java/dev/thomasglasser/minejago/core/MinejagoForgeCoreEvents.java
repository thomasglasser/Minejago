package dev.thomasglasser.minejago.core;

import com.klikli_dev.modonomicon.client.render.page.BookProcessingRecipePageRenderer;
import com.klikli_dev.modonomicon.client.render.page.PageRendererRegistry;
import com.klikli_dev.modonomicon.data.LoaderRegistry;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.data.modonomicons.pages.BookTeapotBrewingRecipePage;
import dev.thomasglasser.minejago.network.CustomPacket;
import dev.thomasglasser.minejago.network.MinejagoPackets;
import dev.thomasglasser.minejago.packs.MinejagoPacks;
import dev.thomasglasser.minejago.packs.PackHolder;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.focus.modifier.biome.BiomeFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.blockstate.BlockStateFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.dimension.DimensionFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.effect.MobEffectFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.entity.EntityFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.itemstack.ItemStackFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.structure.StructureFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.world.WorldFocusModifiers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

public class MinejagoForgeCoreEvents {
    public static void onCommonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->
        {
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
                var pack = Pack.readMetaAndCreate("builtin/" + holder.id().getPath(), Component.translatable(holder.titleKey()), holder.required(), new Pack.ResourcesSupplier() {
                    @Override
                    public PackResources openPrimary(String s)
                    {
                        return new PathPackResources(s, resourcePath, true);
                    }

                    @Override
                    public PackResources openFull(String s, Pack.Info info)
                    {
                        return new PathPackResources(s, resourcePath, true);
                    }
                }, holder.type(), Pack.Position.BOTTOM, PackSource.FEATURE);
                event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));
            }
        }
    }

    public static void onNewDataPackRegistry(DataPackRegistryEvent.NewRegistry event)
    {
        event.dataPackRegistry(MinejagoRegistries.POWER, Power.CODEC, Power.CODEC);
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

    public static void onRegisterPackets(RegisterPayloadHandlerEvent event)
    {
        IPayloadRegistrar registrar = event.registrar(Minejago.MOD_ID);
        MinejagoPackets.PACKETS.forEach((packet, pair) -> registrar.play(pair.getFirst(), (FriendlyByteBuf buf) ->
        {
            try
            {
                return packet.getConstructor(FriendlyByteBuf.class).newInstance(buf);
            }
            catch (NoSuchMethodException e)
            {
	            try
	            {
		            return packet.getConstructor().newInstance();
	            }
                catch (NoSuchMethodException nsm)
	            {
                    throw new RuntimeException("Custom packets must have FriendlyByteBuf or empty constructor!");
	            }
                catch (Exception ex)
                {
                    throw new RuntimeException(e);
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }, handler -> handler
            .client((payload, context) ->
            {
                if (payload.direction() == CustomPacket.Direction.SERVER_TO_CLIENT) payload.handle((ServerPlayer) context.player().orElse(null));
                else throw new RuntimeException("Serverbound packet sent to client!");
            })
            .server((payload, context) ->
            {
                if (payload.direction() == CustomPacket.Direction.CLIENT_TO_SERVER) payload.handle((ServerPlayer) context.player().orElse(null));
                else throw new RuntimeException("Clientbound packet sent to server!");
            })));
    }
}
