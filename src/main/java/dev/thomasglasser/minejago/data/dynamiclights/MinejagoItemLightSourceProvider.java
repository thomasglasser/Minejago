package dev.thomasglasser.minejago.data.dynamiclights;

import dev.lambdaurora.lambdynlights.api.data.ItemLightSourceProvider;
import dev.lambdaurora.lambdynlights.api.item.ItemLuminance;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public class MinejagoItemLightSourceProvider extends ItemLightSourceProvider {
    public MinejagoItemLightSourceProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryProvider) {
        super(packOutput, registryProvider, Minejago.MOD_ID);
    }

    @Override
    protected void generate(HolderLookup.Provider provider) {
        add(MinejagoItemTags.GOLDEN_WEAPONS, ItemLuminance.of(10), false);
        add(MinejagoBlocks.EARTH_DRAGON_HEAD, ItemLuminance.BlockSelf.INSTANCE, false);
    }

    protected void add(ItemLike item, ItemLuminance luminance, boolean waterSensitive) {
        add(item.asItem().builtInRegistryHolder().key().location().getPath(), luminance, waterSensitive, item);
    }

    protected void add(TagKey<Item> tag, ItemLuminance luminance, boolean waterSensitive) {
        add(tag.location().getPath(), tag, luminance, waterSensitive);
    }
}
