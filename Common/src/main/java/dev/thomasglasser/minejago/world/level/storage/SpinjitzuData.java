package dev.thomasglasser.minejago.world.level.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public record SpinjitzuData(boolean unlocked, boolean active) {
    public static final Codec<SpinjitzuData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.BOOL.fieldOf("unlocked").forGetter(SpinjitzuData::unlocked),
                    Codec.BOOL.fieldOf("active").forGetter(SpinjitzuData::active))
            .apply(instance, SpinjitzuData::new));

    public static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(UUID.randomUUID(), "Spinjitzu speed", 1.5, AttributeModifier.Operation.MULTIPLY_BASE);
    public static final AttributeModifier KNOCKBACK_MODIFIER = new AttributeModifier(UUID.randomUUID(), "Spinjitzu knockback", 1.5, AttributeModifier.Operation.ADDITION);

    public SpinjitzuData()
    {
        this(false, false);
    }

    @Override
    public boolean unlocked() {
        return unlocked || true /* TODO: Unlock system */;
    }
}
