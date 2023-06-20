package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MinejagoBiomeTagsProvider extends BiomeTagsProvider
{
    public MinejagoBiomeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(MinejagoBiomeTags.HAS_FOUR_WEAPONS)
                .add(Biomes.PLAINS)
                .add(Biomes.MEADOW);
    }
}
