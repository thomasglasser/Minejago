package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoBiomeTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class MinejagoBiomeTagsProvider extends BiomeTagsProvider {
    public MinejagoBiomeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(MinejagoBiomeTags.HAS_FOUR_WEAPONS)
                .add(Biomes.PLAINS)
                .add(Biomes.MEADOW);
        tag(MinejagoBiomeTags.HAS_CAVE_OF_DESPAIR)
                .addTag(BiomeTags.IS_BADLANDS);
        tag(MinejagoBiomeTags.WITHOUT_SKULKIN_PATROL_SPAWNS)
                .add(Biomes.MUSHROOM_FIELDS);
        tag(MinejagoBiomeTags.HAS_FOCUS_TREES)
                .add(Biomes.MEADOW);
        tag(MinejagoBiomeTags.HAS_NINJAGO_CITY)
                .add(Biomes.PLAINS)
                .add(Biomes.SAVANNA);
    }
}
