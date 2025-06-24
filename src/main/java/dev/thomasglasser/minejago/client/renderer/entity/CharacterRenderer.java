package dev.thomasglasser.minejago.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.thomasglasser.minejago.client.model.CharacterModel;
import dev.thomasglasser.minejago.client.model.SpinjitzuModel;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.character.Character;
import dev.thomasglasser.tommylib.api.client.renderer.entity.layers.geo.ElytraAndItemArmorGeoLayer;
import dev.thomasglasser.tommylib.api.world.item.armor.GeoArmorItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class CharacterRenderer<T extends Character> extends GeoEntityRenderer<T> {
    private static final String BODY = "body";
    private static final String HEAD = "head";
    private static final String LEFT_HAND = "left_hand";
    private static final String RIGHT_HAND = "right_hand";
    private static final String LEFT_BOOT = "left_boot";
    private static final String RIGHT_BOOT = "right_boot";
    private static final String LEFT_ARMOR_LEG = "outer_left_leg";
    private static final String RIGHT_ARMOR_LEG = "outer_right_leg";
    private static final String CHESTPLATE = "outer_body";
    private static final String RIGHT_SLEEVE = "outer_right_arm";
    private static final String LEFT_SLEEVE = "outer_left_arm";
    private static final String HELMET = "outer_head";

    protected ItemStack mainHandItem;
    protected ItemStack offhandItem;

    private SpinjitzuModel<T> spinjitzuModel;

    public CharacterRenderer(EntityRendererProvider.Context context, CharacterModel<T> model) {
        super(context, model);

        // Add some armor rendering
        addRenderLayer(new ElytraAndItemArmorGeoLayer<>(this) {
            @Nullable
            @Override
            protected ItemStack getArmorItemForBone(GeoBone bone, T animatable) {
                // Return the items relevant to the bones being rendered for additional rendering
                return switch (bone.getName()) {
                    case LEFT_BOOT, RIGHT_BOOT -> this.bootsStack;
                    case LEFT_ARMOR_LEG, RIGHT_ARMOR_LEG, BODY -> this.leggingsStack;
                    case CHESTPLATE, RIGHT_SLEEVE, LEFT_SLEEVE -> this.chestplateStack;
                    case HELMET -> this.helmetStack;
                    default -> null;
                };
            }

            // Return the equipment slot relevant to the bone we're using
            @Override
            protected EquipmentSlot getEquipmentSlotForBone(GeoBone bone, ItemStack stack, T animatable) {
                return switch (bone.getName()) {
                    case LEFT_BOOT, RIGHT_BOOT -> EquipmentSlot.FEET;
                    case LEFT_ARMOR_LEG, RIGHT_ARMOR_LEG, BODY -> EquipmentSlot.LEGS;
                    case RIGHT_SLEEVE -> !animatable.isLeftHanded() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                    case LEFT_SLEEVE -> !animatable.isLeftHanded() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                    case CHESTPLATE -> EquipmentSlot.CHEST;
                    case HELMET -> EquipmentSlot.HEAD;
                    default -> super.getEquipmentSlotForBone(bone, stack, animatable);
                };
            }

            // Return the ModelPart responsible for the armor pieces we want to render
            @Override
            protected ModelPart getModelPartForBone(GeoBone bone, EquipmentSlot slot, ItemStack stack, T animatable, HumanoidModel<?> baseModel) {
                return switch (bone.getName()) {
                    case LEFT_BOOT, LEFT_ARMOR_LEG -> baseModel.leftLeg;
                    case RIGHT_BOOT, RIGHT_ARMOR_LEG -> baseModel.rightLeg;
                    case RIGHT_SLEEVE -> baseModel.rightArm;
                    case LEFT_SLEEVE -> baseModel.leftArm;
                    case CHESTPLATE, BODY -> baseModel.body;
                    case HELMET -> baseModel.head;
                    default -> super.getModelPartForBone(bone, slot, stack, animatable, baseModel);
                };
            }
        });

        // Add some held item rendering
        addRenderLayer(new BlockAndItemGeoLayer<>(this, (bone, character) -> switch (bone.getName()) {
            case LEFT_HAND -> animatable.isLeftHanded() ? mainHandItem : offhandItem;
            case RIGHT_HAND -> animatable.isLeftHanded() ? offhandItem : mainHandItem;
            default -> null;
        }, (bone, character) -> null) {
            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, T animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                poseStack.translate(0, 0.1D, -0.1D);
                poseStack.mulPose(Axis.XP.rotationDegrees(-80.0F));
                super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
            }

            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, T animatable) {
                return switch (bone.getName()) {
                    case LEFT_HAND -> ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
                    case RIGHT_HAND -> ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                    case HEAD -> ItemDisplayContext.HEAD;
                    default -> ItemDisplayContext.NONE;
                };
            }
        });
    }

    public CharacterRenderer(EntityRendererProvider.Context context, ResourceLocation entityId, boolean slim) {
        this(context, new CharacterModel<>(entityId, slim));
    }

    public CharacterRenderer(EntityRendererProvider.Context context, ResourceLocation entityId) {
        this(context, entityId, false);
    }

    @Override
    public void preRender(PoseStack poseStack, T animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);

        this.mainHandItem = animatable.getMainHandItem();
        this.offhandItem = animatable.getOffhandItem();
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        setModelProperties(entity);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        if (entity.isDoingSpinjitzu()) {
            if (spinjitzuModel == null) {
                spinjitzuModel = new SpinjitzuModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(SpinjitzuModel.LAYER_LOCATION));
            }
            spinjitzuModel.setupAnim(entity, 0, 0, entity.tickCount + partialTick, 0, 0);
            copyFromBone(model.getBone(BODY).orElseThrow(), spinjitzuModel.getBody());
            spinjitzuModel.render(poseStack, bufferSource, entity.tickCount, partialTick, entity.level().holderOrThrow(entity.getData(MinejagoAttachmentTypes.ELEMENT).element()).value().color().getValue());
        }
    }

    private void copyFromBone(GeoBone bone, ModelPart part) {
        part.x = bone.getPosX();
        part.y = bone.getPosY();
        part.z = bone.getPosZ();
        part.xRot = bone.getRotX();
        part.yRot = bone.getRotY();
        part.zRot = bone.getRotZ();
        part.xScale = bone.getScaleX();
        part.yScale = bone.getScaleY();
        part.zScale = bone.getScaleZ();
    }

    protected void setModelProperties(T entity) {
        CharacterModel<T> characterModel = (CharacterModel<T>) model;
        if (!model.getAnimationProcessor().getRegisteredBones().isEmpty()) {
            if (entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof GeoArmorItem geoArmorItem && geoArmorItem.isSkintight()) {
                characterModel.getBone(LEFT_SLEEVE).ifPresent(bone -> bone.setHidden(true));
                characterModel.getBone(RIGHT_SLEEVE).ifPresent(bone -> bone.setHidden(true));
                characterModel.getBone(CHESTPLATE).ifPresent(bone -> bone.setHidden(true));
            } else {
                characterModel.getBone(LEFT_SLEEVE).ifPresent(bone -> bone.setHidden(false));
                characterModel.getBone(RIGHT_SLEEVE).ifPresent(bone -> bone.setHidden(false));
                characterModel.getBone(CHESTPLATE).ifPresent(bone -> bone.setHidden(false));
            }
            characterModel.getBone(HELMET).orElseThrow().setHidden(entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof GeoArmorItem geoArmorItem && geoArmorItem.isSkintight());
            if (entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof GeoArmorItem iGeoArmorBoots && iGeoArmorBoots.isSkintight() || entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof GeoArmorItem iGeoArmorLeggings && iGeoArmorLeggings.isSkintight()) {
                characterModel.getBone(RIGHT_ARMOR_LEG).ifPresent(bone -> bone.setHidden(true));
                characterModel.getBone(LEFT_ARMOR_LEG).ifPresent(bone -> bone.setHidden(true));
            } else {
                characterModel.getBone(RIGHT_ARMOR_LEG).ifPresent(bone -> bone.setHidden(false));
                characterModel.getBone(LEFT_ARMOR_LEG).ifPresent(bone -> bone.setHidden(false));
            }
        }
    }
}
