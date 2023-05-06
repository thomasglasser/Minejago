package dev.thomasglasser.minejago.data.lang.expansions;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.LanguageProvider;
import org.jetbrains.annotations.NotNull;

public class MinejagoImmersionPackEnUsLanguageProvider extends LanguageProvider {
    public MinejagoImmersionPackEnUsLanguageProvider(PackOutput output) {
        super(output, Minejago.Expansions.IMMERSION_PACK.getId(), "en_us");
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
    public @NotNull String getName() {
        return "Minejago Immersion Pack " + super.getName();
    }
}
