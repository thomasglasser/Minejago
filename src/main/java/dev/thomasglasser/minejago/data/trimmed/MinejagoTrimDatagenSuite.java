package dev.thomasglasser.minejago.data.trimmed;

import dev.dhyces.trimmed.api.data.TrimDatagenSuite;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armortrim.MinejagoTrimPatterns;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class MinejagoTrimDatagenSuite extends TrimDatagenSuite
{
    public MinejagoTrimDatagenSuite(GatherDataEvent event, LanguageProvider languageProvider) {
        super(event, Minejago.MOD_ID, languageProvider::add);
    }

    @Override
    public void generate() {
        makePattern(MinejagoTrimPatterns.FOUR_WEAPONS, MinejagoItems.FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE.get(), false,  patternConfig -> patternConfig
                .createCopyRecipe(Items.IRON_BLOCK));
    }
}
