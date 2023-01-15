package dev.thomasglasser.minejago.world.level.storage;

import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.nbt.CompoundTag;

public class PlayerSpinjitzuComponent implements SpinjitzuComponent, PlayerComponent<SpinjitzuComponent>
{
    private boolean unlocked;
    private boolean active;

    public PlayerSpinjitzuComponent() {}

    public PlayerSpinjitzuComponent(boolean baseUnlocked, boolean baseActive)
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

    @Override
    public void copyForRespawn(SpinjitzuComponent original, boolean lossless, boolean keepInventory, boolean sameCharacter) {
        this.copyFrom(original);
        active = false;
    }

    @Override
    public boolean shouldCopyForRespawn(boolean lossless, boolean keepInventory, boolean sameCharacter) {
        return true;
    }
}
