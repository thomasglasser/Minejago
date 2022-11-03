package dev.thomasglasser.minejago.data.models;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MinejagoItemModels extends ItemModelProvider
{
    public MinejagoItemModels(DataGenerator generator, ExistingFileHelper helper)
    {
        super(generator, Minejago.MOD_ID, helper);

    }

    @Override
    protected void registerModels()
    {
        singleTexture(MinejagoItems.BONE_KNIFE.getId().getPath(), mcLoc("item/handheld"), "layer0", modLoc("item/bone_knife"));
        singleTexture(MinejagoItems.IRON_SHURIKEN.getId().getPath(), mcLoc("item/handheld"), "layer0", modLoc("item/iron_shuriken"));
        singleTexture(MinejagoItems.SKELETAL_CHESTPLATE.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/skeletal_chestplate"));
        /*TODO: Get new SOC texture*/ //singleTexture(MinejagoItems.SCYTHE_OF_QUAKES.getId().getPath(), mcLoc("item/handheld"), "layer0", modLoc("item/scythe_of_quakes"));
        singleTexture(MinejagoItems.TEACUP.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/teacup"));
        singleTexture(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/four_weapons_banner_pattern"));
        singleTexture(MinejagoBlocks.TEAPOT.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/teapot"));
    }
}
