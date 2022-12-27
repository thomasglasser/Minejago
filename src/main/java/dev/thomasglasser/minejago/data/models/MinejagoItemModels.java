package dev.thomasglasser.minejago.data.models;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MinejagoItemModels extends ItemModelProvider
{
    public MinejagoItemModels(PackOutput output, ExistingFileHelper helper)
    {
        super(output, Minejago.MOD_ID, helper);

    }

    @Override
    protected void registerModels()
    {
        singleTexture(MinejagoItems.BONE_KNIFE.getId().getPath(), mcLoc("item/handheld"), "layer0", modLoc("item/bone_knife"));
        singleTexture(MinejagoItems.IRON_SHURIKEN.getId().getPath(), mcLoc("item/handheld"), "layer0", modLoc("item/iron_shuriken"));
        singleTexture(MinejagoItems.RED_SKELETAL_CHESTPLATE.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/skeletal_chestplate_red"));
        singleTexture(MinejagoItems.BLUE_SKELETAL_CHESTPLATE.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/skeletal_chestplate_blue"));
        singleTexture(MinejagoItems.WHITE_SKELETAL_CHESTPLATE.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/skeletal_chestplate_white"));
        singleTexture(MinejagoItems.BLACK_SKELETAL_CHESTPLATE.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/skeletal_chestplate_black"));
        singleTexture(MinejagoItems.TEACUP.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/teacup"));
        singleTexture(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/four_weapons_banner_pattern"));
        singleTexture(MinejagoBlocks.TEAPOT.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/teapot"));
        singleTexture(MinejagoItems.BLACK_GI_HELMET.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/black_gi_helmet"));
        singleTexture(MinejagoItems.BLACK_GI_CHESTPLATE.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/black_gi_chestplate"));
        singleTexture(MinejagoItems.BLACK_GI_LEGGINGS.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/black_gi_leggings"));
        singleTexture(MinejagoItems.BLACK_GI_BOOTS.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/black_gi_boots"));
        singleTexture(MinejagoItems.IRON_KATANA.getId().getPath(), mcLoc("item/handheld"), "layer0", modLoc("item/iron_katana"));

        withExistingParent(MinejagoItems.WU_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(MinejagoItems.KAI_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(MinejagoItems.NYA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(MinejagoItems.UNDERWORLD_SKELETON_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }
}
