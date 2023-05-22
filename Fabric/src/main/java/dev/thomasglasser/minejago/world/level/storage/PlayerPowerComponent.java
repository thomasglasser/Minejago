package dev.thomasglasser.minejago.world.level.storage;

import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;

public class PlayerPowerComponent extends LivingEntityPowerComponent implements PlayerComponent<PlayerPowerComponent>
{
    @Override
    public boolean shouldCopyForRespawn(boolean lossless, boolean keepInventory, boolean sameCharacter) {
        return lossless || keepInventory;
    }
}
