package dev.thomasglasser.minejago.client.renderer.entity;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.kosmx.playerAnim.api.TransformType;
import dev.kosmx.playerAnim.core.util.Vec3f;
import dev.kosmx.playerAnim.impl.IAnimatedPlayer;
import dev.kosmx.playerAnim.impl.animation.AnimationApplier;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.shadow.AbstractShadowCopy;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

public class AbstractShadowCopyRenderer<T extends AbstractShadowCopy> extends MobRenderer<T, EntityModel<T>> {
    private static final Map<ResourceLocation, ResourceLocation> SHADOW_TEXTURES = new HashMap<>();

    @Nullable
    protected LivingEntityRenderer ownerRenderer;
    @Nullable
    protected LivingEntity owner;

    public AbstractShadowCopyRenderer(EntityRendererProvider.Context context) {
        super(context, null, 0.0F);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        owner = entity.getOwner();
        if (owner != null) {
            ownerRenderer = (LivingEntityRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(owner);
            EntityModel model = ownerRenderer.getModel();
            layers = ownerRenderer.layers;
            EnumMap<EquipmentSlot, ItemStack> items = Util.make(new EnumMap<>(EquipmentSlot.class), map -> {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    map.put(slot, owner.getItemBySlot(slot));
                    owner.setItemSlot(slot, entity.getItemBySlot(slot));
                }
            });
            if (net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(new net.neoforged.neoforge.client.event.RenderLivingEvent.Pre<>(entity, this, partialTicks, poseStack, buffer, packedLight)).isCanceled()) return;
            poseStack.pushPose();
            model.young = entity.isBaby();
            float f = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
            float f1 = Mth.rotLerp(partialTicks, entity.yHeadRotO, entity.yHeadRot);
            float f2 = f1 - f;

            float f6 = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());
            if (isEntityUpsideDown(owner)) {
                f6 *= -1.0F;
                f2 *= -1.0F;
            }

            f2 = Mth.wrapDegrees(f2);

            float f8 = entity.getScale();
            poseStack.scale(f8, f8, f8);
            float f9 = this.getBob(entity, partialTicks);
            this.setupRotations(entity, poseStack, f9, f, partialTicks, f8);
            poseStack.scale(-1.0F, -1.0F, 1.0F);
            this.scale(entity, poseStack, partialTicks);
            poseStack.translate(0.0F, -1.501F, 0.0F);
            float f4 = 0.0F;
            float f5 = 0.0F;
            if (entity.isAlive()) {
                f4 = entity.walkAnimation.speed(partialTicks);
                f5 = entity.walkAnimation.position(partialTicks);
                if (entity.isBaby()) {
                    f5 *= 3.0F;
                }

                if (f4 > 1.0F) {
                    f4 = 1.0F;
                }
            }

            model.prepareMobModel(owner, f5, f4, partialTicks);
            model.setupAnim(owner, f5, f4, f9, f2, f6);
            Minecraft minecraft = Minecraft.getInstance();
            boolean flag = this.isBodyVisible(entity);
            boolean flag1 = !flag && !entity.isInvisibleTo(minecraft.player);
            VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(entity)));
            int i = getOverlayCoords(entity, this.getWhiteOverlayProgress(entity, partialTicks));
            model.renderToBuffer(poseStack, vertexconsumer, packedLight, i, flag1 ? 654311423 : -1);

            if (!entity.isSpectator()) {
                for (RenderLayer renderlayer : this.layers) {
                    renderlayer.render(poseStack, buffer, packedLight, owner, f5, f4, partialTicks, f9, f2, f6);
                }
            }

            poseStack.popPose();
            // Neo: Post the RenderNameTagEvent and conditionally wrap #renderNameTag based on the result.
            var event = new net.neoforged.neoforge.client.event.RenderNameTagEvent(entity, entity.getDisplayName(), this, poseStack, buffer, packedLight, partialTicks);
            net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(event);
            if (event.canRender().isTrue() || event.canRender().isDefault() && this.shouldShowName(entity)) {
                this.renderNameTag(entity, event.getContent(), poseStack, buffer, packedLight, partialTicks);
            }
            net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(new net.neoforged.neoforge.client.event.RenderLivingEvent.Post<>(entity, this, partialTicks, poseStack, buffer, packedLight));
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                owner.setItemSlot(slot, items.get(slot));
            }
        }
    }

    protected boolean hasOwner() {
        return owner != null && ownerRenderer != null;
    }

    @Override
    public float getShadowRadius(T entity) {
        return hasOwner() ? ownerRenderer.shadowRadius * entity.getScale() * (owner instanceof Mob mob ? mob.getAgeScale() : 1) : super.getShadowRadius(entity);
    }

    @Override
    public boolean isBodyVisible(T livingEntity) {
        return hasOwner() && ownerRenderer.isBodyVisible(owner);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        if (hasOwner())
            return getShadowTexture(ownerRenderer.getTextureLocation(owner));
        return Minejago.modLoc("");
    }

    @Override
    protected void setupRotations(T entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
        if (owner instanceof IAnimatedPlayer player) {
            AnimationApplier animationPlayer = player.playerAnimator_getAnimation();
            animationPlayer.setTickDelta(partialTick);
            if (animationPlayer.isActive()) {
                //These are additive properties
                Vec3f vec3e = animationPlayer.get3DTransform("body", TransformType.SCALE,
                        new Vec3f(ModelPart.DEFAULT_SCALE, ModelPart.DEFAULT_SCALE, ModelPart.DEFAULT_SCALE));
                poseStack.scale(vec3e.getX(), vec3e.getY(), vec3e.getZ());
                Vec3f vec3d = animationPlayer.get3DTransform("body", TransformType.POSITION, Vec3f.ZERO);
                poseStack.translate(vec3d.getX(), vec3d.getY() + 0.7, vec3d.getZ());
                Vec3f vec3f = animationPlayer.get3DTransform("body", TransformType.ROTATION, Vec3f.ZERO);
                poseStack.mulPose(Axis.ZP.rotation(vec3f.getZ()));    //roll
                poseStack.mulPose(Axis.YP.rotation(vec3f.getY()));    //pitch
                poseStack.mulPose(Axis.XP.rotation(vec3f.getX()));    //yaw
                poseStack.translate(0, -0.7d, 0);
            }
        }
        super.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);
    }

    public static ResourceLocation getShadowTexture(ResourceLocation original) {
        if (SHADOW_TEXTURES.containsKey(original)) {
            return SHADOW_TEXTURES.get(original);
        } else if (SHADOW_TEXTURES.containsValue(original)) {
            return original;
        } else {
            ResourceLocation result = ResourceLocation.fromNamespaceAndPath(original.getNamespace(), original.getPath() + "_shadow");
            try (AbstractTexture texture = Minecraft.getInstance().getTextureManager().getTexture(original)) {
                NativeImage image;
                if (texture instanceof SimpleTexture simpleTexture) {
                    image = simpleTexture.getTextureImage(Minecraft.getInstance().getResourceManager()).getImage();
                } else if (texture instanceof DynamicTexture dynamicTexture) {
                    image = dynamicTexture.getPixels();
                } else {
                    return original;
                }
                if (image != null) {
                    List<Vector2i> nonEmpty = new ArrayList<>();

                    for (int x = 0; x < image.getWidth(); x++) {
                        for (int y = 0; y < image.getHeight(); y++) {
                            if (image.getPixelRGBA(x, y) != 0x00000000)
                                nonEmpty.add(new Vector2i(x, y));
                        }
                    }

                    for (Vector2i pixel : nonEmpty) {
                        image.setPixelRGBA(pixel.x, pixel.y, 0xBF1B1B1B);
                    }
                    Minecraft.getInstance().getTextureManager().register(result, new DynamicTexture(image));
                    SHADOW_TEXTURES.put(original, result);
                    return result;
                }
                return original;
            } catch (Exception e) {
                return original;
            }
        }
    }

    public static void clearShadowTextures() {
        SHADOW_TEXTURES.clear();
    }
}
