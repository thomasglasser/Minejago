package dev.thomasglasser.minejago.world.level.biome;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

public class UnderworldBiomeSource extends BiomeSource {
    public static final MapCodec<UnderworldBiomeSource> CODEC = RecordCodecBuilder.mapCodec((p_255555_) -> p_255555_.group(
            Biome.CODEC.fieldOf("center").forGetter((source) -> source.center),
            Biome.CODEC.fieldOf("islands").forGetter((source) -> source.islands)).apply(p_255555_, p_255555_.stable(UnderworldBiomeSource::new)));
    private final Holder<Biome> center;
    private final Holder<Biome> islands;

    public static UnderworldBiomeSource create(HolderGetter<Biome> biomeGetter) {
        return new UnderworldBiomeSource(biomeGetter.getOrThrow(MinejagoBiomes.UNDERWORLD_CENTER), biomeGetter.getOrThrow(MinejagoBiomes.UNDERWORLD_ISLANDS));
    }

    public UnderworldBiomeSource(Holder<Biome> center, Holder<Biome> islands) {
        this.center = center;
        this.islands = islands;
    }

    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return Stream.of(this.center, this.islands);
    }

    protected MapCodec<? extends BiomeSource> codec() {
        return MinejagoBiomeSources.UNDERWORLD.get();
    }

    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        int i = QuartPos.toBlock(x);
        int j = QuartPos.toBlock(z);
        int k = SectionPos.blockToSectionCoord(i);
        int l = SectionPos.blockToSectionCoord(j);
        if ((long) k * (long) k + (long) l * (long) l <= 4096L) {
            return this.center;
        }
        return this.islands;
    }
}
