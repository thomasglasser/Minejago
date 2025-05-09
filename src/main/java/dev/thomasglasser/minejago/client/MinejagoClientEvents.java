package dev.thomasglasser.minejago.client;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.dynamiclights.entity.MinejagoEntityLuminanceTypes;
import dev.thomasglasser.minejago.client.gui.MinejagoGuis;
import dev.thomasglasser.minejago.client.model.BambooStaffModel;
import dev.thomasglasser.minejago.client.model.BouncingPoleSpinjitzuCourseElementModel;
import dev.thomasglasser.minejago.client.model.FreezingIceModel;
import dev.thomasglasser.minejago.client.model.KrunchaModel;
import dev.thomasglasser.minejago.client.model.LegacyDevTeamBeardModel;
import dev.thomasglasser.minejago.client.model.NuckalModel;
import dev.thomasglasser.minejago.client.model.PilotsSnapshotTesterHatModel;
import dev.thomasglasser.minejago.client.model.RockingPoleSpinjitzuCourseElementModel;
import dev.thomasglasser.minejago.client.model.ScytheModel;
import dev.thomasglasser.minejago.client.model.ShurikenModel;
import dev.thomasglasser.minejago.client.model.SpinjitzuModel;
import dev.thomasglasser.minejago.client.model.SpinningAxesSpinjitzuCourseElementModel;
import dev.thomasglasser.minejago.client.model.SpinningSpinjitzuCourseElementModel;
import dev.thomasglasser.minejago.client.model.ThrownBoneKnifeModel;
import dev.thomasglasser.minejago.client.particle.BoltsParticle;
import dev.thomasglasser.minejago.client.particle.RocksParticle;
import dev.thomasglasser.minejago.client.particle.SnowsParticle;
import dev.thomasglasser.minejago.client.particle.SparklesParticle;
import dev.thomasglasser.minejago.client.particle.SparksParticle;
import dev.thomasglasser.minejago.client.particle.VaporsParticle;
import dev.thomasglasser.minejago.client.renderer.MinejagoDimensionSpecialEffects;
import dev.thomasglasser.minejago.client.renderer.block.DragonButtonRenderer;
import dev.thomasglasser.minejago.client.renderer.block.DragonHeadRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.AbstractShadowCopyRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.CharacterRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.DirectionalSpinjitzuCourseElementRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.DragonRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.EarthBlastRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.HolidayCharacterRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.KrunchaRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.NuckalRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.SamukaiRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.ShadowSourceRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.SkulkinHorseRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.SkulkinRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.SkullMotorbikeRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.SkullTruckRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.SpykorRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.ThrownShurikenOfIceRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.layers.FreezingIceLayer;
import dev.thomasglasser.minejago.client.renderer.entity.layers.LegacyDevTeamLayer;
import dev.thomasglasser.minejago.client.renderer.entity.layers.SnapshotTesterLayer;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.network.ClientboundStartSpinjitzuPayload;
import dev.thomasglasser.minejago.network.ServerboundFlyVehiclePayload;
import dev.thomasglasser.minejago.network.ServerboundStopMeditationPayload;
import dev.thomasglasser.minejago.sounds.MinejagoMusics;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.minejago.world.entity.element.Elements;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.CenterSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.SpinningDummiesSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.SpinningMacesSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.SpinningPoleSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.SwirlingKnivesSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBlockEntityTypes;
import dev.thomasglasser.minejago.world.level.block.entity.TeapotBlockEntity;
import dev.thomasglasser.minejago.world.level.dimension.MinejagoDimensionTypes;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.client.renderer.entity.ThrownSwordRenderer;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import dev.thomasglasser.tommylib.api.world.entity.PlayerRideableFlying;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.client.event.SelectMusicEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.tslat.tes.api.util.TESClientUtil;
import org.lwjgl.glfw.GLFW;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MinejagoClientEvents {
    private static final List<UUID> skulkinRaids = new ArrayList<>();

    public static void onPlayerLoggedIn() {
        MinejagoClientUtils.refreshVip();
    }

    public static void onClientTick() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null)
            return;
        if (PlayerRideableFlying.isRidingFlyable(player)) {
            if (MinejagoKeyMappings.ASCEND.get().isDown()) {
                ((PlayerRideableFlying) player.getVehicle()).ascend();
                TommyLibServices.NETWORK.sendToServer(new ServerboundFlyVehiclePayload(ServerboundFlyVehiclePayload.Stage.START_ASCEND));
            } else if (MinejagoKeyMappings.DESCEND.get().isDown()) {
                ((PlayerRideableFlying) player.getVehicle()).descend();
                TommyLibServices.NETWORK.sendToServer(new ServerboundFlyVehiclePayload(ServerboundFlyVehiclePayload.Stage.START_DESCEND));
            } else {
                ((PlayerRideableFlying) player.getVehicle()).stop();
                TommyLibServices.NETWORK.sendToServer(new ServerboundFlyVehiclePayload(ServerboundFlyVehiclePayload.Stage.STOP));
            }
        }
    }

    public static void onInput(int key) {
        Player mainClientPlayer = ClientUtils.getMainClientPlayer();
        if (mainClientPlayer != null && !(key >= GLFW.GLFW_KEY_F1 && key <= GLFW.GLFW_KEY_F25) && !MinejagoKeyMappings.MEDITATE.get().isDown() && !MinejagoKeyMappings.ENTER_SHADOW_FORM.get().isDown() && key != GLFW.GLFW_KEY_LEFT_SHIFT && key != GLFW.GLFW_KEY_ESCAPE) {
            FocusData focusData = mainClientPlayer.getData(MinejagoAttachmentTypes.FOCUS);
            CompoundTag persistentData = TommyLibServices.ENTITY.getPersistentData(mainClientPlayer);
            if (focusData.isMeditating() && persistentData.getInt(MinejagoEntityEvents.KEY_WAIT_TICKS) <= 0) {
                TommyLibServices.NETWORK.sendToServer(new ServerboundStopMeditationPayload(true));
                focusData.stopMeditating();
                persistentData.putInt(MinejagoEntityEvents.KEY_WAIT_TICKS, 5);
                TommyLibServices.ENTITY.mergePersistentData(mainClientPlayer, persistentData, false);
            }
        }
    }

    public static void onInteractionKeyMappingTriggered(InputEvent.InteractionKeyMappingTriggered event) {
        Player player = ClientUtils.getMainClientPlayer();
        if (player != null && player.getData(MinejagoAttachmentTypes.SHADOW_SOURCE).isPresent() && player.getAbilities().flying) {
            event.setCanceled(true);
        }
    }

    public static int renderElementIcon(GuiGraphics guiGraphics, Minecraft mc, DeltaTracker deltaTracker, LivingEntity entity, float opacity, boolean inWorldHud) {
        if (mc.level != null && !inWorldHud) {
            Optional<Holder.Reference<Element>> element = mc.level.holder(entity.getData(MinejagoAttachmentTypes.ELEMENT).element());
            if (element.isPresent() && element.get().key() != Elements.NONE) {
                TESClientUtil.prepRenderForTexture(Element.getIcon(element.get()));
                guiGraphics.pose().pushPose();
                guiGraphics.pose().scale(0.5f, 0.5f, 1.0f);
                TESClientUtil.drawSimpleTexture(guiGraphics, -2, -1, 18, 18, 32, 32, 32, 32, 32, 32);
                guiGraphics.pose().popPose();
                return 16;
            }
        }

        return 0;
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        if (Minejago.Dependencies.LAMBDYNAMICLIGHTS.isInstalled()) MinejagoEntityLuminanceTypes.init();
    }

    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ThrownBoneKnifeModel.LAYER_LOCATION, ThrownBoneKnifeModel::createBodyLayer);
        event.registerLayerDefinition(BambooStaffModel.LAYER_LOCATION, BambooStaffModel::createBodyLayer);
        event.registerLayerDefinition(ScytheModel.LAYER_LOCATION, ScytheModel::createBodyLayer);
        event.registerLayerDefinition(ShurikenModel.LAYER_LOCATION, ShurikenModel::createBodyLayer);
        event.registerLayerDefinition(PilotsSnapshotTesterHatModel.LAYER_LOCATION, PilotsSnapshotTesterHatModel::createBodyLayer);
        event.registerLayerDefinition(LegacyDevTeamBeardModel.LAYER_LOCATION, LegacyDevTeamBeardModel::createBodyLayer);
        event.registerLayerDefinition(FreezingIceModel.LAYER_LOCATION, FreezingIceModel::createBodyLayer);
        event.registerLayerDefinition(KrunchaModel.LAYER_LOCATION, KrunchaModel::createBodyLayer);
        event.registerLayerDefinition(NuckalModel.LAYER_LOCATION, NuckalModel::createBodyLayer);
        event.registerLayerDefinition(SpinjitzuModel.LAYER_LOCATION, SpinjitzuModel::createBodyLayer);
    }

    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MinejagoEntityTypes.THROWN_SHURIKEN_OF_ICE.get(), ThrownShurikenOfIceRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.THROWN_BONE_KNIFE.get(), (context -> new ThrownSwordRenderer<>(context, MinejagoItems.BONE_KNIFE.getId(), new ThrownBoneKnifeModel(context.bakeLayer(ThrownBoneKnifeModel.LAYER_LOCATION)))));
        event.registerEntityRenderer(MinejagoEntityTypes.THROWN_BAMBOO_STAFF.get(), (context -> new ThrownSwordRenderer<>(context, MinejagoItems.BAMBOO_STAFF.getId(), new BambooStaffModel(context.bakeLayer(BambooStaffModel.LAYER_LOCATION)))));
        event.registerEntityRenderer(MinejagoEntityTypes.EARTH_BLAST.get(), EarthBlastRenderer::new);

        event.registerEntityRenderer(MinejagoEntityTypes.WU.get(), HolidayCharacterRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.KAI.get(), CharacterRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.NYA.get(), (context) -> new CharacterRenderer<>(context, true));
        event.registerEntityRenderer(MinejagoEntityTypes.JAY.get(), CharacterRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.COLE.get(), CharacterRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.ZANE.get(), CharacterRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.SKULKIN.get(), SkulkinRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.KRUNCHA.get(), KrunchaRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.NUCKAL.get(), NuckalRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.SKULKIN_HORSE.get(), SkulkinHorseRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.EARTH_DRAGON.get(), pContext -> new DragonRenderer<>(pContext, MinejagoEntityTypes.EARTH_DRAGON.getId()));
        event.registerEntityRenderer(MinejagoEntityTypes.SAMUKAI.get(), SamukaiRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.SKULL_TRUCK.get(), SkullTruckRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.SKULL_MOTORBIKE.get(), SkullMotorbikeRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.SPYKOR.get(), SpykorRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.CENTER_SPINJITZU_COURSE_ELEMENT.get(), pContext -> new DirectionalSpinjitzuCourseElementRenderer<>(pContext, new SpinningSpinjitzuCourseElementModel<CenterSpinjitzuCourseElement>(MinejagoEntityTypes.CENTER_SPINJITZU_COURSE_ELEMENT.getId(), "spinning")));
        event.registerEntityRenderer(MinejagoEntityTypes.BOUNCING_POLE_SPINJITZU_COURSE_ELEMENT.get(), pContext -> new GeoEntityRenderer<>(pContext, new BouncingPoleSpinjitzuCourseElementModel()));
        event.registerEntityRenderer(MinejagoEntityTypes.ROCKING_POLE_SPINJITZU_COURSE_ELEMENT.get(), pContext -> new DirectionalSpinjitzuCourseElementRenderer<>(pContext, new RockingPoleSpinjitzuCourseElementModel()));
        event.registerEntityRenderer(MinejagoEntityTypes.SPINNING_POLE_SPINJITZU_COURSE_ELEMENT.get(), pContext -> new DirectionalSpinjitzuCourseElementRenderer<>(pContext, new SpinningSpinjitzuCourseElementModel<SpinningPoleSpinjitzuCourseElement>(MinejagoEntityTypes.SPINNING_POLE_SPINJITZU_COURSE_ELEMENT.getId(), "pole")));
        event.registerEntityRenderer(MinejagoEntityTypes.SPINNING_MACES_SPINJITZU_COURSE_ELEMENT.get(), pContext -> new DirectionalSpinjitzuCourseElementRenderer<>(pContext, new SpinningSpinjitzuCourseElementModel<SpinningMacesSpinjitzuCourseElement>(MinejagoEntityTypes.SPINNING_MACES_SPINJITZU_COURSE_ELEMENT.getId(), "structure")));
        event.registerEntityRenderer(MinejagoEntityTypes.SPINNING_DUMMIES_SPINJITZU_COURSE_ELEMENT.get(), pContext -> new DirectionalSpinjitzuCourseElementRenderer<>(pContext, new SpinningSpinjitzuCourseElementModel<SpinningDummiesSpinjitzuCourseElement>(MinejagoEntityTypes.SPINNING_DUMMIES_SPINJITZU_COURSE_ELEMENT.getId(), "structure")));
        event.registerEntityRenderer(MinejagoEntityTypes.SWIRLING_KNIVES_SPINJITZU_COURSE_ELEMENT.get(), pContext -> new DirectionalSpinjitzuCourseElementRenderer<>(pContext, new SpinningSpinjitzuCourseElementModel<SwirlingKnivesSpinjitzuCourseElement>(MinejagoEntityTypes.SWIRLING_KNIVES_SPINJITZU_COURSE_ELEMENT.getId(), "structure")));
        event.registerEntityRenderer(MinejagoEntityTypes.SPINNING_AXES_SPINJITZU_COURSE_ELEMENT.get(), pContext -> new DirectionalSpinjitzuCourseElementRenderer<>(pContext, new SpinningAxesSpinjitzuCourseElementModel()));

        event.registerEntityRenderer(MinejagoEntityTypes.EARTH_DRAGON_HEAD.get(), context -> new DragonHeadRenderer(context, MinejagoEntityTypes.EARTH_DRAGON_HEAD.getId()));

        event.registerEntityRenderer(MinejagoEntityTypes.SHADOW_SOURCE.get(), ShadowSourceRenderer::new);
        event.registerEntityRenderer(MinejagoEntityTypes.SHADOW_CLONE.get(), AbstractShadowCopyRenderer::new);

        event.registerBlockEntityRenderer(MinejagoBlockEntityTypes.DRAGON_BUTTON.get(), context -> new DragonButtonRenderer(MinejagoBlocks.DRAGON_BUTTON.getId()));
    }

    public static void registerModels(ModelEvent.RegisterAdditional event) {
        ResourceManager manager = Minecraft.getInstance().getResourceManager();
        Map<ResourceLocation, Resource> map = manager.listResources("models/item/minejago_armor", (location -> location.getPath().endsWith(".json")));
        for (ResourceLocation rl : map.keySet()) {
            ResourceLocation stripped = ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), rl.getPath().substring("models/".length(), rl.getPath().indexOf(".json")));
            event.register(ModelResourceLocation.standalone(stripped));
        }
    }

    public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(MinejagoParticleTypes.SPARKS.get(), SparksParticle.Provider::new);
        event.registerSpriteSet(MinejagoParticleTypes.SPARKLES.get(), SparklesParticle.Provider::new);
        event.registerSpriteSet(MinejagoParticleTypes.SNOWS.get(), SnowsParticle.Provider::new);
        event.registerSpriteSet(MinejagoParticleTypes.ROCKS.get(), RocksParticle.Provider::new);
        event.registerSpriteSet(MinejagoParticleTypes.BOLTS.get(), BoltsParticle.Provider::new);
        event.registerSpriteSet(MinejagoParticleTypes.VAPORS.get(), VaporsParticle.Provider::new);
    }

    public static void onRegisterItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register((pStack, pTintIndex) -> {
            if (pTintIndex == 1)
                return FastColor.ARGB32.opaque(pStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor());
            return -1;
        }, MinejagoItems.allFilledTeacups().toArray(new ItemLike[0]));
        event.register((itemStack, i) -> {
            BlockState blockstate = ((BlockItem) itemStack.getItem()).getBlock().defaultBlockState();
            return ClientUtils.getMinecraft().getBlockColors().getColor(blockstate, ClientUtils.getLevel(), null, i);
        }, MinejagoBlocks.FOCUS_LEAVES_SET.leaves().get());
    }

    public static void onRegisterBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register(((blockState, blockAndTintGetter, blockPos, i) -> {
            if (blockPos == null || blockAndTintGetter == null)
                return -1;
            if (i == 1 && blockAndTintGetter.getBlockEntity(blockPos) instanceof TeapotBlockEntity teapotBlockEntity && blockState.getValue(TeapotBlock.CUPS) > 3) {
                return PotionContents.createItemStack(Items.POTION, teapotBlockEntity.getPotion()).get(DataComponents.POTION_CONTENTS).getColor();
            }
            return -1;
        }), MinejagoBlocks.allPots().toArray(new Block[0]));
        event.register(((blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageFoliageColor(blockAndTintGetter, blockPos) : FoliageColor.getDefaultColor()), MinejagoBlocks.FOCUS_LEAVES_SET.leaves().get());
    }

    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        EntityModelSet models = event.getEntityModels();

        for (PlayerSkin.Model skin : event.getSkins()) {
            PlayerRenderer player = event.getSkin(skin);

            if (player != null) {
                player.addLayer(new FreezingIceLayer(player, models));
                player.addLayer(new SnapshotTesterLayer<>(player, models));
                player.addLayer(new LegacyDevTeamLayer<>(player, models));
            }
        }

        for (EntityType<?> type : event.getEntityTypes()) {
            if (event.getRenderer(type) instanceof LivingEntityRenderer livingRenderer) {
                livingRenderer.addLayer(new FreezingIceLayer<>(livingRenderer, models));
            }
        }
    }

    public static void onClientConfigChanged(ModConfigEvent event) {
        if (event.getConfig().getType() == ModConfig.Type.CLIENT && Minecraft.getInstance().player != null) {
            MinejagoClientUtils.refreshVip();
        }
    }

    public static void onRegisterGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(ResourceLocation.withDefaultNamespace("food_level"), Minejago.modLoc("focus"), (guiGraphics, deltaTracker) -> MinejagoGuis.renderFocusBar(guiGraphics));
    }

    public static void onRegisterClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(MinejagoClientUtils.getBewlr());
        event.registerReloadListener((ResourceManagerReloadListener) resourceManager -> {
            MinejagoClientUtils.refreshVip();
            AbstractShadowCopyRenderer.clearShadowTextures();
            spinjitzuModel = null;
        });
    }

    public static void onPrePlayerRender(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        if (player.getData(MinejagoAttachmentTypes.SHADOW_SOURCE).isPresent() && player.getAbilities().flying) {
            event.setCanceled(true);
        }
    }

    private static SpinjitzuModel<Player> spinjitzuModel = null;

    public static void onPostPlayerRender(RenderPlayerEvent.Post event) {
        Player player = event.getEntity();
        if (spinjitzuModel == null)
            spinjitzuModel = new SpinjitzuModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(SpinjitzuModel.LAYER_LOCATION));
        CompoundTag data = TommyLibServices.ENTITY.getPersistentData(player);
        boolean renderTornadoOfCreation = data.getInt(MinejagoEntityEvents.KEY_TORNADO_OF_CREATION_SECONDS) > 0;
        if (!(ClientUtils.getMainClientPlayer() == player && ClientUtils.getMinecraft().options.getCameraType().isFirstPerson()) && player.getData(MinejagoAttachmentTypes.SPINJITZU).active() && data.getInt(ClientboundStartSpinjitzuPayload.KEY_SPINJITZUSTARTTICKS) <= 0 && (!data.getBoolean(MinejagoEntityEvents.KEY_IS_IN_TORNADO_OF_CREATION) || renderTornadoOfCreation)) {
            spinjitzuModel.setupAnim(player, 0, 0, player.tickCount + event.getPartialTick(), 0, 0);
            spinjitzuModel.getBody().copyFrom(event.getRenderer().getModel().body);
            float scale = (float) player.getAttributeValue(Attributes.SCALE);
            if (renderTornadoOfCreation)
                scale *= 5;
            event.getPoseStack().scale(scale, scale, scale);
            int color = player.level().holderOrThrow(player.getData(MinejagoAttachmentTypes.ELEMENT).element()).value().color().getValue();
            if (renderTornadoOfCreation)
                color = player.level().holderOrThrow(Elements.CREATION).value().color().getValue();
            spinjitzuModel.render(event.getPoseStack(), event.getMultiBufferSource(), player.tickCount, event.getPartialTick(), 0xFF000000 | color);
        }
    }

    public static boolean hasSkulkinRaid() {
        BossHealthOverlay bossHealthOverlay = ClientUtils.getMinecraft().gui.getBossOverlay();
        return skulkinRaids.stream().anyMatch(bossHealthOverlay.events::containsKey);
    }

    public static void onSelectMusic(SelectMusicEvent event) {
        Player player = ClientUtils.getMainClientPlayer();
        if (player != null) {
            CompoundTag data = TommyLibServices.ENTITY.getPersistentData(player);
            BossHealthOverlay bossOverlay = ClientUtils.getMinecraft().gui.getBossOverlay();
            if (bossOverlay.shouldPlayMusic() && hasSkulkinRaid()) {
                event.setMusic(MinejagoMusics.SKULKIN_RAID);
            } else if (data.getBoolean(MinejagoEntityEvents.KEY_IS_IN_MONASTERY_OF_SPINJITZU)) {
                event.setMusic(MinejagoMusics.MONASTERY_OF_SPINJITZU);
            } else if (data.getBoolean(MinejagoEntityEvents.KEY_IS_IN_CAVE_OF_DESPAIR)) {
                event.setMusic(MinejagoMusics.CAVE_OF_DESPAIR);
            }
        }
    }

    public static void addSkulkinRaid(UUID id) {
        skulkinRaids.add(id);
    }

    public static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        CreativeModeTab.ItemDisplayParameters parameters = event.getParameters();
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            // Wood Sets
            event.insertAfter(Items.CHERRY_BUTTON.getDefaultInstance(), MinejagoBlocks.ENCHANTED_WOOD_SET.log().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.ENCHANTED_WOOD_SET.log().toStack(), MinejagoBlocks.ENCHANTED_WOOD_SET.wood().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.ENCHANTED_WOOD_SET.wood().toStack(), MinejagoBlocks.ENCHANTED_WOOD_SET.strippedLog().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.ENCHANTED_WOOD_SET.strippedLog().toStack(), MinejagoBlocks.ENCHANTED_WOOD_SET.strippedWood().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.ENCHANTED_WOOD_SET.strippedWood().toStack(), MinejagoBlocks.ENCHANTED_WOOD_SET.planks().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.ENCHANTED_WOOD_SET.planks().toStack(), MinejagoBlocks.ENCHANTED_WOOD_SET.stairs().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.ENCHANTED_WOOD_SET.stairs().toStack(), MinejagoBlocks.ENCHANTED_WOOD_SET.slab().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.ENCHANTED_WOOD_SET.slab().toStack(), MinejagoBlocks.ENCHANTED_WOOD_SET.fence().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.ENCHANTED_WOOD_SET.fence().toStack(), MinejagoBlocks.ENCHANTED_WOOD_SET.fenceGate().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.ENCHANTED_WOOD_SET.fenceGate().toStack(), MinejagoBlocks.ENCHANTED_WOOD_SET.door().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.ENCHANTED_WOOD_SET.door().toStack(), MinejagoBlocks.ENCHANTED_WOOD_SET.trapdoor().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.ENCHANTED_WOOD_SET.trapdoor().toStack(), MinejagoBlocks.ENCHANTED_WOOD_SET.pressurePlate().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.ENCHANTED_WOOD_SET.pressurePlate().toStack(), MinejagoBlocks.ENCHANTED_WOOD_SET.button().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.LIGHT_WEIGHTED_PRESSURE_PLATE.getDefaultInstance(), MinejagoBlocks.GOLD_DISC.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(MinejagoBlocks.TOP_POST.toStack());
        } else if (event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
            event.accept(MinejagoBlocks.TEAPOT);
            event.accept(MinejagoBlocks.TEAPOTS.get(DyeColor.WHITE));
            event.accept(MinejagoBlocks.TEAPOTS.get(DyeColor.LIGHT_GRAY));
            event.accept(MinejagoBlocks.TEAPOTS.get(DyeColor.GRAY));
            event.accept(MinejagoBlocks.TEAPOTS.get(DyeColor.BLACK));
            event.accept(MinejagoBlocks.TEAPOTS.get(DyeColor.BROWN));
            event.accept(MinejagoBlocks.TEAPOTS.get(DyeColor.RED));
            event.accept(MinejagoBlocks.TEAPOTS.get(DyeColor.ORANGE));
            event.accept(MinejagoBlocks.TEAPOTS.get(DyeColor.YELLOW));
            event.accept(MinejagoBlocks.TEAPOTS.get(DyeColor.LIME));
            event.accept(MinejagoBlocks.TEAPOTS.get(DyeColor.GREEN));
            event.accept(MinejagoBlocks.TEAPOTS.get(DyeColor.CYAN));
            event.accept(MinejagoBlocks.TEAPOTS.get(DyeColor.LIGHT_BLUE));
            event.accept(MinejagoBlocks.TEAPOTS.get(DyeColor.BLUE));
            event.accept(MinejagoBlocks.TEAPOTS.get(DyeColor.PURPLE));
            event.accept(MinejagoBlocks.TEAPOTS.get(DyeColor.MAGENTA));
            event.accept(MinejagoBlocks.TEAPOTS.get(DyeColor.PINK));
            event.accept(MinejagoBlocks.JASPOT);
        } else if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            // Wood Sets
            event.insertAfter(Items.CHERRY_LOG.getDefaultInstance(), MinejagoBlocks.ENCHANTED_WOOD_SET.log().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            // Leaves Sets
            event.insertAfter(Items.CHERRY_LEAVES.getDefaultInstance(), MinejagoBlocks.FOCUS_LEAVES_SET.leaves().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.CHERRY_SAPLING.getDefaultInstance(), MinejagoBlocks.FOCUS_LEAVES_SET.sapling().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.BLUE_ICE.getDefaultInstance(), MinejagoBlocks.FREEZING_ICE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.insertAfter(Items.SUSPICIOUS_SAND.getDefaultInstance(), MinejagoBlocks.SUSPICIOUS_RED_SAND.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.CHISELED_BOOKSHELF.getDefaultInstance(), MinejagoBlocks.SCROLL_SHELF.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.SCROLL_SHELF.toStack(), MinejagoBlocks.CHISELED_SCROLL_SHELF.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            // Wood Sets
            event.insertAfter(Items.CHERRY_HANGING_SIGN.getDefaultInstance(), MinejagoBlocks.ENCHANTED_WOOD_SET.sign().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.ENCHANTED_WOOD_SET.sign().toStack(), MinejagoBlocks.ENCHANTED_WOOD_SET.hangingSign().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            // Colored Blocks
            event.insertAfter(Raid.getLeaderBannerInstance(event.getParameters().holders().lookupOrThrow(Registries.BANNER_PATTERN)), MinejagoBlocks.TEAPOT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.TEAPOT.toStack(), MinejagoBlocks.TEAPOTS.get(DyeColor.WHITE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.TEAPOTS.get(DyeColor.WHITE).toStack(), MinejagoBlocks.TEAPOTS.get(DyeColor.LIGHT_GRAY).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.TEAPOTS.get(DyeColor.LIGHT_GRAY).toStack(), MinejagoBlocks.TEAPOTS.get(DyeColor.GRAY).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.TEAPOTS.get(DyeColor.GRAY).toStack(), MinejagoBlocks.TEAPOTS.get(DyeColor.BLACK).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.TEAPOTS.get(DyeColor.BLACK).toStack(), MinejagoBlocks.TEAPOTS.get(DyeColor.BROWN).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.TEAPOTS.get(DyeColor.BROWN).toStack(), MinejagoBlocks.TEAPOTS.get(DyeColor.RED).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.TEAPOTS.get(DyeColor.RED).toStack(), MinejagoBlocks.TEAPOTS.get(DyeColor.ORANGE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.TEAPOTS.get(DyeColor.ORANGE).toStack(), MinejagoBlocks.TEAPOTS.get(DyeColor.YELLOW).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.TEAPOTS.get(DyeColor.YELLOW).toStack(), MinejagoBlocks.TEAPOTS.get(DyeColor.LIME).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.TEAPOTS.get(DyeColor.LIME).toStack(), MinejagoBlocks.TEAPOTS.get(DyeColor.GREEN).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.TEAPOTS.get(DyeColor.GREEN).toStack(), MinejagoBlocks.TEAPOTS.get(DyeColor.CYAN).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.TEAPOTS.get(DyeColor.CYAN).toStack(), MinejagoBlocks.TEAPOTS.get(DyeColor.LIGHT_BLUE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.TEAPOTS.get(DyeColor.LIGHT_BLUE).toStack(), MinejagoBlocks.TEAPOTS.get(DyeColor.BLUE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.TEAPOTS.get(DyeColor.BLUE).toStack(), MinejagoBlocks.TEAPOTS.get(DyeColor.PURPLE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.TEAPOTS.get(DyeColor.PURPLE).toStack(), MinejagoBlocks.TEAPOTS.get(DyeColor.MAGENTA).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.TEAPOTS.get(DyeColor.MAGENTA).toStack(), MinejagoBlocks.TEAPOTS.get(DyeColor.PINK).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.TEAPOTS.get(DyeColor.PINK).toStack(), MinejagoBlocks.JASPOT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.insertAfter(Items.LEVER.getDefaultInstance(), MinejagoBlocks.DRAGON_BUTTON.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            // Spinjitzu Course Elements
            event.insertAfter(Items.BELL.getDefaultInstance(), MinejagoItems.CENTER_SPINJITZU_COURSE_ELEMENT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.CENTER_SPINJITZU_COURSE_ELEMENT.toStack(), MinejagoItems.SPINNING_DUMMIES_SPINJITZU_COURSE_ELEMENT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.SPINNING_DUMMIES_SPINJITZU_COURSE_ELEMENT.toStack(), MinejagoItems.SPINNING_POLE_SPINJITZU_COURSE_ELEMENT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.SPINNING_POLE_SPINJITZU_COURSE_ELEMENT.toStack(), MinejagoItems.SWIRLING_KNIVES_SPINJITZU_COURSE_ELEMENT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.SWIRLING_KNIVES_SPINJITZU_COURSE_ELEMENT.toStack(), MinejagoItems.BOUNCING_POLE_SPINJITZU_COURSE_ELEMENT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.BOUNCING_POLE_SPINJITZU_COURSE_ELEMENT.toStack(), MinejagoItems.SPINNING_AXES_SPINJITZU_COURSE_ELEMENT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.SPINNING_AXES_SPINJITZU_COURSE_ELEMENT.toStack(), MinejagoItems.ROCKING_POLE_SPINJITZU_COURSE_ELEMENT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.ROCKING_POLE_SPINJITZU_COURSE_ELEMENT.toStack(), MinejagoItems.SPINNING_MACES_SPINJITZU_COURSE_ELEMENT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.insertAfter(Items.NETHERITE_HOE.getDefaultInstance(), MinejagoItems.SCYTHE_OF_QUAKES.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.SCYTHE_OF_QUAKES.toStack(), MinejagoItems.SHURIKEN_OF_ICE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.SHURIKEN_OF_ICE.toStack(), MinejagoItems.NUNCHUCKS_OF_LIGHTNING.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.WRITABLE_BOOK.getDefaultInstance(), MinejagoItems.WRITABLE_SCROLL.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            // Wood Sets
            event.insertAfter(Items.CHERRY_CHEST_BOAT.getDefaultInstance(), MinejagoBlocks.ENCHANTED_WOOD_SET.boatItem().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.ENCHANTED_WOOD_SET.boatItem().toStack(), MinejagoBlocks.ENCHANTED_WOOD_SET.chestBoatItem().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            // Unique Weapons
            event.insertAfter(Items.MACE.getDefaultInstance(), MinejagoItems.SCYTHE_OF_QUAKES.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.SCYTHE_OF_QUAKES.toStack(), MinejagoItems.SHURIKEN_OF_ICE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.SHURIKEN_OF_ICE.toStack(), MinejagoItems.NUNCHUCKS_OF_LIGHTNING.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.NUNCHUCKS_OF_LIGHTNING.toStack(), MinejagoItems.BAMBOO_STAFF.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.BAMBOO_STAFF.toStack(), MinejagoItems.BONE_KNIFE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            // Armor Sets
            event.insertAfter(Items.NETHERITE_BOOTS.getDefaultInstance(), MinejagoArmors.BLACK_GI_SET.HEAD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoArmors.BLACK_GI_SET.HEAD.toStack(), MinejagoArmors.BLACK_GI_SET.CHEST.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoArmors.BLACK_GI_SET.CHEST.toStack(), MinejagoArmors.BLACK_GI_SET.LEGS.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoArmors.BLACK_GI_SET.LEGS.toStack(), MinejagoArmors.BLACK_GI_SET.FEET.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            // Unique Armor
            List<ItemStack> skeletalChestplates = MinejagoArmors.SKELETAL_CHESTPLATE_SET.getAllAsStacks();
            event.insertAfter(Items.TURTLE_HELMET.getDefaultInstance(), skeletalChestplates.getFirst(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            for (int i = 1; i < skeletalChestplates.size(); i++) {
                event.insertAfter(skeletalChestplates.get(i - 1), skeletalChestplates.get(i), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
            event.insertAfter(skeletalChestplates.getLast(), MinejagoArmors.SAMUKAIS_CHESTPLATE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            parameters.holders()
                    .lookup(Registries.POTION)
                    .ifPresent(
                            potion -> {
                                CreativeModeTabs.generatePotionEffectTypes(
                                        event,
                                        potion,
                                        MinejagoItems.FILLED_TEACUP.get(),
                                        CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS,
                                        parameters.enabledFeatures());
                            });
        } else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.insertAfter(Items.BOOK.getDefaultInstance(), MinejagoItems.SCROLL.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.GLASS_BOTTLE.getDefaultInstance(), MinejagoItems.TEACUP.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.TEACUP.toStack(), MinejagoItems.TEACUPS.get(DyeColor.WHITE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.TEACUPS.get(DyeColor.WHITE).toStack(), MinejagoItems.TEACUPS.get(DyeColor.LIGHT_GRAY).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.TEACUPS.get(DyeColor.LIGHT_GRAY).toStack(), MinejagoItems.TEACUPS.get(DyeColor.GRAY).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.TEACUPS.get(DyeColor.GRAY).toStack(), MinejagoItems.TEACUPS.get(DyeColor.BLACK).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.TEACUPS.get(DyeColor.BLACK).toStack(), MinejagoItems.TEACUPS.get(DyeColor.BROWN).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.TEACUPS.get(DyeColor.BROWN).toStack(), MinejagoItems.TEACUPS.get(DyeColor.RED).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.TEACUPS.get(DyeColor.RED).toStack(), MinejagoItems.TEACUPS.get(DyeColor.ORANGE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.TEACUPS.get(DyeColor.ORANGE).toStack(), MinejagoItems.TEACUPS.get(DyeColor.YELLOW).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.TEACUPS.get(DyeColor.YELLOW).toStack(), MinejagoItems.TEACUPS.get(DyeColor.LIME).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.TEACUPS.get(DyeColor.LIME).toStack(), MinejagoItems.TEACUPS.get(DyeColor.GREEN).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.TEACUPS.get(DyeColor.GREEN).toStack(), MinejagoItems.TEACUPS.get(DyeColor.CYAN).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.TEACUPS.get(DyeColor.CYAN).toStack(), MinejagoItems.TEACUPS.get(DyeColor.LIGHT_BLUE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.TEACUPS.get(DyeColor.LIGHT_BLUE).toStack(), MinejagoItems.TEACUPS.get(DyeColor.BLUE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.TEACUPS.get(DyeColor.BLUE).toStack(), MinejagoItems.TEACUPS.get(DyeColor.PURPLE).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.TEACUPS.get(DyeColor.PURPLE).toStack(), MinejagoItems.TEACUPS.get(DyeColor.MAGENTA).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.TEACUPS.get(DyeColor.MAGENTA).toStack(), MinejagoItems.TEACUPS.get(DyeColor.PINK).toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.TEACUPS.get(DyeColor.PINK).toStack(), MinejagoItems.MINICUP.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            // Tea Ingredients
            event.insertAfter(Items.PHANTOM_MEMBRANE.getDefaultInstance(), Items.OAK_LEAVES.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.OAK_LEAVES.getDefaultInstance(), Items.SPRUCE_LEAVES.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.SPRUCE_LEAVES.getDefaultInstance(), Items.BIRCH_LEAVES.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.BIRCH_LEAVES.getDefaultInstance(), Items.JUNGLE_LEAVES.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.JUNGLE_LEAVES.getDefaultInstance(), Items.ACACIA_LEAVES.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.ACACIA_LEAVES.getDefaultInstance(), Items.DARK_OAK_LEAVES.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.DARK_OAK_LEAVES.getDefaultInstance(), Items.MANGROVE_LEAVES.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.MANGROVE_LEAVES.getDefaultInstance(), Items.CHERRY_LEAVES.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.CHERRY_LEAVES.getDefaultInstance(), MinejagoBlocks.FOCUS_LEAVES_SET.leaves().toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoBlocks.FOCUS_LEAVES_SET.leaves().toStack(), Items.AZALEA_LEAVES.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.AZALEA_LEAVES.getDefaultInstance(), Items.FLOWERING_AZALEA_LEAVES.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            // Banner Patterns
            event.insertAfter(Items.GUSTER_BANNER_PATTERN.getDefaultInstance(), MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.toStack(), MinejagoItems.NINJA_BANNER_PATTERN.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            // Pottery Sherds
            event.insertAfter(Items.SNORT_POTTERY_SHERD.getDefaultInstance(), MinejagoItems.ICE_CUBE_POTTERY_SHERD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.ICE_CUBE_POTTERY_SHERD.toStack(), MinejagoItems.THUNDER_POTTERY_SHERD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.THUNDER_POTTERY_SHERD.toStack(), MinejagoItems.PEAKS_POTTERY_SHERD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.PEAKS_POTTERY_SHERD.toStack(), MinejagoItems.MASTER_POTTERY_SHERD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.MASTER_POTTERY_SHERD.toStack(), MinejagoItems.YIN_YANG_POTTERY_SHERD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.YIN_YANG_POTTERY_SHERD.toStack(), MinejagoItems.DRAGONS_HEAD_POTTERY_SHERD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.DRAGONS_HEAD_POTTERY_SHERD.toStack(), MinejagoItems.DRAGONS_TAIL_POTTERY_SHERD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            // Smithing Templates
            event.insertAfter(Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE.getDefaultInstance(), MinejagoItems.FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE.toStack(), MinejagoItems.TERRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.TERRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.toStack(), MinejagoItems.LOTUS_ARMOR_TRIM_SMITHING_TEMPLATE.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            // Must be in alphabetical order
            event.insertAfter(Items.COD_SPAWN_EGG.getDefaultInstance(), MinejagoItems.COLE_SPAWN_EGG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.DROWNED_SPAWN_EGG.getDefaultInstance(), MinejagoItems.EARTH_DRAGON_SPAWN_EGG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.IRON_GOLEM_SPAWN_EGG.getDefaultInstance(), MinejagoItems.JAY_SPAWN_EGG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.JAY_SPAWN_EGG.toStack(), MinejagoItems.KAI_SPAWN_EGG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.KAI_SPAWN_EGG.toStack(), MinejagoItems.KRUNCHA_SPAWN_EGG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.MULE_SPAWN_EGG.getDefaultInstance(), MinejagoItems.NUCKAL_SPAWN_EGG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.NUCKAL_SPAWN_EGG.toStack(), MinejagoItems.NYA_SPAWN_EGG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.SALMON_SPAWN_EGG.getDefaultInstance(), MinejagoItems.SAMUKAI_SPAWN_EGG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.SKELETON_HORSE_SPAWN_EGG.getDefaultInstance(), MinejagoItems.SKULL_MOTORBIKE_SPAWN_EGG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.SKULL_MOTORBIKE_SPAWN_EGG.toStack(), MinejagoItems.SKULL_TRUCK_SPAWN_EGG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.SKULL_TRUCK_SPAWN_EGG.toStack(), MinejagoItems.SKULKIN_SPAWN_EGG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.SKULKIN_SPAWN_EGG.toStack(), MinejagoItems.SKULKIN_HORSE_SPAWN_EGG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.SPIDER_SPAWN_EGG.getDefaultInstance(), MinejagoItems.SPYKOR_SPAWN_EGG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.insertAfter(Items.WOLF_SPAWN_EGG.getDefaultInstance(), MinejagoItems.WU_SPAWN_EGG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(MinejagoItems.WU_SPAWN_EGG.toStack(), MinejagoItems.ZANE_SPAWN_EGG.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else if (event.getTabKey() == CreativeModeTabs.OP_BLOCKS && Minecraft.getInstance().options.operatorItemsTab().get()) {
            event.insertAfter(Items.STRUCTURE_VOID.getDefaultInstance(), MinejagoItems.EARTH_DRAGON_HEAD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    public static void onRegisterDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(MinejagoDimensionTypes.UNDERWORLD.location(), MinejagoDimensionSpecialEffects.UNDERWORLD);
    }
}
