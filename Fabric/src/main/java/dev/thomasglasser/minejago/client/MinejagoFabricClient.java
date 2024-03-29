package dev.thomasglasser.minejago.client;

import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.model.BambooStaffModel;
import dev.thomasglasser.minejago.client.model.DragonModel;
import dev.thomasglasser.minejago.client.model.KrunchaModel;
import dev.thomasglasser.minejago.client.model.NuckalModel;
import dev.thomasglasser.minejago.client.model.OgDevTeamBeardModel;
import dev.thomasglasser.minejago.client.model.PilotsSnapshotTesterHatModel;
import dev.thomasglasser.minejago.client.model.ScytheModel;
import dev.thomasglasser.minejago.client.model.SpearModel;
import dev.thomasglasser.minejago.client.model.ThrownBoneKnifeModel;
import dev.thomasglasser.minejago.client.model.ThrownIronShurikenModel;
import dev.thomasglasser.minejago.client.renderer.block.DragonHeadRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.CharacterRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.DragonRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.EarthBlastRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.KrunchaRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.NuckalRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.SamukaiRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.SkulkinHorseRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.SkulkinRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.SkullMotorbikeRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.SkullTruckRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.ThrownBambooStaffRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.ThrownBoneKnifeRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.ThrownIronShurikenRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.ThrownIronSpearRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.WuRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.layers.OgDevTeamLayer;
import dev.thomasglasser.minejago.client.renderer.entity.layers.SnapshotTesterLayer;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBlockEntityTypes;
import dev.thomasglasser.minejago.world.level.block.entity.TeapotBlockEntity;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.client.animation.AnimationUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.PreparableModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.BrushableBlockRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class MinejagoFabricClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient() {
        registerRenderers();
        registerModelLayers();
        registerModelProviders();

        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return Minejago.modLoc("bewlr");
            }

            @Override
            public CompletableFuture<Void> reload(PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
                return Minejago.getBewlr().reload(preparationBarrier, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor);
            }
        });

        registerItemAndBlockColors();

        BlockRenderLayerMap.INSTANCE.putBlock(MinejagoBlocks.TEAPOT.get(), RenderType.cutout());
        MinejagoBlocks.TEAPOTS.forEach((dyeColor, teapot) -> BlockRenderLayerMap.INSTANCE.putBlock(teapot.get(), RenderType.cutout()));
        BlockRenderLayerMap.INSTANCE.putBlock(MinejagoBlocks.JASPOT.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(MinejagoBlocks.FLAME_TEAPOT.get(), RenderType.cutout());

        BlockRenderLayerMap.INSTANCE.putBlock(MinejagoBlocks.TOP_POST.get(), RenderType.cutout());

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(), MinejagoBlocks.FOCUS_LEAVES_SET.sapling().get(), MinejagoBlocks.FOCUS_LEAVES_SET.pottedSapling().get());

        registerEvents();

        MinejagoClientEvents.registerMenuScreens();
    }

    private void registerRenderers()
    {
        EntityRendererRegistry.register(MinejagoEntityTypes.THROWN_BONE_KNIFE.get(), ThrownBoneKnifeRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.THROWN_BAMBOO_STAFF.get(), ThrownBambooStaffRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.THROWN_IRON_SPEAR.get(), ThrownIronSpearRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.THROWN_IRON_SHURIKEN.get(), ThrownIronShurikenRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.EARTH_BLAST.get(), EarthBlastRenderer::new);

        EntityRendererRegistry.register(MinejagoEntityTypes.WU.get(), WuRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.KAI.get(), CharacterRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.NYA.get(), (context) -> new CharacterRenderer<>(context, true));
        EntityRendererRegistry.register(MinejagoEntityTypes.JAY.get(), CharacterRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.COLE.get(), CharacterRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.ZANE.get(), CharacterRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.SKULKIN.get(), SkulkinRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.KRUNCHA.get(), KrunchaRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.NUCKAL.get(), NuckalRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.SKULKIN_HORSE.get(), SkulkinHorseRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.EARTH_DRAGON.get(), context -> new DragonRenderer<>(context, new DragonModel<>(Minejago.modLoc("dragon/earth_dragon"))));
        EntityRendererRegistry.register(MinejagoEntityTypes.SAMUKAI.get(), SamukaiRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.SKULL_TRUCK.get(), SkullTruckRenderer::new);
        EntityRendererRegistry.register(MinejagoEntityTypes.SKULL_MOTORBIKE.get(), SkullMotorbikeRenderer::new);

        BlockEntityRenderers.register(MinejagoBlockEntityTypes.DRAGON_HEAD.get(), context -> new DragonHeadRenderer());
        BlockEntityRenderers.register(MinejagoBlockEntityTypes.BRUSHABLE.get(), BrushableBlockRenderer::new);
    }

    private void registerModelLayers()
    {
        EntityModelLayerRegistry.registerModelLayer(ThrownBoneKnifeModel.LAYER_LOCATION, ThrownBoneKnifeModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(BambooStaffModel.LAYER_LOCATION, BambooStaffModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SpearModel.LAYER_LOCATION, SpearModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ThrownIronShurikenModel.LAYER_LOCATION, ThrownIronShurikenModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ScytheModel.LAYER_LOCATION, ScytheModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(PilotsSnapshotTesterHatModel.LAYER_LOCATION, PilotsSnapshotTesterHatModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(OgDevTeamBeardModel.LAYER_LOCATION, OgDevTeamBeardModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(KrunchaModel.LAYER_LOCATION, KrunchaModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(NuckalModel.LAYER_LOCATION, NuckalModel::createBodyLayer);
    }

    private void registerModelProviders()
    {
        ModelLoadingPlugin.register(context -> context.addModels(new ModelResourceLocation(Minejago.MOD_ID, "iron_scythe_inventory", "inventory")));
        ModelLoadingPlugin.register(context -> context.addModels(new ModelResourceLocation(Minejago.MOD_ID, "scythe_of_quakes_inventory", "inventory")));
        ModelLoadingPlugin.register(context -> context.addModels(new ModelResourceLocation(Minejago.MOD_ID, "iron_spear_inventory", "inventory")));
        ModelLoadingPlugin.register(context -> context.addModels(new ModelResourceLocation(Minejago.MOD_ID, "wooden_nunchucks_inventory", "inventory")));
        ModelLoadingPlugin.register(context -> context.addModels(new ModelResourceLocation(Minejago.MOD_ID, "bamboo_staff_inventory", "inventory")));
        PreparableModelLoadingPlugin.register(((resourceManager, executor) ->
        {
            Map<ResourceLocation, Resource> map = resourceManager.listResources("models/item/minejago_armor", (location -> location.getPath().endsWith(".json")));
            List<ModelResourceLocation> rls = new ArrayList<>();
            for (ResourceLocation rl : map.keySet())
            {
                ResourceLocation stripped = new ResourceLocation(rl.getNamespace(), rl.getPath().substring("models/item/".length(), rl.getPath().indexOf(".json")));
                rls.add(new ModelResourceLocation(stripped, "inventory"));
            }
            return CompletableFuture.supplyAsync(() -> rls, executor);
        }), (data, pluginContext) ->
                pluginContext.addModels(data));
    }

    private void registerItemAndBlockColors()
    {
        ColorProviderRegistry.ITEM.register((pStack, pTintIndex) -> {
            if (pTintIndex == 0)
                return PotionUtils.getColor(pStack);
            return -1;
        }, MinejagoItems.FILLED_TEACUP.get());
        ColorProviderRegistry.ITEM.register((itemStack, i) ->
        {
            BlockState blockstate = ((BlockItem)itemStack.getItem()).getBlock().defaultBlockState();
            return ClientUtils.getMinecraft().getBlockColors().getColor(blockstate, null, null, i);
        }, MinejagoBlocks.FOCUS_LEAVES_SET.leaves().get());

        ColorProviderRegistry.BLOCK.register(((blockState, blockAndTintGetter, blockPos, i) ->
        {
            if (blockPos == null || blockAndTintGetter == null)
                return -1;
            if (i == 1 && blockAndTintGetter.getBlockEntity(blockPos) instanceof TeapotBlockEntity teapotBlockEntity && blockState.getValue(TeapotBlock.FILLED))
            {
                return PotionUtils.getColor(PotionUtils.setPotion(new ItemStack(Items.POTION), teapotBlockEntity.getPotion()));
            }
            return -1;
        }), MinejagoBlocks.allPots().toArray(new Block[0]));
        ColorProviderRegistry.BLOCK.register(((blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageFoliageColor(blockAndTintGetter, blockPos) : FoliageColor.getDefaultColor()), MinejagoBlocks.FOCUS_LEAVES_SET.leaves().get());
    }

    private void registerEvents()
    {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) ->
        {
            EntityModelSet models = context.getModelSet();

            if (entityType == EntityType.PLAYER)
            {
                LivingEntityRenderer<Player, PlayerModel<Player>> player = (LivingEntityRenderer<Player, PlayerModel<Player>>) entityRenderer;

                if (player != null)
                {
                    player.addLayer(new SnapshotTesterLayer<>(player, models));
                    player.addLayer(new OgDevTeamLayer<>(player, models));
                }
            }
        });
        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled()) PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register(AnimationUtils::registerPlayerAnimation);
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, entries) ->
                entries.acceptAll(MinejagoClientEvents.getItemsForTab(BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(group).orElseThrow())));
        ClientTickEvents.END_CLIENT_TICK.register(client ->
                MinejagoClientEvents.onClientTick());
    }
}