package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoBiomeTags;
import dev.thomasglasser.tommylib.api.data.tags.ExtendedTagsProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class MinejagoBiomeTagsProvider extends ExtendedTagsProvider<Biome> {
    public MinejagoBiomeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, Registries.BIOME, pProvider, Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(MinejagoBiomeTags.HAS_FOUR_WEAPONS)
                .add(Biomes.PLAINS)
                .add(Biomes.MEADOW);
        tag(MinejagoBiomeTags.HAS_CAVE_OF_DESPAIR)
                .addTag(BiomeTags.IS_BADLANDS);
        tag(MinejagoBiomeTags.HAS_FOCUS_TREES)
                .add(Biomes.MEADOW);
        tag(MinejagoBiomeTags.HAS_NINJAGO_CITY)
                .add(Biomes.PLAINS)
                .add(Biomes.SAVANNA);
        tag(MinejagoBiomeTags.HAS_MONASTERY_OF_SPINJITZU)
                .add(Biomes.SNOWY_SLOPES)
                .add(Biomes.FROZEN_PEAKS)
                .add(Biomes.JAGGED_PEAKS)
                .add(Biomes.STONY_PEAKS);
    }
}
