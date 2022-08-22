package com.thomasglasser.minejago.init;

import com.thomasglasser.minejago.MinejagoMod;
import com.thomasglasser.minejago.client.entity.model.ThrownBambooStaffModel;
import com.thomasglasser.minejago.client.entity.renderer.ThrownBambooStaffRenderer;
import com.thomasglasser.minejago.client.entity.renderer.ThrownBoneKnifeRenderer;
import com.thomasglasser.minejago.client.entity.model.ThrownBoneKnifeModel;
import com.thomasglasser.minejago.server.entity.MinejagoEntityTypes;
import com.thomasglasser.minejago.server.item.MinejagoItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MinejagoMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    public static void init(FMLClientSetupEvent event) {
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(ThrownBoneKnifeModel.LAYER_LOCATION, ThrownBoneKnifeModel::createBodyLayer);
        event.registerLayerDefinition(ThrownBambooStaffModel.LAYER_LOCATION, ThrownBambooStaffModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(MinejagoEntityTypes.THROWN_BONE_KNIFE.get(), ThrownBoneKnifeRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.THROWN_BAMBOO_STAFF.get(), ThrownBambooStaffRenderer::new);
    }

    @SubscribeEvent
    public static void registerModels(ModelEvent.RegisterAdditional event)
    {
        event.register(new ResourceLocation(MinejagoMod.MODID, "item/bamboo_staff_inventory"));
    }

    @SubscribeEvent
    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event)
    {
        event.registerReloadListener(IClientItemExtensions.of(MinejagoItems.BAMBOO_STAFF.get()).getCustomRenderer());
    }
}
