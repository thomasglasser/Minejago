package com.thomasglasser.minejago.generated;

import com.thomasglasser.minejago.MinejagoMod;
import com.thomasglasser.minejago.server.item.MinejagoItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModels extends ItemModelProvider
{
    public ModItemModels(DataGenerator generator, ExistingFileHelper helper)
    {
        super(generator, MinejagoMod.MODID, helper);

    }

    @Override
    protected void registerModels()
    {
        singleTexture(MinejagoItems.BONE_KNIFE.getId().getPath(), mcLoc("item/handheld"), "layer0", modLoc("item/bone_knife"));
        singleTexture(MinejagoItems.SCYTHE_OF_QUAKES.getId().getPath(), mcLoc("item/handheld"), "layer0", modLoc("item/scythe_of_quakes"));
    }
}
