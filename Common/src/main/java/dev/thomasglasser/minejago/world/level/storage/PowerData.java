package dev.thomasglasser.minejago.world.level.storage;

import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;

public record PowerData(Power power) {
    public PowerData()
    {
        this(MinejagoPowers.NONE.get());
    }

    @Override
    public Power power() {
        return power == null ? MinejagoPowers.NONE.get() : power;
    }
}
