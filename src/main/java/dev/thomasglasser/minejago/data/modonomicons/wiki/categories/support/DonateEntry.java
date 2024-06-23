package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.support;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.world.item.Items;

public class DonateEntry extends IndexModeEntryProvider
{
	public static final String ID = "donate";

	public DonateEntry(CategoryProviderBase parent)
	{
		super(parent);
	}

	@Override
	protected void generatePages()
	{
		page("icon", () -> BookImagePageModel.create()
			.withImages(Minejago.modLoc("textures/modonomicon/wiki/support/donate/buy_me_a_coffee.png")));

		page("link", () -> BookTextPageModel.create()
				.withTitle(context().pageTitle())
				.withText(context().pageText()));

		add(context().pageTitle(), "Buy Me a Coffee");
		add(context().pageText(), "If you like the mod and want to support its development, you can donate at [https://www.buymeacoffee.com/ThomasGlasser](https://www.buymeacoffee.com/ThomasGlasser).");
	}

	@Override
	protected String entryName()
	{
		return "Donate";
	}

	@Override
	protected String entryDescription()
	{
		return "Supporting the mod and its developers.";
	}

	@Override
	protected BookIconModel entryIcon()
	{
		return BookIconModel.create(Items.EMERALD);
	}

	@Override
	protected String entryId()
	{
		return ID;
	}
}
