package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.levelgen.structure.MinejagoStructures;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MinejagoStructureTagsProvider extends StructureTagsProvider {
    public MinejagoStructureTagsProvider(PackOutput p_256522_, CompletableFuture<HolderLookup.Provider> p_256661_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256522_, p_256661_, Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(MinejagoStructureTags.CAVE_OF_DESPAIR)
                .add(MinejagoStructures.CAVE_OF_DESPAIR);

        tag(MinejagoStructureTags.GOLDEN_WEAPONS_HOLDER)
                .addTag(MinejagoStructureTags.CAVE_OF_DESPAIR);
    }
}
