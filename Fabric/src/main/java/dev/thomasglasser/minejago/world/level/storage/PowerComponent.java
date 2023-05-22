package dev.thomasglasser.minejago.world.level.storage;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.resources.ResourceKey;

public interface PowerComponent extends ComponentV3, AutoSyncedComponent
{
    ResourceKey<Power> getPower();

    void setPower(ResourceKey<Power> newPower);

    boolean isGiven();

    void setGiven(boolean given);
}
