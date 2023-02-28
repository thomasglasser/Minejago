package dev.thomasglasser.minejago.world.level.storage;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public record SpinjitzuData(boolean unlocked, boolean active) {
    public static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(UUID.randomUUID(), "Spinjitzu speed", 1.5, AttributeModifier.Operation.MULTIPLY_BASE);
    public static final AttributeModifier KNOCKBACK_MODIFIER = new AttributeModifier(UUID.randomUUID(), "Spinjitzu knockback", 1.5, AttributeModifier.Operation.ADDITION);

    @Override
    public boolean unlocked() {
        return unlocked || true /* TODO: Unlock system */;
    }
}
