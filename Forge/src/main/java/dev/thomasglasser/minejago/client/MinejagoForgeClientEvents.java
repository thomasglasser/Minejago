package dev.thomasglasser.minejago.client;

import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.MinejagoPlayerAnimator;
import dev.thomasglasser.minejago.client.model.*;
import dev.thomasglasser.minejago.client.particle.*;
import dev.thomasglasser.minejago.client.renderer.entity.*;
import dev.thomasglasser.minejago.client.renderer.entity.layers.BetaTesterLayer;
import dev.thomasglasser.minejago.client.renderer.entity.layers.DevLayer;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.packs.MinejagoPacks;
import dev.thomasglasser.minejago.packs.PackHolder;
import dev.thomasglasser.minejago.platform.ForgeItemHelper;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.IModeledItem;
import dev.thomasglasser.minejago.world.item.MinejagoCreativeModeTabs;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.IGeoArmorItem;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.minejago.world.level.block.entity.TeapotBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.resource.PathPackResources;

import java.util.Map;
import java.util.function.Supplier;

public class MinejagoForgeClientEvents {

    public static void onClientSetup(FMLClientSetupEvent event)
    {
        if (Minejago.Dependencies.PLAYER_ANIMATOR.isInstalled()) PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register(MinejagoPlayerAnimator::registerPlayerAnimation);
    }

    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(ThrownBoneKnifeModel.LAYER_LOCATION, ThrownBoneKnifeModel::createBodyLayer);
        event.registerLayerDefinition(BambooStaffModel.LAYER_LOCATION, BambooStaffModel::createBodyLayer);
        event.registerLayerDefinition(SpearModel.LAYER_LOCATION, SpearModel::createBodyLayer);
        event.registerLayerDefinition(ThrownIronShurikenModel.LAYER_LOCATION, ThrownIronShurikenModel::createBodyLayer);
        event.registerLayerDefinition(ScytheModel.LAYER_LOCATION, ScytheModel::createBodyLayer);
        event.registerLayerDefinition(BambooHatModel.LAYER_LOCATION, BambooHatModel::createBodyLayer);
        event.registerLayerDefinition(BeardModel.LAYER_LOCATION, BeardModel::createBodyLayer);
        event.registerLayerDefinition(KrunchaModel.LAYER_LOCATION, KrunchaModel::createBodyLayer);
        event.registerLayerDefinition(NuckalModel.LAYER_LOCATION, NuckalModel::createBodyLayer);
    }

    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(MinejagoEntityTypes.THROWN_BONE_KNIFE.get(), ThrownBoneKnifeRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.THROWN_BAMBOO_STAFF.get(), ThrownBambooStaffRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.THROWN_IRON_SPEAR.get(), ThrownIronSpearRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.THROWN_IRON_SHURIKEN.get(), ThrownIronShurikenRenderer::new);

        event.registerEntityRenderer(MinejagoEntityTypes.WU.get(), WuRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.KAI.get(), CharacterRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.NYA.get(), (context) -> new CharacterRenderer<>(context, true));
        event.registerEntityRenderer(MinejagoEntityTypes.JAY.get(), CharacterRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.COLE.get(), CharacterRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.ZANE.get(), CharacterRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.SKULKIN.get(), UnderworldSkeletonRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.KRUNCHA.get(), KrunchaRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.NUCKAL.get(), NuckalRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.SKULKIN_HORSE.get(), SkulkinHorseRenderer::new);
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

    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event)
    {
        MinejagoArmor.ARMOR.getEntries().forEach(armor ->
        {
            if (armor.get() instanceof IGeoArmorItem || armor.get() instanceof IModeledItem)
                event.registerReloadListener(IClientItemExtensions.of(armor.get()).getCustomRenderer());
        });
        MinejagoItems.ITEMS.getEntries().forEach(item ->
        {
            if (item.get() instanceof IModeledItem)
                event.registerReloadListener(IClientItemExtensions.of(item.get()).getCustomRenderer());
        });
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
                return PotionUtils.getColor(pStack);
            return -1;
        }, MinejagoItems.FILLED_TEACUP.get());
    }

    public static void onRegisterBlockColorHandlers(RegisterColorHandlersEvent.Block event)
    {
        event.register(((blockState, blockAndTintGetter, blockPos, i) ->
        {
            if (blockPos == null || blockAndTintGetter == null)
                return -1;
            if (i == 1 && blockAndTintGetter.getBlockEntity(blockPos) instanceof TeapotBlockEntity teapotBlockEntity && blockState.getValue(TeapotBlock.FILLED))
            {
                return teapotBlockEntity.getPotion().getName("").contains("tea") || teapotBlockEntity.getPotion().getName("").contains("awkward") ? 7028992 : PotionUtils.getColor(PotionUtils.setPotion(new ItemStack(Items.POTION), teapotBlockEntity.getPotion()));
            }
            return -1;
        }), MinejagoBlocks.allPots().toArray(new Block[0]));
    }

    public static void onAddLayers(EntityRenderersEvent.AddLayers event)
    {
        EntityModelSet models = event.getEntityModels();

        for (String skin : event.getSkins()) {
            LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> player = event.getSkin(skin);

            if (player != null)
            {
                player.addLayer(new BetaTesterLayer<>(player, models));
                player.addLayer(new DevLayer<>(player, models));
            }
        }
    }

    public static void onBuildCreativeTabContent(CreativeModeTabEvent.BuildContents event)
    {
        event.acceptAll(MinejagoItems.getItemsForTab(event.getTab()));
    }

    public static void onRegisterCreativeTab(CreativeModeTabEvent.Register event)
    {
        ((ForgeItemHelper)(Services.ITEM)).TABS.forEach(list ->
        {
            if (list.get(0).equals(Minejago.modLoc("gi")))
            {
                MinejagoCreativeModeTabs.GI = event.registerCreativeModeTab((ResourceLocation) list.get(0), (builder ->
                {
                    builder.title((Component) list.get(1)).icon((Supplier<ItemStack>) list.get(2)).displayItems((CreativeModeTab.DisplayItemsGenerator) list.get(3)).build();
                }));
            }
        });
    }

    public static void onClientConfigChanged(ModConfigEvent event)
    {
        if (event.getConfig().getType() == ModConfig.Type.CLIENT && Minecraft.getInstance().player != null)
        {
            MinejagoClientUtils.refreshVip();
        }
    }

    public static void onAddPackFinders(AddPackFindersEvent event)
    {
        for (PackHolder holder : MinejagoPacks.getPacks())
        {
            if (event.getPackType() == holder.type())
            {
                var resourcePath = ModList.get().getModFileById(Minejago.MOD_ID).getFile().findResource("resourcepacks/" + holder.id().getPath());
                var pack = Pack.readMetaAndCreate("builtin/" + holder.id().getPath(), Component.translatable(holder.titleKey()), holder.required(),
                        (path) -> new PathPackResources(path, false, resourcePath), holder.type(), Pack.Position.BOTTOM, PackSource.BUILT_IN);
                event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));
            }
        }
    }
}