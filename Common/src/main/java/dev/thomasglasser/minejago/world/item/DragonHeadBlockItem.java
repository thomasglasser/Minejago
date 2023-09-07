package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.client.model.DragonHeadBlockItemModel;
import dev.thomasglasser.minejago.client.model.DragonHeadModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

public class DragonHeadBlockItem extends GeoBlockItem
{
    public DragonHeadBlockItem(Block block, Properties properties, DragonHeadModel model, String dragon) {
        super(block, properties, new DragonHeadBlockItemModel(model, dragon, BuiltInRegistries.BLOCK.getKey(block).getPath()));
    }
}
