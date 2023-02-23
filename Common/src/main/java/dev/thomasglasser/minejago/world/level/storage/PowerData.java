package dev.thomasglasser.minejago.world.level.storage;

import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.resources.ResourceKey;

public record PowerData(ResourceKey<Power> power) {
    public PowerData()
    {
        this(MinejagoPowers.NONE);
    }

    @Override
    public ResourceKey<Power> power() {
        return power == null ? MinejagoPowers.NONE : power;
    }
}
