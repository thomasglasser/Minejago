package dev.thomasglasser.minejago.data.advancements;

import dev.thomasglasser.minejago.data.advancements.packs.MinejagoAdventureAdvancements;
import dev.thomasglasser.minejago.data.advancements.packs.MinejagoHusbandryAdvancements;
import dev.thomasglasser.minejago.data.advancements.packs.MinejagoStoryAdvancements;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MinejagoAdvancementProvider extends ForgeAdvancementProvider {
    /**
     * Constructs an advancement provider using the generators to write the
     * advancements to a file.
     *
     * @param output             the target directory of the data generator
     * @param registries         a future of a lookup for registries and their objects
     * @param existingFileHelper a helper used to find whether a file exists
     */
    public MinejagoAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper, LanguageProvider enUs) {
        super(output, registries, existingFileHelper, List.of(
                new MinejagoStoryAdvancements(enUs),
                new MinejagoAdventureAdvancements(enUs),
                new MinejagoHusbandryAdvancements(enUs)
        ));
    }
}
