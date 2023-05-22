package dev.thomasglasser.minejago.world.level.storage;

import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;

public class PlayerSpinjitzuComponent extends LivingEntitySpinjitzuComponent implements PlayerComponent<PlayerSpinjitzuComponent>
{
    @Override
    public boolean shouldCopyForRespawn(boolean lossless, boolean keepInventory, boolean sameCharacter) {
        return lossless || keepInventory;
    }

    @Override
    public void copyForRespawn(PlayerSpinjitzuComponent original, boolean lossless, boolean keepInventory, boolean sameCharacter) {
        PlayerComponent.super.copyForRespawn(original, lossless, keepInventory, sameCharacter);
        setActive(false);
    }
}
