package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.support;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.world.item.Items;

public class ContactEntry extends IndexModeEntryProvider
{
	public static final String ID = "contact";

	public ContactEntry(CategoryProviderBase parent)
	{
		super(parent);
	}

	@Override
	protected void generatePages()
	{
		page("discord", () -> BookImagePageModel.create()
				.withTitle(context().pageTitle())
				.withImages(Minejago.modLoc("textures/modonomicon/wiki/support/contact/discord.png"))
				.withText(context().pageText()));

		add(context().pageTitle(), "Discord");
		add(context().pageText(), "Support is available on the Minejago Discord server, available [here](https://discord.gg/U6Qf4P9YW4).");

		page("online_wiki", () -> BookTextPageModel.create()
				.withTitle(context().pageTitle())
				.withText(context().pageText()));

		add(context().pageTitle(), "Online Wiki");
		add(context().pageText(), "This wiki is also available online at [minejago.wiki.thomasglasser.dev](https://minejago.wiki.thomasglasser.dev).");

		page("github", () -> BookImagePageModel.create()
				.withTitle(context().pageTitle())
				.withImages(Minejago.modLoc("textures/modonomicon/wiki/support/contact/github.png"))
				.withText(context().pageText()));

		add(context().pageTitle(), "GitHub");
		add(context().pageText(), "The source code for this mod is available on GitHub [here](https://github.com/thomasglasser/Minejago).");

		page("mod_pages", () -> BookImagePageModel.create()
				.withTitle(context().pageTitle())
				.withImages(
						Minejago.modLoc("textures/modonomicon/wiki/support/contact/modrinth.png"),
						Minejago.modLoc("textures/modonomicon/wiki/support/contact/curseforge.png")
				).withText(context().pageText()));

		add(context().pageTitle(), "Modrinth");
		add(context().pageText(), "The mod is available on Modrinth [here](https://modrinth.com/mod/minejago) and Curseforge [here](https://www.curseforge.com/minecraft/mc-mods/minejago).");
	}

	@Override
	protected String entryName()
	{
		return "Contact";
	}

	@Override
	protected String entryDescription()
	{
		return "Resources for reaching out and getting support.";
	}

	@Override
	protected BookIconModel entryIcon()
	{
		return BookIconModel.create(Items.GOAT_HORN);
	}

	@Override
	protected String entryId()
	{
		return ID;
	}
}
