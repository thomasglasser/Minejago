package dev.thomasglasser.minejago.world.level.storage;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.SimpleMapCodec;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.network.ClientboundSkillLevelUpPacket;
import dev.thomasglasser.minejago.network.ClientboundSyncSkillDataSetPayload;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.skill.Skill;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;

public class SkillDataSet {
    public static final int LEVEL_UP_THRESHOLD = 100;
    public static final SimpleMapCodec<Skill, SkillData> MAP_CODEC = Codec.simpleMap(Codec.STRING.xmap(Skill::valueOf, Skill::name), SkillData.CODEC, StringRepresentable.keys(Skill.values()));
    public static final Codec<SkillDataSet> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MAP_CODEC.fieldOf("map").forGetter(set -> set.map)).apply(instance, SkillDataSet::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SkillDataSet> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(
                    Maps::newHashMapWithExpectedSize,
                    ByteBufCodecs.STRING_UTF8.map(Skill::valueOf, Skill::name),
                    SkillData.STREAM_CODEC),
            set -> set.map,
            SkillDataSet::new);

    private final Map<Skill, SkillData> map;
    private boolean dirty;

    public SkillDataSet() {
        this.map = new HashMap<>();
    }

    public SkillDataSet(Map<Skill, SkillData> map) {
        this.map = new HashMap<>(map);
    }

    public void addPractice(LivingEntity livingEntity, Skill key, float amount) {
        if (livingEntity.level().isClientSide)
            throw new IllegalStateException("Cannot tick practice on client side");
        SkillData data = get(key);
        if (data.practice() >= LEVEL_UP_THRESHOLD) {
            if (livingEntity instanceof ServerPlayer player) {
                TommyLibServices.NETWORK.sendToClient(new ClientboundSkillLevelUpPacket(key), player);
            }
            put(livingEntity, key, data.increaseLevel(), true);
        } else {
            put(livingEntity, key, data.addPractice(amount), true);
        }
    }

    public SkillData get(Skill key) {
        return map.getOrDefault(key, new SkillData());
    }

    public SkillData put(LivingEntity entity, Skill key, SkillData value, boolean syncToClient) {
        SkillData data = map.put(key, value);
        if (entity instanceof ServerPlayer player)
            MinejagoCriteriaTriggers.INCREASED_SKILL.get().trigger(player, key, value.level());
        setDirty(true);
        save(entity, syncToClient);
        return data;
    }

    public List<SkillData> values() {
        return List.copyOf(map.values());
    }

    public void save(LivingEntity entity, boolean syncToClient) {
        entity.setData(MinejagoAttachmentTypes.SKILL.get(), this);
        if (syncToClient) TommyLibServices.NETWORK.sendToAllClients(new ClientboundSyncSkillDataSetPayload(this, entity.getId()), entity.level().getServer());
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
}
