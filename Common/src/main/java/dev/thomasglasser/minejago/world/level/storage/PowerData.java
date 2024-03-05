package dev.thomasglasser.minejago.world.level.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.power.Power;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

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
}
