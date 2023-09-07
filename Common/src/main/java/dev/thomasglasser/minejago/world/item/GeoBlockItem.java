package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.client.model.GeoBlockItemModel;
import dev.thomasglasser.minejago.client.renderer.item.GeoBlockItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GeoBlockItem extends BlockItem implements FabricGeoItem, ModeledItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final GeoBlockItemModel model;

    private GeoBlockItemRenderer bewlr;

    public GeoBlockItem(Block block, Properties properties, GeoBlockItemModel model) {
        super(block, properties);
        this.model = model;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public BlockEntityWithoutLevelRenderer getBEWLR() {
        if (bewlr == null && model != null) bewlr = new GeoBlockItemRenderer(model);
        return bewlr;
    }
}
