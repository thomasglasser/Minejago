package com.thomasglasser.minejago.generated;

import com.thomasglasser.minejago.MinejagoMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModBlockTags extends BlockTagsProvider
{

    public ModBlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, MinejagoMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {

    }

    @Override
    public String getName() {
        return "Minejago Tags";
    }
}
