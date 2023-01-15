package dev.thomasglasser.minejago.world.entity.powers;

import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleOptions;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class Power {
    @NotNull
    private String name;
    @NotNull
    protected Vector3f mainSpinjitzuColor;
    @NotNull
    protected Vector3f altSpinjitzuColor;
    @Nullable
    protected Supplier<? extends ParticleOptions> borderParticle;

    private String descriptionId;

    public Power(@NotNull String name)
    {
        this.name = name;
        mainSpinjitzuColor = SpinjitzuParticleOptions.DEFAULT;
        altSpinjitzuColor = SpinjitzuParticleOptions.DEFAULT;
    }

    public Power(@NotNull String name, @NotNull Vector3f mainSpinjitzuColor, @NotNull Vector3f altSpinjitzuColor, @Nullable Supplier<? extends ParticleOptions> borderParticle)
    {
        this.name = name;
        this.mainSpinjitzuColor = mainSpinjitzuColor;
        this.altSpinjitzuColor = altSpinjitzuColor;
        this.borderParticle = borderParticle;
    }

    @NotNull
    public Vector3f getMainSpinjitzuColor()
    {
        return mainSpinjitzuColor;
    }
    @NotNull
    public Vector3f getAltSpinjitzuColor()
    {
        return altSpinjitzuColor;
    }
    @Nullable
    public ParticleOptions getBorderParticle()
    {
        return borderParticle.get();
    }

    @Override
    public String toString() {
        return "[" + super.toString() + "]: {power:" + name + "}";
    }

    public String getName() {
        return name;
    }

    protected String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("power", MinejagoRegistries.POWER.get().getKey(this));
        }

        return this.descriptionId;
    }

    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }
}
