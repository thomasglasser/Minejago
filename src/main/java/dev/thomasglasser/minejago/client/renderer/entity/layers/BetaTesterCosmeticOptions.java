package dev.thomasglasser.minejago.client.renderer.entity.layers;

import net.minecraft.world.entity.EquipmentSlot;

public enum BetaTesterCosmeticOptions {
    BAMBOO_HAT(EquipmentSlot.HEAD);

    final EquipmentSlot slot;

    BetaTesterCosmeticOptions(EquipmentSlot slot) {
        this.slot = slot;
    }

    public EquipmentSlot slot() {
        return this.slot;
    }
}
