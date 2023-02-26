package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PowerTagsProvider extends TagsProvider<Power> {
    public PowerTagsProvider(PackOutput pOutput, String modid, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, MinejagoRegistries.POWER,
                CompletableFuture.supplyAsync(() -> {
                    final RegistryAccess.Frozen access = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
                    final RegistrySetBuilder builder = new RegistrySetBuilder();
                    MinejagoPowers.POWERS.addToSet(builder);
                    return builder.build(access);
                }, Util.backgroundExecutor()),
                modid, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(MinejagoPowerTags.EARTH)
                .add(MinejagoPowers.EARTH);
    }
}
