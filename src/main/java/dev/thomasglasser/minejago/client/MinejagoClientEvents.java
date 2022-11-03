package dev.thomasglasser.minejago.client;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.model.BambooStaffModel;
import dev.thomasglasser.minejago.client.model.IronSpearModel;
import dev.thomasglasser.minejago.client.model.ThrownIronShurikenModel;
import dev.thomasglasser.minejago.client.model.armor.SkeletalChestplateModel;
import dev.thomasglasser.minejago.client.particle.*;
import dev.thomasglasser.minejago.client.renderer.entity.ThrownBambooStaffRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.ThrownBoneKnifeRenderer;
import dev.thomasglasser.minejago.client.model.ThrownBoneKnifeModel;
import dev.thomasglasser.minejago.client.renderer.entity.ThrownIronShurikenRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.ThrownIronSpearRenderer;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class MinejagoClientEvents {
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(ThrownBoneKnifeModel.LAYER_LOCATION, ThrownBoneKnifeModel::createBodyLayer);
        event.registerLayerDefinition(BambooStaffModel.LAYER_LOCATION, BambooStaffModel::createBodyLayer);
        event.registerLayerDefinition(IronSpearModel.LAYER_LOCATION, IronSpearModel::createBodyLayer);
        event.registerLayerDefinition(ThrownIronShurikenModel.LAYER_LOCATION, ThrownIronShurikenModel::createBodyLayer);
        event.registerLayerDefinition(SkeletalChestplateModel.LAYER_LOCATION, SkeletalChestplateModel::createBodyLayer);
    }

    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(MinejagoEntityTypes.THROWN_BONE_KNIFE.get(), ThrownBoneKnifeRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.THROWN_BAMBOO_STAFF.get(), ThrownBambooStaffRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.THROWN_IRON_SPEAR.get(), ThrownIronSpearRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.THROWN_IRON_SHURIKEN.get(), ThrownIronShurikenRenderer::new);
    }

    public static void registerModels(ModelEvent.RegisterAdditional event)
    {
        event.register(new ResourceLocation(Minejago.MOD_ID, "item/bamboo_staff_inventory"));
        event.register(new ResourceLocation(Minejago.MOD_ID, "item/iron_spear_inventory"));
    }

    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event)
    {
        event.registerReloadListener(IClientItemExtensions.of(MinejagoItems.BAMBOO_STAFF.get()).getCustomRenderer());
        event.registerReloadListener(IClientItemExtensions.of(MinejagoItems.IRON_SPEAR.get()).getCustomRenderer());
        event.registerReloadListener(IClientItemExtensions.of(MinejagoItems.SKELETAL_CHESTPLATE.get()).getCustomRenderer());
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
        event.register(new ItemColor() {
            @Override
            public int getColor(ItemStack pStack, int pTintIndex) {
                if (pTintIndex == 0)
                    return PotionUtils.getPotion(pStack).getName("").contains("tea") ? 7028992 : PotionUtils.getColor(pStack);
                return -1;
            }
        }, MinejagoItems.FILLED_TEACUP.get());

        event.register(new ItemColor() {
            @Override
            public int getColor(ItemStack pStack, int pTintIndex) {
                if (pTintIndex == 0)
                    return PotionUtils.getPotion(pStack).getName("").contains("tea") ? 7028992 : PotionUtils.getColor(pStack);
                return -1;
            }
        }, Items.POTION);

        event.register(new ItemColor() {
            @Override
            public int getColor(ItemStack pStack, int pTintIndex) {
                if (pTintIndex == 0)
                    return PotionUtils.getPotion(pStack).getName("").contains("tea") ? 7028992 : PotionUtils.getColor(pStack);
                return -1;
            }
        }, Items.SPLASH_POTION);

        event.register(new ItemColor() {
            @Override
            public int getColor(ItemStack pStack, int pTintIndex) {
                if (pTintIndex == 0)
                    return PotionUtils.getPotion(pStack).getName("").contains("tea") ? 7028992 : PotionUtils.getColor(pStack);
                return -1;
            }
        }, Items.LINGERING_POTION);
    }
}
