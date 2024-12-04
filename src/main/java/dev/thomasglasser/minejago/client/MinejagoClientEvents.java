package dev.thomasglasser.minejago.client;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.gui.MinejagoGuis;
import dev.thomasglasser.minejago.client.model.BambooStaffModel;
import dev.thomasglasser.minejago.client.model.BouncingPoleSpinjitzuCourseElementModel;
import dev.thomasglasser.minejago.client.model.KrunchaModel;
import dev.thomasglasser.minejago.client.model.LegacyDevTeamBeardModel;
import dev.thomasglasser.minejago.client.model.NuckalModel;
import dev.thomasglasser.minejago.client.model.PilotsSnapshotTesterHatModel;
import dev.thomasglasser.minejago.client.model.RockingPoleSpinjitzuCourseElementModel;
import dev.thomasglasser.minejago.client.model.ScytheModel;
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
import dev.thomasglasser.minejago.client.renderer.block.DragonButtonRenderer;
import dev.thomasglasser.minejago.client.renderer.block.DragonHeadRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.CharacterRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.DirectionalSpinjitzuCourseElementRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.DragonRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.EarthBlastRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.HolidayCharacterRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.KrunchaRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.NuckalRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.SamukaiRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.SkulkinHorseRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.SkulkinRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.SkullMotorbikeRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.SkullTruckRenderer;
import dev.thomasglasser.minejago.client.renderer.entity.layers.LegacyDevTeamLayer;
import dev.thomasglasser.minejago.client.renderer.entity.layers.SnapshotTesterLayer;
import dev.thomasglasser.minejago.client.renderer.entity.state.MinejagoPlayerRenderState;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.network.ServerboundFlyVehiclePayload;
import dev.thomasglasser.minejago.network.ServerboundStopMeditationPayload;
import dev.thomasglasser.minejago.plugins.MinejagoDynamicLights;
import dev.thomasglasser.minejago.sounds.MinejagoMusics;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.CenterSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.SpinningDummiesSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.SpinningMacesSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.SpinningPoleSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.SwirlingKnivesSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.item.MinejagoItemUtils;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBlockEntityTypes;
import dev.thomasglasser.minejago.world.level.block.entity.TeapotBlockEntity;
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
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.blockentity.BrushableBlockRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
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
            if (MinejagoKeyMappings.ASCEND.isDown()) {
                ((PlayerRideableFlying) player.getVehicle()).ascend();
                TommyLibServices.NETWORK.sendToServer(new ServerboundFlyVehiclePayload(ServerboundFlyVehiclePayload.Stage.START_ASCEND));
            } else if (MinejagoKeyMappings.DESCEND.isDown()) {
                ((PlayerRideableFlying) player.getVehicle()).descend();
                TommyLibServices.NETWORK.sendToServer(new ServerboundFlyVehiclePayload(ServerboundFlyVehiclePayload.Stage.START_DESCEND));
            } else {
                ((PlayerRideableFlying) player.getVehicle()).stop();
                TommyLibServices.NETWORK.sendToServer(new ServerboundFlyVehiclePayload(ServerboundFlyVehiclePayload.Stage.STOP));
            }
        }
    }

    public static List<ItemStack> getItemsForTab(ResourceKey<CreativeModeTab> tab, HolderLookup.Provider lookupProvider) {
        List<ItemStack> items = new ArrayList<>();

        if (tab == CreativeModeTabs.FOOD_AND_DRINKS) {
            for (Holder<Potion> potion : BuiltInRegistries.POTION.listElements().filter(ref -> ref.key().location().getNamespace().equals(ResourceLocation.DEFAULT_NAMESPACE) || ref.key().location().getNamespace().equals(Minejago.MOD_ID)).toList()) {
                items.add(MinejagoItemUtils.fillTeacup(potion));
            }
        }

        if (tab == CreativeModeTabs.COMBAT) {
            MinejagoArmors.ARMOR_SETS.forEach(set -> items.addAll(set.getAllAsItems().stream().map(Item::getDefaultInstance).toList()));
        }

        return items;
    }

    public static void onInput(int key) {
        Player mainClientPlayer = ClientUtils.getMainClientPlayer();
        if (mainClientPlayer != null && !(key >= GLFW.GLFW_KEY_F1 && key <= GLFW.GLFW_KEY_F25) && !MinejagoKeyMappings.MEDITATE.isDown() && key != GLFW.GLFW_KEY_LEFT_SHIFT && key != GLFW.GLFW_KEY_ESCAPE) {
            FocusData focusData = mainClientPlayer.getData(MinejagoAttachmentTypes.FOCUS);
            CompoundTag persistentData = TommyLibServices.ENTITY.getPersistentData(mainClientPlayer);
            if (focusData.isMeditating() && persistentData.getInt("WaitTicks") <= 0) {
                TommyLibServices.NETWORK.sendToServer(new ServerboundStopMeditationPayload(true));
                focusData.stopMeditating();
                persistentData.putInt("WaitTicks", 5);
                TommyLibServices.ENTITY.setPersistentData(mainClientPlayer, persistentData, false);
            }
        }
    }

    public static int renderPowerSymbol(GuiGraphics guiGraphics, Minecraft mc, DeltaTracker deltaTracker, LivingEntity entity, float opacity, boolean inWorldHud) {
        if (mc.level != null && !inWorldHud) {
            Optional<Holder.Reference<Power>> power = mc.level.holder(entity.getData(MinejagoAttachmentTypes.POWER).power());
            if (power.isPresent()) {
                TESClientUtil.prepRenderForTexture(power.orElseThrow().value().getIcon(mc.level.registryAccess().lookupOrThrow(MinejagoRegistries.POWER)));
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
        if (Minejago.Dependencies.RYOAMIC_LIGHTS.isInstalled()) MinejagoDynamicLights.register();
    }

    private static final ModelLayerLocation ENCHANTED_BOAT_LAYER = new ModelLayerLocation(Minejago.modLoc("boat/enchanted"), "main");
    private static final ModelLayerLocation ENCHANTED_CHEST_BOAT_LAYER = new ModelLayerLocation(Minejago.modLoc("chest_boat/enchanted"), "main");

    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ThrownBoneKnifeModel.LAYER_LOCATION, ThrownBoneKnifeModel::createBodyLayer);
        event.registerLayerDefinition(BambooStaffModel.LAYER_LOCATION, BambooStaffModel::createBodyLayer);
        event.registerLayerDefinition(ScytheModel.LAYER_LOCATION, ScytheModel::createBodyLayer);
        event.registerLayerDefinition(PilotsSnapshotTesterHatModel.LAYER_LOCATION, PilotsSnapshotTesterHatModel::createBodyLayer);
        event.registerLayerDefinition(LegacyDevTeamBeardModel.LAYER_LOCATION, LegacyDevTeamBeardModel::createBodyLayer);
        event.registerLayerDefinition(KrunchaModel.LAYER_LOCATION, KrunchaModel::createBodyLayer);
        event.registerLayerDefinition(NuckalModel.LAYER_LOCATION, NuckalModel::createBodyLayer);
        event.registerLayerDefinition(SpinjitzuModel.LAYER_LOCATION, SpinjitzuModel::createBodyLayer);
        event.registerLayerDefinition(ENCHANTED_BOAT_LAYER, BoatModel::createBoatModel);
        event.registerLayerDefinition(ENCHANTED_CHEST_BOAT_LAYER, BoatModel::createChestBoatModel);
    }

    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
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
        event.registerEntityRenderer(MinejagoEntityTypes.CENTER_SPINJITZU_COURSE_ELEMENT.get(), pContext -> new DirectionalSpinjitzuCourseElementRenderer<>(pContext, new SpinningSpinjitzuCourseElementModel<CenterSpinjitzuCourseElement>(MinejagoEntityTypes.CENTER_SPINJITZU_COURSE_ELEMENT.getId(), "spinning")));
        event.registerEntityRenderer(MinejagoEntityTypes.BOUNCING_POLE_SPINJITZU_COURSE_ELEMENT.get(), pContext -> new GeoEntityRenderer<>(pContext, new BouncingPoleSpinjitzuCourseElementModel()));
        event.registerEntityRenderer(MinejagoEntityTypes.ROCKING_POLE_SPINJITZU_COURSE_ELEMENT.get(), pContext -> new DirectionalSpinjitzuCourseElementRenderer<>(pContext, new RockingPoleSpinjitzuCourseElementModel()));
        event.registerEntityRenderer(MinejagoEntityTypes.SPINNING_POLE_SPINJITZU_COURSE_ELEMENT.get(), pContext -> new DirectionalSpinjitzuCourseElementRenderer<>(pContext, new SpinningSpinjitzuCourseElementModel<SpinningPoleSpinjitzuCourseElement>(MinejagoEntityTypes.SPINNING_POLE_SPINJITZU_COURSE_ELEMENT.getId(), "pole")));
        event.registerEntityRenderer(MinejagoEntityTypes.SPINNING_MACES_SPINJITZU_COURSE_ELEMENT.get(), pContext -> new DirectionalSpinjitzuCourseElementRenderer<>(pContext, new SpinningSpinjitzuCourseElementModel<SpinningMacesSpinjitzuCourseElement>(MinejagoEntityTypes.SPINNING_MACES_SPINJITZU_COURSE_ELEMENT.getId(), "structure")));
        event.registerEntityRenderer(MinejagoEntityTypes.SPINNING_DUMMIES_SPINJITZU_COURSE_ELEMENT.get(), pContext -> new DirectionalSpinjitzuCourseElementRenderer<>(pContext, new SpinningSpinjitzuCourseElementModel<SpinningDummiesSpinjitzuCourseElement>(MinejagoEntityTypes.SPINNING_DUMMIES_SPINJITZU_COURSE_ELEMENT.getId(), "structure")));
        event.registerEntityRenderer(MinejagoEntityTypes.SWIRLING_KNIVES_SPINJITZU_COURSE_ELEMENT.get(), pContext -> new DirectionalSpinjitzuCourseElementRenderer<>(pContext, new SpinningSpinjitzuCourseElementModel<SwirlingKnivesSpinjitzuCourseElement>(MinejagoEntityTypes.SWIRLING_KNIVES_SPINJITZU_COURSE_ELEMENT.getId(), "structure")));
        event.registerEntityRenderer(MinejagoEntityTypes.SPINNING_AXES_SPINJITZU_COURSE_ELEMENT.get(), pContext -> new DirectionalSpinjitzuCourseElementRenderer<>(pContext, new SpinningAxesSpinjitzuCourseElementModel()));
        event.registerEntityRenderer(MinejagoBlocks.ENCHANTED_WOOD_SET.boat().get().getEntityType(), context -> new BoatRenderer(context, ENCHANTED_BOAT_LAYER));
        event.registerEntityRenderer(MinejagoBlocks.ENCHANTED_WOOD_SET.chestBoat().get().getEntityType(), context -> new BoatRenderer(context, ENCHANTED_CHEST_BOAT_LAYER));

        event.registerBlockEntityRenderer(MinejagoBlockEntityTypes.DRAGON_HEAD.get(), pContext -> new DragonHeadRenderer());
        event.registerBlockEntityRenderer(MinejagoBlockEntityTypes.BRUSHABLE.get(), BrushableBlockRenderer::new);
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
            if (pTintIndex == 0)
                return ARGB.opaque(pStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor());
            return -1;
        }, MinejagoItems.FILLED_TEACUP.get());
        event.register((itemStack, i) -> {
            BlockState blockstate = ((BlockItem) itemStack.getItem()).getBlock().defaultBlockState();
            return ClientUtils.getMinecraft().getBlockColors().getColor(blockstate, ClientUtils.getLevel(), null, i);
        }, MinejagoBlocks.FOCUS_LEAVES_SET.leaves().get());
    }

    public static void onRegisterBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register(((blockState, blockAndTintGetter, blockPos, i) -> {
            if (blockPos == null || blockAndTintGetter == null)
                return -1;
            if (i == 1 && blockAndTintGetter.getBlockEntity(blockPos) instanceof TeapotBlockEntity teapotBlockEntity && blockState.getValue(TeapotBlock.FILLED)) {
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
                player.addLayer(new SnapshotTesterLayer<>(player, models));
                player.addLayer(new LegacyDevTeamLayer<>(player, models));
            }
        }
    }

    public static void onBuildCreativeTabContent(BuildCreativeModeTabContentsEvent event) {
        event.acceptAll(MinejagoClientEvents.getItemsForTab(event.getTabKey(), event.getParameters().holders()));
    }

    public static void onClientConfigChanged(ModConfigEvent event) {
        if (event.getConfig().getType() == ModConfig.Type.CLIENT && Minecraft.getInstance().player != null) {
            MinejagoClientUtils.refreshVip();
        }
    }

    public static void onRegisterGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(ResourceLocation.withDefaultNamespace("food_level"), Minejago.modLoc("focus"), MinejagoGuis::renderFocusBar);
    }

    public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(MinejagoClientUtils.getBewlr());
    }

    public static void onPostPlayerRender(RenderPlayerEvent.Post event) {
        PlayerRenderState state = event.getRenderState();
        MinejagoPlayerRenderState minejagoState = (MinejagoPlayerRenderState) event.getRenderState();
        SpinjitzuModel<PlayerRenderState> model = new SpinjitzuModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(SpinjitzuModel.LAYER_LOCATION));
        if (minejagoState.minejago$isSpinjitzuActive() && minejagoState.minejago$spinjitzuStartTicks() <= 0) {
            model.setupAnim(state);
            model.getBody().copyFrom(event.getRenderer().getModel().body);
            float scale = state.scale;
            event.getPoseStack().scale(scale, scale, scale);
            int color = minejagoState.minejago$spinjitzuColor();
            model.render(event.getPoseStack(), event.getMultiBufferSource(), (int) state.ageInTicks, event.getPartialTick(), 0xFF000000 | color);
        }
    }

    private static int musicCooldown = 0;

    public static void onSelectMusic(SelectMusicEvent event) {
        Player player = ClientUtils.getMainClientPlayer();
        if (player != null) {
            if (musicCooldown <= 0) {
                CompoundTag data = TommyLibServices.ENTITY.getPersistentData(player);
                BossHealthOverlay bossOverlay = ClientUtils.getMinecraft().gui.getBossOverlay();
                if (bossOverlay.shouldPlayMusic() && skulkinRaids.stream().anyMatch(bossOverlay.events::containsKey)) {
                    event.setMusic(MinejagoMusics.SKULKIN_RAID);
                } else if (data.getBoolean(MinejagoEntityEvents.KEY_ISINMONASTERYOFSPINJITZU)) {
                    event.setMusic(MinejagoMusics.MONASTERY_OF_SPINJITZU);
                } else if (data.getBoolean(MinejagoEntityEvents.KEY_ISINCAVEOFDESPAIR)) {
                    event.setMusic(MinejagoMusics.CAVE_OF_DESPAIR);
                } else if (MinejagoMusics.getAll().stream().anyMatch(music -> Minecraft.getInstance().getMusicManager().isPlayingMusic(music))) {
                    event.setMusic(null);
                    musicCooldown = 20;
                }
            } else {
                musicCooldown--;
            }
        }
    }

    public static void addSkulkinRaid(UUID id) {
        skulkinRaids.add(id);
    }
}
