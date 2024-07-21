package dev.thomasglasser.minejago.world.level.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ProgressData(int elementsPassed, int elementsPresent, boolean passed) {

    public static final Codec<ProgressData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("elements_defeated").forGetter(ProgressData::elementsPassed),
            Codec.INT.fieldOf("elements_present").forGetter(ProgressData::elementsPresent),
            Codec.BOOL.fieldOf("passed").forGetter(ProgressData::passed))
            .apply(instance, ProgressData::new));
    public static final StreamCodec<ByteBuf, ProgressData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ProgressData::elementsPassed,
            ByteBufCodecs.INT, ProgressData::elementsPresent,
            ByteBufCodecs.BOOL, ProgressData::passed,
            ProgressData::new);
    public ProgressData() {
        this(0, 0, false);
    }

    public ProgressData withElementsPassed(int elementsPassed) {
        return new ProgressData(elementsPassed, elementsPresent, passed || elementsPassed >= elementsPresent);
    }

    public ProgressData withElementsPresent(int elementsPresent) {
        return new ProgressData(elementsPassed, elementsPresent, passed || elementsPassed >= elementsPresent);
    }

    public ProgressData increment() {
        return withElementsPassed(elementsPassed + 1);
    }

    public ProgressData reset() {
        return new ProgressData(0, 0, passed);
    }
}
