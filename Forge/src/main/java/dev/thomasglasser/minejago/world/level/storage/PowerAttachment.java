package dev.thomasglasser.minejago.world.level.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.power.Power;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;

public class PowerAttachment
{
    public static final Codec<PowerAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceKey.codec(MinejagoRegistries.POWER).fieldOf("power").forGetter(PowerAttachment::getPower),
            Codec.BOOL.fieldOf("given").forGetter(PowerAttachment::isGiven))
            .apply(instance, PowerAttachment::new));

    @NotNull
    private ResourceKey<Power> power;
    private boolean given;

    public PowerAttachment(ResourceKey<Power> power, boolean given)
    {
        this.power = power;
        this.given = given;
    }

    public @NotNull ResourceKey<Power> getPower() {
        return power;
    }

    public void setPower(ResourceKey<Power> power) {
        this.power = power;
    }

    public void setGiven(boolean given) {
        this.given = given;
    }

    public boolean isGiven() {
        return given;
    }
}
