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
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.Character;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import dev.thomasglasser.minejago.world.item.armor.ModeledArmorItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

public class MinejagoClientEvents {

    public static void onClientSetup(FMLClientSetupEvent event)
    {
        //Set the player construct callback. It can be a lambda function.
        PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register(MinejagoPlayerAnimator::registerPlayerAnimation);
    }

    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(ThrownBoneKnifeModel.LAYER_LOCATION, ThrownBoneKnifeModel::createBodyLayer);
        event.registerLayerDefinition(BambooStaffModel.LAYER_LOCATION, BambooStaffModel::createBodyLayer);
        event.registerLayerDefinition(IronSpearModel.LAYER_LOCATION, IronSpearModel::createBodyLayer);
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
        event.register(new ResourceLocation(Minejago.MOD_ID, "item/bamboo_staff_inventory"));
        event.register(new ResourceLocation(Minejago.MOD_ID, "item/iron_spear_inventory"));
        event.register(new ResourceLocation(Minejago.MOD_ID, "item/scythe_of_quakes_inventory"));
        event.register(new ResourceLocation(Minejago.MOD_ID, "item/iron_scythe_inventory"));
        event.register(new ResourceLocation(Minejago.MOD_ID, "item/wooden_nunchucks_inventory"));
    }

    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event)
    {
        event.registerReloadListener(IClientItemExtensions.of(MinejagoItems.BAMBOO_STAFF.get()).getCustomRenderer());
        event.registerReloadListener(IClientItemExtensions.of(MinejagoItems.IRON_SPEAR.get()).getCustomRenderer());
        MinejagoArmor.ARMOR.getEntries().forEach(armor ->
        {
            if (armor.get() instanceof ModeledArmorItem)
                event.registerReloadListener(IClientItemExtensions.of(armor.get()).getCustomRenderer());
        });
        event.registerReloadListener(IClientItemExtensions.of(MinejagoItems.SCYTHE_OF_QUAKES.get()).getCustomRenderer());
        event.registerReloadListener(IClientItemExtensions.of(MinejagoItems.IRON_SCYTHE.get()).getCustomRenderer());
        event.registerReloadListener(IClientItemExtensions.of(MinejagoItems.WOODEN_NUNCHUCKS.get()).getCustomRenderer());
    }

    public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event)
    {
        event.register(MinejagoParticleTypes.SPINJITZU.get(), SpinjitzuParticle.Provider::new);
        event.register(MinejagoParticleTypes.SPARKS.get(), SparksParticle.Provider::new);
        event.register(MinejagoParticleTypes.SPARKLES.get(), SparklesParticle.Provider::new);
        event.register(MinejagoParticleTypes.SNOWS.get(), SnowsParticle.Provider::new);
        event.register(MinejagoParticleTypes.ROCKS.get(), RocksParticle.Provider::new);
        event.register(MinejagoParticleTypes.BOLTS.get(), BoltsParticle.Provider::new);
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
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event)
    {
        event.register(MinejagoKeyMappings.ACTIVATE_SPINJITZU);
    }

    public static void onAddLayers(EntityRenderersEvent.AddLayers event)
    {
        EntityModelSet models = event.getEntityModels();
        for (String skin : event.getSkins()) {
            LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> player = event.getSkin(skin);

            player.addLayer(new BetaTesterLayer<>(player, models));
            player.addLayer(new DevLayer(player, models));
        }

        LivingEntityRenderer<Character, PlayerModel<Character>> wu = event.getRenderer(MinejagoEntityTypes.WU.get());
        wu.addLayer(new BetaTesterLayer<>(wu, models));
        wu.addLayer(new DevLayer<>(wu, models));
    }

    public static void onBuildCreativeTabContent(CreativeModeTabEvent.BuildContents event)
    {
        MinejagoItems.getItemTabs().forEach((tab, itemLikes) -> {
            if (event.getTab() == tab)
            {
                itemLikes.forEach((itemLike) -> event.accept(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(itemLike))));
            }
        });

        if (event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS)
        {
            for (Potion potion : ForgeRegistries.POTIONS) {
                if (potion != Potions.EMPTY) {
                    event.accept(PotionUtils.setPotion(new ItemStack(ForgeRegistries.ITEMS.getValue(MinejagoItems.FILLED_TEACUP.getId())), potion));
                }
            }
        }

        if (event.getTab() == CreativeModeTabs.COMBAT)
        {
            for (RegistryObject<Item> item : MinejagoArmor.ARMOR.getEntries())
            {
                event.accept(item);
            }
        }
    }

    public static void onPlayerLoggedIn(ClientPlayerNetworkEvent.LoggingIn event)
    {
        MinejagoClientUtils.refreshVip();
    }

    public static void onConfigChanged(ModConfigEvent event)
    {
        if (event.getConfig().getType() == ModConfig.Type.CLIENT && Minecraft.getInstance().player != null)
        {
            MinejagoClientUtils.refreshVip();
            System.out.println(Minecraft.getInstance().player.getUUID());
        }
    }


}
