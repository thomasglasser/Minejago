package dev.thomasglasser.minejago.data.modonomicons;

import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.modonomicons.wiki.WikiBookSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class MinejagoBookProvider extends BookProvider
{
	public MinejagoBookProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries, BiConsumer<String, String> lang)
	{
		super(packOutput, registries, Minejago.MOD_ID, List.of(
			new WikiBookSubProvider(lang)
		));
	}
}
