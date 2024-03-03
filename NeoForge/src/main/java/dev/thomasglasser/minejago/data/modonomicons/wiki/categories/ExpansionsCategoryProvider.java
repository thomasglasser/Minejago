package dev.thomasglasser.minejago.data.modonomicons.wiki.categories;

import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import dev.thomasglasser.minejago.world.item.MinejagoItems;

public class ExpansionsCategoryProvider extends CategoryProvider
{
	public static final String ID = "expansions";

	public static final String[] ENTRY_MAP = new String[]
			{
					"a______b______c_____d",
					"e______f______g_____h",
					"i______j______k_____l",
					"m______n______o_____p",
					"q______r______s_____t",
					"u______v______w_____x",
					"y______z______1_____2",
					"3______4______5_____6"
			};

	public ExpansionsCategoryProvider(BookProvider parent) {
		super(parent, ID);
	}

	@Override
	protected String[] generateEntryMap() {
		return ENTRY_MAP;
	}

	@Override
	protected void generateEntries() {
		// TODO: Add immersion and potion pot pack entries
	}

	@Override
	protected BookCategoryModel generateCategory() {
		lang().add(context().categoryName(), "Expansions");

		return BookCategoryModel.create(this.modLoc(this.context().categoryId()), this.context().categoryName())
				// TODO: Category description
				.withIcon(MinejagoItems.TEACUP.get());
	}
}
