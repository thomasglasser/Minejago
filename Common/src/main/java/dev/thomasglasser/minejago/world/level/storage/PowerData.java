package dev.thomasglasser.minejago.world.level.storage;

import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public record PowerData(ResourceKey<Power> power, boolean given) {
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
