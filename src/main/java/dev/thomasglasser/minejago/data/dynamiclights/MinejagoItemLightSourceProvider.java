package dev.thomasglasser.minejago.data.dynamiclights;

import dev.lambdaurora.lambdynlights.api.data.ItemLightSourceDataProvider;
import dev.lambdaurora.lambdynlights.api.item.ItemLuminance;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

public class MinejagoItemLightSourceProvider extends ItemLightSourceDataProvider {
    public MinejagoItemLightSourceProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryProvider) {
        super(packOutput, registryProvider, Minejago.MOD_ID);
    }

    @Override
    protected void generate(Context context) {
        context.add(MinejagoItemTags.GOLDEN_WEAPONS, ItemLuminance.of(10), false);
        context.add(MinejagoItemTags.GOLDEN_WEAPON_HOLDERS, ItemLuminance.of(10), false);
    }
}
