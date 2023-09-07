package dev.thomasglasser.minejago.world.entity;

import net.minecraft.world.entity.PlayerRideable;

public interface PlayerRideableFlying extends PlayerRideable
{
    void ascend();
    void descend();
    void stop();
    double getVerticalSpeed();
    enum Flight {
        ASCENDING,
        DESCENDING,
        HOVERING
    }
}