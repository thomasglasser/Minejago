package dev.thomasglasser.minejago.world.level.storage;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface SpinjitzuComponent extends ComponentV3
{
    boolean isActive();
    void setActive(boolean beActive);

    boolean isUnlocked();
    void setUnlocked(boolean beUnlocked);
}
