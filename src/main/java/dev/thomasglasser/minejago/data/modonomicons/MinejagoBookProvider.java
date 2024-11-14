package dev.thomasglasser.minejago.data.modonomicons;

import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import dev.thomasglasser.tommylib.api.registration.DeferredItem;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class MinejagoBookProvider extends BookProvider {
    public MinejagoBookProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, BiConsumer<String, String> lang) {
        super(packOutput, registries, Minejago.MOD_ID, List.of(
                new WikiBookSubProvider(lang)));
    }

    public static ResourceLocation itemLoc(DeferredItem<?> item) {
        return item.getId().withPrefix("textures/item/").withSuffix(".png");
    }

    public static ResourceLocation itemLoc(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).withPrefix("textures/item/").withSuffix(".png");
    }

    public static ResourceLocation blockLoc(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).withPrefix("textures/block/").withSuffix(".png");
    }
}
