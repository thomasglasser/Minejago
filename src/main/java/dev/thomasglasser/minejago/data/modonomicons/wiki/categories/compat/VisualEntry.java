package dev.thomasglasser.minejago.data.modonomicons.wiki.categories.compat;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import net.minecraft.world.item.Items;

public class VisualEntry extends IndexModeEntryProvider
{
	public static final String ID = "visual";

	public VisualEntry(CategoryProviderBase parent)
	{
		super(parent);
	}

	@Override
	protected void generatePages()
	{
		// Text Page
		this.page("text", () -> BookTextPageModel.create()
				.withTitle(context().pageTitle())
				.withText(context().pageText()));

		pageTitle("playerAnimator");
		pageText("The playerAnimator mod adds animations for actions such as performing spinjitzu and using the golden weapons, making your moves look much cooler.");

		// Image Page
		this.page("image", () -> BookImagePageModel.create()
				// TODO: Recreate pic once playerAnimator is reinstalled
				.withImages(modLoc("textures/modonomicon/wiki/compat/visual/playeranimator.png"))
				.withText(context().pageText()));

		pageText("Using the Scythe of Quakes with playerAnimator installed");
	}

	@Override
	protected String entryId()
	{
		return ID;
	}

	@Override
	protected String entryName()
	{
		return "Visual";
	}

	@Override
	protected String entryDescription()
	{
		return "Mods that make the world of Minejago more visually appealing or add more viewing details.";
	}

	@Override
	protected BookIconModel entryIcon()
	{
		return BookIconModel.create(Items.SPYGLASS);
	}
}
