package dev.thomasglasser.minejago.data.modonomicons.wiki.categories;

import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.minejago.packs.MinejagoPacks;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.world.item.Items;

import static dev.thomasglasser.minejago.data.modonomicons.wiki.MinejagoWikiBookProvider.SIMPLE_ENTRY_MAP;

public class ExpansionsCategoryProvider extends CategoryProvider
{
	public static final String ID = "expansions";

	public ExpansionsCategoryProvider(BookProvider parent) {
		super(parent, ID);
	}

	@Override
	protected String[] generateEntryMap() {
		return SIMPLE_ENTRY_MAP;
	}

	@Override
	protected BookCategoryModel generateCategory() {
		lang().add(context().categoryName(), "Expansions");

		return BookCategoryModel.create(this.modLoc(this.context().categoryId()), this.context().categoryName())
				// TODO: Category description
				.withIcon(MinejagoItems.TEACUP.get());
	}

	@Override
	protected void generateEntries() {
		add(generateImmersionPackEntry().withLocation(entryMap.get('a')));
		add(generatePotionPotPackEntry().withLocation(entryMap.get('b')));
	}

	protected BookEntryModel generateImmersionPackEntry() {
		context().entry("immersion_pack");

		context().page("summary_text");
		BookTextPageModel summaryText = BookTextPageModel.create()
				.withText(context().pageText());
		lang().add(context().pageText(), "The Immersion Pack is a resource pack that makes the world of Ninjago feel more whole with small changes, such as turning Note Blocks into Drums.");

		context().page("summary_image");
		BookImagePageModel summaryImage = BookImagePageModel.create()
				.withImages(
						// TODO: Image of drum scene with characters dancing (need dancing)
						modLoc("textures/modonomicon/wiki/expansions/immersion_pack/drum_scene.png"))
				.withText(context().pageText());
		lang().add(context().pageText(), "We are aware this image is missing. It will be added in the next update.");

		return BookEntryModel.create(
						this.modLoc(this.context().categoryId() + "/" + this.context().entryId()),
						MinejagoPacks.IMMERSION.titleKey()
				)
				.withDescription(MinejagoPacks.IMMERSION.descriptionKey())
				.withIcon(Items.NOTE_BLOCK)
				.withPages(
						summaryText, summaryImage
				);
	}

	protected BookEntryModel generatePotionPotPackEntry()
	{
		context().entry("potion_pot_pack");

		context().page("summary_text");
		BookTextPageModel summaryText = BookTextPageModel.create()
				.withText(context().pageText());
		lang().add(context().pageText(),
				"The Potion Pot Pack is a data pack that allows the player to brew vanilla potions in teapots using the vanilla brewing recipes. " +
				"These recipes may be overridden by a datapack, which will have no effect on the brewing stand, but if the brewing recipes have been changed by a mod, the teapot recipes will remain the same as vanilla unless also overridden by a data pack.");

		context().page("summary_image");
		BookImagePageModel summaryImage = BookImagePageModel.create()
				.withImages(modLoc("textures/modonomicon/wiki/expansions/potion_pot_pack/regeneration_pot.png"));

		return BookEntryModel.create(
						this.modLoc(this.context().categoryId() + "/" + this.context().entryId()),
						MinejagoPacks.POTION_POT.titleKey()
				)
				.withDescription(MinejagoPacks.POTION_POT.descriptionKey())
				.withIcon(MinejagoBlocks.TEAPOT.get())
				.withPages(
						summaryText,
						summaryImage
				);
	}
}
