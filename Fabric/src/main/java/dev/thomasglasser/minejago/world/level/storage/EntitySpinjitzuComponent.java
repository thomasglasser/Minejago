package dev.thomasglasser.minejago.world.level.storage;

import net.minecraft.nbt.CompoundTag;

public class EntitySpinjitzuComponent implements SpinjitzuComponent
{
    private boolean unlocked;
    private boolean active;

    public EntitySpinjitzuComponent() {}

    public EntitySpinjitzuComponent(boolean baseUnlocked, boolean baseActive)
    {
        unlocked = baseUnlocked;
        active = baseActive;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean beActive) {
        active = beActive;
    }

    @Override
    public boolean isUnlocked() {
        return unlocked;
    }

    @Override
    public void setUnlocked(boolean beUnlocked) {
        unlocked = beUnlocked;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        unlocked = tag.getBoolean("Unlocked");
        active = tag.getBoolean("Active");
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putBoolean("Unlocked", unlocked);
        tag.putBoolean("Active", active);
    }
}
