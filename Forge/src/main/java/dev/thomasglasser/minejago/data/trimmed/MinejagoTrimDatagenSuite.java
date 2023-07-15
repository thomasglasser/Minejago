package dev.thomasglasser.minejago.data.trimmed;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armortrim.MinejagoTrimPatterns;
import dhyces.trimmed.api.data.TrimDatagenSuite;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;

public class MinejagoTrimDatagenSuite extends TrimDatagenSuite {
    public MinejagoTrimDatagenSuite(GatherDataEvent event, LanguageProvider languageProvider) {
        super(event, Minejago.MOD_ID, languageProvider::add);

        makeFourWeapons();
    }

    private void makeFourWeapons()
    {
        makePattern(MinejagoTrimPatterns.FOUR_WEAPONS, MinejagoItems.FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE.get(), patternConfig -> patternConfig
                .createCopyRecipe(Items.IRON_BLOCK));
    }
}
