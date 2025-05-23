package dev.thomasglasser.minejago.data.advancements;

import dev.thomasglasser.minejago.data.advancements.packs.MinejagoAdventureAdvancements;
import dev.thomasglasser.minejago.data.advancements.packs.MinejagoStoryAdvancements;
import dev.thomasglasser.tommylib.api.data.advancements.ExtendedAdvancementProvider;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MinejagoAdvancementProvider extends ExtendedAdvancementProvider {
    public MinejagoAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper, LanguageProvider enUs) {
        super(output, registries, existingFileHelper, ReferenceOpenHashSet.of(
                new MinejagoStoryAdvancements(enUs),
                new MinejagoAdventureAdvancements(enUs)));
    }
}
