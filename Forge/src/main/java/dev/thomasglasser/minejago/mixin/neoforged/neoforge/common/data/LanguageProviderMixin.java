package dev.thomasglasser.minejago.mixin.neoforged.neoforge.common.data;

import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(LanguageProvider.class)
public abstract class LanguageProviderMixin implements ModonomiconLanguageProvider
{
	@Shadow @Final private String locale;

	@Shadow @Final private Map<String, String> data;

	@Override
	public String locale()
	{
		return locale;
	}

	@Override
	public Map<String, String> data()
	{
		return data;
	}
}
