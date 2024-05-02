package dev.thomasglasser.minejago.client;

import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.gui.MinejagoGuis;
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
import dev.thomasglasser.minejago.client.particle.BoltsParticle;
import dev.thomasglasser.minejago.client.particle.RocksParticle;
import dev.thomasglasser.minejago.client.particle.SnowsParticle;
import dev.thomasglasser.minejago.client.particle.SparklesParticle;
import dev.thomasglasser.minejago.client.particle.SparksParticle;
import dev.thomasglasser.minejago.client.particle.SpinjitzuParticle;
import dev.thomasglasser.minejago.client.particle.VaporsParticle;
import dev.thomasglasser.minejago.client.renderer.block.DragonHeadRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.CharacterRenderer;
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
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.plugins.MinejagoDynamicLights;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBlockEntityTypes;
import dev.thomasglasser.minejago.world.level.block.entity.TeapotBlockEntity;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.client.animation.AnimationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.blockentity.BrushableBlockRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class MinejagoForgeClientEvents {

    public static void onClientSetup(FMLClientSetupEvent event)
    {
        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled()) PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register(AnimationUtils::registerPlayerAnimation);
        if (Minejago.Dependencies.DYNAMIC_LIGHTS.isInstalled()) MinejagoDynamicLights.register();
    }

    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(ThrownBoneKnifeModel.LAYER_LOCATION, ThrownBoneKnifeModel::createBodyLayer);
        event.registerLayerDefinition(BambooStaffModel.LAYER_LOCATION, BambooStaffModel::createBodyLayer);
        event.registerLayerDefinition(SpearModel.LAYER_LOCATION, SpearModel::createBodyLayer);
        event.registerLayerDefinition(ThrownIronShurikenModel.LAYER_LOCATION, ThrownIronShurikenModel::createBodyLayer);
        event.registerLayerDefinition(ScytheModel.LAYER_LOCATION, ScytheModel::createBodyLayer);
        event.registerLayerDefinition(PilotsSnapshotTesterHatModel.LAYER_LOCATION, PilotsSnapshotTesterHatModel::createBodyLayer);
        event.registerLayerDefinition(OgDevTeamBeardModel.LAYER_LOCATION, OgDevTeamBeardModel::createBodyLayer);
        event.registerLayerDefinition(KrunchaModel.LAYER_LOCATION, KrunchaModel::createBodyLayer);
        event.registerLayerDefinition(NuckalModel.LAYER_LOCATION, NuckalModel::createBodyLayer);
    }

    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(MinejagoEntityTypes.THROWN_BONE_KNIFE.get(), ThrownBoneKnifeRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.THROWN_BAMBOO_STAFF.get(), ThrownBambooStaffRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.THROWN_IRON_SPEAR.get(), ThrownIronSpearRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.THROWN_IRON_SHURIKEN.get(), ThrownIronShurikenRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.EARTH_BLAST.get(), EarthBlastRenderer::new);

        event.registerEntityRenderer(MinejagoEntityTypes.WU.get(), WuRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.KAI.get(), CharacterRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.NYA.get(), (context) -> new CharacterRenderer<>(context, true));
        event.registerEntityRenderer(MinejagoEntityTypes.JAY.get(), CharacterRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.COLE.get(), CharacterRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.ZANE.get(), CharacterRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.SKULKIN.get(), SkulkinRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.KRUNCHA.get(), KrunchaRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.NUCKAL.get(), NuckalRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.SKULKIN_HORSE.get(), SkulkinHorseRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.EARTH_DRAGON.get(), pContext -> new GeoEntityRenderer<>(pContext, new DragonModel<>(MinejagoEntityTypes.EARTH_DRAGON.getId())));
        event.registerEntityRenderer(MinejagoEntityTypes.SAMUKAI.get(), SamukaiRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.SKULL_TRUCK.get(), SkullTruckRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.SKULL_MOTORBIKE.get(), SkullMotorbikeRenderer::new);

        event.registerBlockEntityRenderer(MinejagoBlockEntityTypes.DRAGON_HEAD.get(), pContext -> new DragonHeadRenderer());
        event.registerBlockEntityRenderer(MinejagoBlockEntityTypes.BRUSHABLE.get(), BrushableBlockRenderer::new);
    }

    public static void registerModels(ModelEvent.RegisterAdditional event)
    {
        event.register(Minejago.modLoc("item/iron_scythe_inventory"));
        event.register(Minejago.modLoc("item/scythe_of_quakes_inventory"));
        event.register(Minejago.modLoc("item/iron_spear_inventory"));
        event.register(Minejago.modLoc("item/wooden_nunchucks_inventory"));
        event.register(Minejago.modLoc("item/bamboo_staff_inventory"));

        ResourceManager manager = Minecraft.getInstance().getResourceManager();
        Map<ResourceLocation, Resource> map = manager.listResources("models/item/minejago_armor", (location -> location.getPath().endsWith(".json")));
        for (ResourceLocation rl : map.keySet())
        {
            ResourceLocation stripped = new ResourceLocation(rl.getNamespace(), rl.getPath().substring("models/".length(), rl.getPath().indexOf(".json")));
            event.register(stripped);
        }
    }

    public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event)
    {
        event.registerSpriteSet(MinejagoParticleTypes.SPINJITZU.get(), SpinjitzuParticle.Provider::new);
        event.registerSpriteSet(MinejagoParticleTypes.SPARKS.get(), SparksParticle.Provider::new);
        event.registerSpriteSet(MinejagoParticleTypes.SPARKLES.get(), SparklesParticle.Provider::new);
        event.registerSpriteSet(MinejagoParticleTypes.SNOWS.get(), SnowsParticle.Provider::new);
        event.registerSpriteSet(MinejagoParticleTypes.ROCKS.get(), RocksParticle.Provider::new);
        event.registerSpriteSet(MinejagoParticleTypes.BOLTS.get(), BoltsParticle.Provider::new);
        event.registerSpriteSet(MinejagoParticleTypes.VAPORS.get(), VaporsParticle.Provider::new);
    }

    public static void onRegisterItemColorHandlers(RegisterColorHandlersEvent.Item event)
    {
        event.register((pStack, pTintIndex) -> {
            if (pTintIndex == 0)
                return pStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor();
            return -1;
        }, MinejagoItems.FILLED_TEACUP.get());
        event.register((itemStack, i) ->
        {
            BlockState blockstate = ((BlockItem)itemStack.getItem()).getBlock().defaultBlockState();
            return ClientUtils.getMinecraft().getBlockColors().getColor(blockstate, ClientUtils.getLevel(), null, i);
        }, MinejagoBlocks.FOCUS_LEAVES_SET.get().leaves().get());
    }

    public static void onRegisterBlockColorHandlers(RegisterColorHandlersEvent.Block event)
    {
        event.register(((blockState, blockAndTintGetter, blockPos, i) ->
        {
            if (blockPos == null || blockAndTintGetter == null)
                return -1;
            if (i == 1 && blockAndTintGetter.getBlockEntity(blockPos) instanceof TeapotBlockEntity teapotBlockEntity && blockState.getValue(TeapotBlock.FILLED))
            {
                return PotionContents.createItemStack(Items.POTION, teapotBlockEntity.getPotion()).get(DataComponents.POTION_CONTENTS).getColor();
            }
            return -1;
        }), MinejagoBlocks.allPots().toArray(new Block[0]));
        event.register(((blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageFoliageColor(blockAndTintGetter, blockPos) : FoliageColor.getDefaultColor()), MinejagoBlocks.FOCUS_LEAVES_SET.get().leaves().get());
    }

    public static void onAddLayers(EntityRenderersEvent.AddLayers event)
    {
        EntityModelSet models = event.getEntityModels();

        for (PlayerSkin.Model skin : event.getSkins()) {
            LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> player = event.getSkin(skin);

            if (player != null)
            {
                player.addLayer(new SnapshotTesterLayer<>(player, models));
                player.addLayer(new OgDevTeamLayer<>(player, models));
            }
        }
    }

    public static void onBuildCreativeTabContent(BuildCreativeModeTabContentsEvent event)
    {
        event.acceptAll(MinejagoClientEvents.getItemsForTab(event.getTabKey(), event.getParameters().holders()));
    }

    public static void onClientConfigChanged(ModConfigEvent event)
    {
        if (event.getConfig().getType() == ModConfig.Type.CLIENT && Minecraft.getInstance().player != null)
        {
            MinejagoClientUtils.refreshVip();
        }
    }

    public static void onRegisterGuiOverlays(RegisterGuiLayersEvent event)
    {
        event.registerAbove(new ResourceLocation("food_level"), Minejago.modLoc("focus"), MinejagoGuis::renderFocusBar);
    }

    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event)
    {
        event.registerReloadListener(Minejago.getBewlr());
    }
}