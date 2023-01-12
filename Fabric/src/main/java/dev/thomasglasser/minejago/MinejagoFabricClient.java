package dev.thomasglasser.minejago;

import dev.thomasglasser.minejago.client.MinejagoKeyMappings;
import dev.thomasglasser.minejago.client.model.*;
import dev.thomasglasser.minejago.client.renderer.MinejagoBlockEntityWithoutLevelRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.*;
import dev.thomasglasser.minejago.client.renderer.entity.layers.BetaTesterLayer;
import dev.thomasglasser.minejago.client.renderer.entity.layers.DevLayer;
import dev.thomasglasser.minejago.commands.MinejagoCommandEvents;
import dev.thomasglasser.minejago.network.*;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.IModeledItem;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class MinejagoFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerRenderers();
        registerModelLayers();
        registerModelProviders();

        MinejagoBlockEntityWithoutLevelRenderer bewlr = new MinejagoBlockEntityWithoutLevelRenderer();

        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return Minejago.modLoc("bewlr");
            }

            @Override
            public CompletableFuture<Void> reload(PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
                return bewlr.reload(preparationBarrier, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor);
            }
        });

        for (RegistryObject<Item> item : MinejagoItems.ITEMS.getEntries())
        {
            if (item.get() instanceof IModeledItem)
            {
                BuiltinItemRendererRegistry.INSTANCE.register(item.get(), (bewlr::renderByItem));
            }
        }

        registerItemColors();

        BlockRenderLayerMap.INSTANCE.putBlock(MinejagoBlocks.TEAPOT.get(), RenderType.translucent());

        registerEvents();

        registerPackets();
    }

    private void registerRenderers()
    {
        EntityRendererRegistry.register(MinejagoEntityTypes.THROWN_BONE_KNIFE.get(), ThrownBoneKnifeRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.THROWN_BAMBOO_STAFF.get(), ThrownBambooStaffRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.THROWN_IRON_SPEAR.get(), ThrownIronSpearRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.THROWN_IRON_SHURIKEN.get(), ThrownIronShurikenRenderer::new);

        EntityRendererRegistry.register(MinejagoEntityTypes.WU.get(), CharacterRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.KAI.get(), CharacterRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.NYA.get(), CharacterRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.JAY.get(), CharacterRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.COLE.get(), CharacterRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.ZANE.get(), CharacterRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.UNDERWORLD_SKELETON.get(), UnderworldSkeletonRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.KRUNCHA.get(), KrunchaRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.NUCKAL.get(), NuckalRenderer::new);
    }

    private void registerModelLayers()
    {
        EntityModelLayerRegistry.registerModelLayer(ThrownBoneKnifeModel.LAYER_LOCATION, ThrownBoneKnifeModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(BambooStaffModel.LAYER_LOCATION, BambooStaffModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SpearModel.LAYER_LOCATION, SpearModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ThrownIronShurikenModel.LAYER_LOCATION, ThrownIronShurikenModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ScytheModel.LAYER_LOCATION, ScytheModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(BambooHatModel.LAYER_LOCATION, BambooHatModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(BeardModel.LAYER_LOCATION, BeardModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(KrunchaModel.LAYER_LOCATION, KrunchaModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(NuckalModel.LAYER_LOCATION, NuckalModel::createBodyLayer);
    }

    private void registerModelProviders()
    {
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, consumer) -> consumer.accept(new ModelResourceLocation(Minejago.MOD_ID, "iron_scythe_inventory", "inventory")));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, consumer) -> consumer.accept(new ModelResourceLocation(Minejago.MOD_ID, "scythe_of_quakes_inventory", "inventory")));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, consumer) -> consumer.accept(new ModelResourceLocation(Minejago.MOD_ID, "iron_spear_inventory", "inventory")));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, consumer) -> consumer.accept(new ModelResourceLocation(Minejago.MOD_ID, "wooden_nunchucks_inventory", "inventory")));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, consumer) -> consumer.accept(new ModelResourceLocation(Minejago.MOD_ID, "bamboo_staff_inventory", "inventory")));
    }

    private void registerItemColors()
    {
        ColorProviderRegistry.ITEM.register((pStack, pTintIndex) -> {
            if (pTintIndex == 0)
                return PotionUtils.getPotion(pStack).getName("").contains("tea") ? 7028992 : PotionUtils.getColor(pStack);
            return -1;
        }, MinejagoItems.FILLED_TEACUP.get());

        ColorProviderRegistry.ITEM.register((pStack, pTintIndex) -> {
            if (pTintIndex == 0)
                return PotionUtils.getPotion(pStack).getName("").contains("tea") ? 7028992 : PotionUtils.getColor(pStack);
            return -1;
        }, Items.POTION);

        ColorProviderRegistry.ITEM.register((pStack, pTintIndex) -> {
            if (pTintIndex == 0)
                return PotionUtils.getPotion(pStack).getName("").contains("tea") ? 7028992 : PotionUtils.getColor(pStack);
            return -1;
        }, Items.SPLASH_POTION);

        ColorProviderRegistry.ITEM.register((pStack, pTintIndex) -> {
            if (pTintIndex == 0)
                return PotionUtils.getPotion(pStack).getName("").contains("tea") ? 7028992 : PotionUtils.getColor(pStack);
            return -1;
        }, Items.LINGERING_POTION);
    }

    private void registerEvents()
    {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) ->
        {
            EntityModelSet models = context.getModelSet();

            if (entityType == MinejagoEntityTypes.WU.get())
            {
                LivingEntityRenderer<Mob, PlayerModel<Mob>> wu = (LivingEntityRenderer<Mob, PlayerModel<Mob>>) entityRenderer;
                wu.addLayer(new BetaTesterLayer<>(wu, models));
                wu.addLayer(new DevLayer<>(wu, models));
            }
            else if (entityType == EntityType.PLAYER)
            {
                LivingEntityRenderer<Player, PlayerModel<Player>> player = (LivingEntityRenderer<Player, PlayerModel<Player>>) entityRenderer;

                if (player != null)
                {
                    player.addLayer(new BetaTesterLayer<>(player, models));
                    player.addLayer(new DevLayer<>(player, models));
                }
            }
        });
    }

    private void registerPackets()
    {
        ClientPlayNetworking.registerGlobalReceiver(ClientboundChangeVipDataPacket.ID, (client, handler, buf, responseSender) ->
                {
                    buf.retain();
                    client.execute(() ->
                    {
                        new ClientboundChangeVipDataPacket(buf).handle();
                        buf.release();
                    });
                });
        ClientPlayNetworking.registerGlobalReceiver(ClientboundRefreshVipDataPacket.ID, (client, handler, buf, responseSender) ->
                {
                    buf.retain();
                    client.execute(() ->
                    {
                        new ClientboundRefreshVipDataPacket().handle();
                        buf.release();
                    });
                });
        ClientPlayNetworking.registerGlobalReceiver(ClientboundStartScytheAnimationPacket.ID, (client, handler, buf, responseSender) ->
                {
                    buf.retain();
                    client.execute(() ->
                    {
                        new ClientboundStartScytheAnimationPacket(buf).handle();
                        buf.release();
                    });
                });
        ClientPlayNetworking.registerGlobalReceiver(ClientboundStartSpinjitzuPacket.ID, (client, handler, buf, responseSender) ->
                {
                    buf.retain();
                    client.execute(() ->
                    {
                        new ClientboundStartSpinjitzuPacket(buf).handle();
                        buf.release();
                    });
                });
        ClientPlayNetworking.registerGlobalReceiver(ClientboundStopAnimationPacket.ID, (client, handler, buf, responseSender) ->
                {
                    buf.retain();
                    client.execute(() ->
                    {
                        new ClientboundStopAnimationPacket(buf).handle();
                        buf.release();
                    });
                });
    }
}
