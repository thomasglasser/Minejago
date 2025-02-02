package dev.thomasglasser.minejago.data.lang.expansions;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.packs.MinejagoPacks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MinejagoImmersionPackEnUsLanguageProvider extends LanguageProvider {
    public MinejagoImmersionPackEnUsLanguageProvider(PackOutput output) {
        super(output, MinejagoPacks.IMMERSION.knownPack().id(), "en_us");
    }

    @Override
    protected void addTranslations() {
        add(Blocks.NOTE_BLOCK, "Drum");
        add("advancements.husbandry.allay_deliver_cake_to_note_block.description", "Have an Allay drop a Cake at a Drum");
        add("soundCategory.record", "Jukebox/Drums");
        add("stat.minecraft.play_noteblock", "Drums Played");
        add("stat.minecraft.tune_noteblock", "Drums Tuned");
        add("subtitles.block.note_block.note", "Drum plays");
    }

    @Override
    public String getName() {
        return "Minejago Immersion Pack " + super.getName();
    }
}
