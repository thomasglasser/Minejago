package dev.thomasglasser.minejago.world.level.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record SkillData(int level, float practice) {

    public static final Codec<SkillData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("level").forGetter(SkillData::level),
            Codec.FLOAT.fieldOf("practice").forGetter(SkillData::practice)).apply(instance, SkillData::new));
    public static final StreamCodec<ByteBuf, SkillData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, SkillData::level,
            ByteBufCodecs.FLOAT, SkillData::practice,
            SkillData::new);
    public SkillData() {
        this(0, 0);
    }

    public SkillData increaseLevel() {
        return new SkillData(level + 1, 0);
    }

    public SkillData addPractice(float amount) {
        return new SkillData(level, practice + amount);
    }
}
