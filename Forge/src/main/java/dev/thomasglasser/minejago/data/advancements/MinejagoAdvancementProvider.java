package dev.thomasglasser.minejago.data.advancements;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.advancements.packs.MinejagoAdventureAdvancements;
import dev.thomasglasser.minejago.data.advancements.packs.MinejagoStoryAdvancements;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

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
    public MinejagoAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(
                new MinejagoStoryAdvancements(),
                new MinejagoAdventureAdvancements()
        ));
    }

    public static Component title(String category, String path)
    {
        return Component.translatable("advancement." + Minejago.MOD_ID + "." + category + "." + path + ".title");
    }

    public static Component desc(String category, String path)
    {
        return Component.translatable("advancement." + Minejago.MOD_ID + "." + category + "." + path + ".desc");
    }
}
