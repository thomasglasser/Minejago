package dev.thomasglasser.minejago.world.entity;

import net.minecraft.world.entity.Entity;

public interface ElementGiver {
    int getId();

    float distanceTo(Entity entity);
}
