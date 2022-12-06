package dev.thomasglasser.minejago.world.entity.powers;

import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Power {
    @NotNull
    private String name;
    @NotNull
    protected Vector3f mainSpinjitzuColor;
    @NotNull
    protected Vector3f altSpinjitzuColor;
    @Nullable
    protected ParticleOptions borderParticle;

    public Power(@NotNull String name)
    {
        this.name = name;
        mainSpinjitzuColor = SpinjitzuParticleOptions.DEFAULT_MAIN;
        altSpinjitzuColor = SpinjitzuParticleOptions.DEFAULT_ALT;
    }

    public Power(@NotNull String name, @NotNull Vector3f mainSpinjitzuColor, @NotNull Vector3f altSpinjitzuColor, @Nullable ParticleOptions borderParticle)
    {
        this.name = name;
        this.mainSpinjitzuColor = mainSpinjitzuColor;
        this.altSpinjitzuColor = altSpinjitzuColor;
        this.borderParticle = borderParticle;
    }

    public Power(CompoundTag tag)
    {
        name = tag.getString("Name");

        CompoundTag main = tag.getCompound("MainSpinjitzuColor");
        mainSpinjitzuColor = !main.isEmpty() ? new Vector3f(main.getFloat("x"), main.getFloat("y"), main.getFloat("z")) : SpinjitzuParticleOptions.DEFAULT_MAIN;

        CompoundTag alt = tag.getCompound("AltSpinjitzuColor");
        altSpinjitzuColor = !alt.isEmpty() ? new Vector3f(alt.getFloat("x"), alt.getFloat("y"), alt.getFloat("z")) : SpinjitzuParticleOptions.DEFAULT_ALT;

        if (tag.contains("BorderParticle"))
            borderParticle = ParticleTypes.CODEC.parse(NbtOps.INSTANCE, tag.get("BorderParticle"))
                .resultOrPartial(s -> Minejago.LOGGER.error("Failed to load particle from " + tag.get("BorderParticle") + "\n" + s))
                .orElseThrow();
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
        return borderParticle;
    }

    public CompoundTag save()
    {
        CompoundTag nbt = new CompoundTag();

        nbt.putString("Name", name);

        CompoundTag mainColor = new CompoundTag();
        mainColor.putFloat("x", getMainSpinjitzuColor().x());
        mainColor.putFloat("y", getMainSpinjitzuColor().y());
        mainColor.putFloat("z", getMainSpinjitzuColor().z());

        nbt.put("MainSpinjitzuColor", mainColor);

        CompoundTag altColor = new CompoundTag();
        altColor.putFloat("x", getAltSpinjitzuColor().x());
        altColor.putFloat("y", getAltSpinjitzuColor().y());
        altColor.putFloat("z", getAltSpinjitzuColor().z());

        nbt.put("AltSpinjitzuColor", altColor);

        if (borderParticle != null)
        {
            nbt.put("BorderParticle", ParticleTypes.CODEC.encodeStart(NbtOps.INSTANCE, borderParticle)
                    .resultOrPartial(s -> Minejago.LOGGER.error("Failed to save particle " + borderParticle.toString() + "\n" + s))
                    .orElseThrow());
        }

        return nbt;
    }

    @Override
    public String toString() {
        return "[" + super.toString() + "]: {particle:" + name + "}";
    }
}
