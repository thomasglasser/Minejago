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
import dev.thomasglasser.minejago.platform.ForgeItemHelper;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.IModeledItem;
import dev.thomasglasser.minejago.world.item.MinejagoCreativeModeTabs;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.IGeoArmorItem;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;
import java.util.function.Supplier;

public class MinejagoForgeClientEvents {

    public static void onClientSetup(FMLClientSetupEvent event)
    {
        PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register(MinejagoPlayerAnimator::registerPlayerAnimation);
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

        event.registerEntityRenderer(MinejagoEntityTypes.WU.get(), CharacterRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.KAI.get(), CharacterRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.NYA.get(), (context) -> new CharacterRenderer<>(context, true));
        event.registerEntityRenderer(MinejagoEntityTypes.JAY.get(), CharacterRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.COLE.get(), CharacterRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.ZANE.get(), CharacterRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.UNDERWORLD_SKELETON.get(), UnderworldSkeletonRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.KRUNCHA.get(), KrunchaRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.NUCKAL.get(), NuckalRenderer::new);
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
        event.register(MinejagoParticleTypes.SPINJITZU.get(), SpinjitzuParticle.Provider::new);
        event.register(MinejagoParticleTypes.SPARKS.get(), SparksParticle.Provider::new);
        event.register(MinejagoParticleTypes.SPARKLES.get(), SparklesParticle.Provider::new);
        event.register(MinejagoParticleTypes.SNOWS.get(), SnowsParticle.Provider::new);
        event.register(MinejagoParticleTypes.ROCKS.get(), RocksParticle.Provider::new);
        event.register(MinejagoParticleTypes.BOLTS.get(), BoltsParticle.Provider::new);
        event.register(MinejagoParticleTypes.VAPORS.get(), VaporsParticle.Provider::new);
    }

    public static void onRegisterColorHandlers(RegisterColorHandlersEvent.Item event)
    {
        event.register((pStack, pTintIndex) -> {
            if (pTintIndex == 0)
                return PotionUtils.getPotion(pStack).getName("").contains("tea") ? 7028992 : PotionUtils.getColor(pStack);
            return -1;
        }, MinejagoItems.FILLED_TEACUP.get());

        event.register((pStack, pTintIndex) -> {
            if (pTintIndex == 0)
                return PotionUtils.getPotion(pStack).getName("").contains("tea") ? 7028992 : PotionUtils.getColor(pStack);
            return -1;
        }, Items.POTION);

        event.register((pStack, pTintIndex) -> {
            if (pTintIndex == 0)
                return PotionUtils.getPotion(pStack).getName("").contains("tea") ? 7028992 : PotionUtils.getColor(pStack);
            return -1;
        }, Items.SPLASH_POTION);

        event.register((pStack, pTintIndex) -> {
            if (pTintIndex == 0)
                return PotionUtils.getPotion(pStack).getName("").contains("tea") ? 7028992 : PotionUtils.getColor(pStack);
            return -1;
        }, Items.LINGERING_POTION);
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

        LivingEntityRenderer<Mob, PlayerModel<Mob>> wu = event.getRenderer(MinejagoEntityTypes.WU.get());
        if (wu != null)
        {
            wu.addLayer(new BetaTesterLayer<>(wu, models));
            wu.addLayer(new DevLayer<>(wu, models));
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


}