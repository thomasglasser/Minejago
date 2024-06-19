package dev.thomasglasser.minejago.world.level.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.power.Power;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public record PowerData(ResourceKey<Power> power, boolean given) {
    public static final Codec<PowerData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    ResourceKey.codec(MinejagoRegistries.POWER).fieldOf("power").forGetter(PowerData::power),
                    Codec.BOOL.fieldOf("given").forGetter(PowerData::given))
            .apply(instance, PowerData::new));
    
    public PowerData()
    {
        this(MinejagoPowers.NONE, false);
    }

    public PowerData(ResourceLocation location, boolean given)
    {
        this(ResourceKey.create(MinejagoRegistries.POWER, location), given);
    }

    @Override
    public ResourceKey<Power> power() {
        return power == null ? MinejagoPowers.NONE : power;
    }

    public void save(LivingEntity entity)
    {
        entity.setData(MinejagoAttachmentTypes.POWER, this);
        if (entity instanceof ServerPlayer serverPlayer)
            MinejagoCriteriaTriggers.GOT_POWER.get().trigger(serverPlayer, this.power());
    }
}
