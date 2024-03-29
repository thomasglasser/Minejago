package dev.thomasglasser.minejago.client.renderer.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.armor.TrainingGiItem;
import dev.thomasglasser.tommylib.api.client.model.NbtArmorGeoModel;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class TrainingGiRenderer extends GeoArmorRenderer<TrainingGiItem> {
    private final NbtArmorGeoModel<?> model;

    public TrainingGiRenderer() {
        super(new NbtArmorGeoModel<>(Minejago.modLoc("armor/training_gi"), "Power"));
        model = (NbtArmorGeoModel<?>) this.getGeoModel();
    }

    @Override
    public ResourceLocation getTextureLocation(TrainingGiItem animatable) {
        return new ResourceLocation(model.getLocation().getNamespace(), "textures/models/armor/training_gi_" + model.getLocation().getPath() + ".png");
    }
}
