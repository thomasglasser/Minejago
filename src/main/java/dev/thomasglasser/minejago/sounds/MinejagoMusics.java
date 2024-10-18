package dev.thomasglasser.minejago.sounds;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;

public class MinejagoMusics {
    private static final List<Music> MUSICS = new ArrayList<>();

    // Structures
    public static final Music MONASTERY_OF_SPINJITZU = registerStructure(MinejagoSoundEvents.MUSIC_MONASTERY_OF_SPINJITZU);
    public static final Music CAVE_OF_DESPAIR = registerStructure(MinejagoSoundEvents.MUSIC_CAVE_OF_DESPAIR);

    // Events
    public static final Music SKULKIN_RAID = registerEvent(MinejagoSoundEvents.MUSIC_SKULKIN_RAID);

    private static Music register(Holder<SoundEvent> soundEvent, int minDelay, int maxDelay, boolean replaceCurrentMusic) {
        Music music = new Music(soundEvent, minDelay, maxDelay, replaceCurrentMusic);
        MUSICS.add(music);
        return music;
    }

    private static Music registerStructure(Holder<SoundEvent> soundEvent) {
        return register(soundEvent, Musics.ONE_SECOND, Musics.THIRTY_SECONDS, true);
    }

    private static Music registerEvent(Holder<SoundEvent> soundEvent) {
        return register(soundEvent, 1, Musics.ONE_SECOND, true);
    }

    public static List<Music> getAll() {
        return MUSICS;
    }
}
