package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.tommylib.api.client.renderer.entity.ThrownSwordRenderer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ScytheModel extends Model {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Minejago.modLoc("scythe"), "in_hand");
    public static final ResourceLocation TEXTURE = ThrownSwordRenderer.TEXTURE.apply(MinejagoItems.SCYTHE_OF_QUAKES.getId());

    public ScytheModel(ModelPart root) {
        super(root, RenderType::entityCutout);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();

        meshdefinition.getRoot().addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -25.0F, -0.5F, 1.0F, 25.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 5).addBox(-5.5F, -28.0F, -1.0F, 7.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 10).addBox(-6.5F, -27.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 0).addBox(-7.5F, -29.0F, 0.0F, 9.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }
}
