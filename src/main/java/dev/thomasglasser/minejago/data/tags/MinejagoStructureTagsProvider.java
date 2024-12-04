package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoStructureTags;
import dev.thomasglasser.minejago.world.level.levelgen.structure.MinejagoStructures;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class MinejagoStructureTagsProvider extends StructureTagsProvider {
    public MinejagoStructureTagsProvider(PackOutput p_256522_, CompletableFuture<HolderLookup.Provider> p_256661_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256522_, p_256661_, Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(MinejagoStructureTags.FOUR_WEAPONS)
                .add(MinejagoStructures.FOUR_WEAPONS);

        tag(MinejagoStructureTags.NINJAGO_CITY)
                .add(MinejagoStructures.NINJAGO_CITY);

        tag(MinejagoStructureTags.CAVE_OF_DESPAIR)
                .add(MinejagoStructures.CAVE_OF_DESPAIR);

        // TODO: Other structures
        tag(MinejagoStructureTags.ICE_TEMPLE);
        tag(MinejagoStructureTags.FLOATING_RUINS);
        tag(MinejagoStructureTags.FIRE_TEMPLE);

        tag(MinejagoStructureTags.MONASTERY_OF_SPINJITZU)
                .add(MinejagoStructures.MONASTERY_OF_SPINJITZU);

        tag(MinejagoStructureTags.HAS_GOLDEN_WEAPON)
                .addTag(MinejagoStructureTags.CAVE_OF_DESPAIR)
                .addTag(MinejagoStructureTags.ICE_TEMPLE)
                .addTag(MinejagoStructureTags.FLOATING_RUINS)
                .addTag(MinejagoStructureTags.FIRE_TEMPLE);
    }
}
