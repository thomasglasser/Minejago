package dev.thomasglasser.minejago.client.renderer.armor;

import dev.thomasglasser.minejago.client.model.armor.BlackGiModel;
import dev.thomasglasser.minejago.world.item.BlackGiItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class BlackGiRenderer extends GeoArmorRenderer<BlackGiItem> {

    public BlackGiRenderer() {
        super(new BlackGiModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.leftArmBone = "armorLeftArm";
        this.rightArmBone = "armorRightArm";
        this.leftLegBone = "armorLeftLeg";
        this.rightLegBone = "armorRightLeg";
        this.leftBootBone = "armorLeftBoot";
        this.rightBootBone = "armorRightBoot";
    }
}
