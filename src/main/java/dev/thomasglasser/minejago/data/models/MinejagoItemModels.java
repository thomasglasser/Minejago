package dev.thomasglasser.minejago.data.models;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MinejagoItemModels extends ItemModelProvider
{
    public MinejagoItemModels(DataGenerator generator, ExistingFileHelper helper)
    {
        super(generator, Minejago.MODID, helper);

    }

    @Override
    protected void registerModels()
    {
        singleTexture(MinejagoItems.BONE_KNIFE.getId().getPath(), mcLoc("item/handheld"), "layer0", modLoc("item/bone_knife"));
        singleTexture(MinejagoItems.SCYTHE_OF_QUAKES.getId().getPath(), mcLoc("item/handheld"), "layer0", modLoc("item/scythe_of_quakes"));
        singleTexture(MinejagoItems.TEACUP.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/teacup"));
        singleTexture(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.getId().getPath(), mcLoc("item/generated"), "layer0", modLoc("item/four_weapons_banner_pattern"));
    }
}
