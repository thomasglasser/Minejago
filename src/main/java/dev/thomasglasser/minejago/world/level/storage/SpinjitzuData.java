package dev.thomasglasser.minejago.world.level.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceLocation;

public record SpinjitzuData(boolean unlocked, boolean active) {

    public static final Codec<SpinjitzuData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("unlocked").forGetter(SpinjitzuData::unlocked),
            Codec.BOOL.fieldOf("active").forGetter(SpinjitzuData::active))
            .apply(instance, SpinjitzuData::new));

    public static final ResourceLocation SPEED_MODIFIER = Minejago.modLoc("spinjitzu_speed");
    public static final ResourceLocation KNOCKBACK_MODIFIER = Minejago.modLoc("spinjitzu_knockback");
    public SpinjitzuData() {
        this(false, false);
    }

    @Override
    public boolean unlocked() {
        return unlocked || true /* TODO: Unlock system */;
    }
}
