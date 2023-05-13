package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MinejagoEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public MinejagoEntityTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(MinejagoEntityTypeTags.SKULKINS)
                .add(MinejagoEntityTypes.SKULKIN.get())
                .add(MinejagoEntityTypes.NUCKAL.get())
                .add(MinejagoEntityTypes.KRUNCHA.get())
                .add(MinejagoEntityTypes.SKULKIN_HORSE.get());

        tag(EntityTypeTags.SKELETONS)
                .addTag(MinejagoEntityTypeTags.SKULKINS);
    }
}
