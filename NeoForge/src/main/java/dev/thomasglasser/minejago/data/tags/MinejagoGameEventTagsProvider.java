package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.gameevent.MinejagoGameEvents;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.GameEventTagsProvider;
import net.minecraft.tags.GameEventTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MinejagoGameEventTagsProvider extends GameEventTagsProvider {
    public MinejagoGameEventTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(GameEventTags.VIBRATIONS)
                .add(MinejagoGameEvents.SPINJITZU.getResourceKey());
        tag(GameEventTags.WARDEN_CAN_LISTEN)
                .add(MinejagoGameEvents.SPINJITZU.getResourceKey());
    }
}
