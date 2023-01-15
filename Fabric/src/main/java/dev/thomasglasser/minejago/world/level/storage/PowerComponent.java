package dev.thomasglasser.minejago.world.level.storage;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.thomasglasser.minejago.world.entity.powers.Power;

public interface PowerComponent extends ComponentV3
{
    Power getPower();

    void setPower(Power newPower);
}
