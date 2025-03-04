package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.tags.MinejagoPowerTags;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.tommylib.api.data.tags.ExtendedTagsProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class PowerTagsProvider extends ExtendedTagsProvider<Power> {
    public PowerTagsProvider(PackOutput pOutput, String modId, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, MinejagoRegistries.POWER, pProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(MinejagoPowerTags.CAN_USE_SCYTHE_OF_QUAKES)
                .add(MinejagoPowers.EARTH);

        tag(MinejagoPowerTags.CAN_USE_SHURIKEN_OF_ICE)
                .add(MinejagoPowers.ICE);
    }
}
