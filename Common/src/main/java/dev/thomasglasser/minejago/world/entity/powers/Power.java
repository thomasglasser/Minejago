package dev.thomasglasser.minejago.world.entity.powers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistryKeys;
import dev.thomasglasser.minejago.data.tags.MinejagoPowerTags;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class Power {
    public static final Codec<Power> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(Power::getId),
            ExtraCodecs.VECTOR3F.optionalFieldOf("main_spinjitzu_color", SpinjitzuParticleOptions.DEFAULT).forGetter(Power::getMainSpinjitzuColor),
            ExtraCodecs.VECTOR3F.optionalFieldOf("alt_spinjitzu_color", SpinjitzuParticleOptions.DEFAULT).forGetter(Power::getAltSpinjitzuColor),
            BuiltInRegistries.PARTICLE_TYPE.byNameCodec().optionalFieldOf("border_particle", null).forGetter(Power::getBorderParticleType),
            Codec.BOOL.optionalFieldOf("make_sets", false).forGetter(Power::doMakeSets)
    ).apply(instance, Power::new));

    @NotNull
    private final ResourceLocation id;
    @NotNull
    protected Vector3f mainSpinjitzuColor;
    @NotNull
    protected Vector3f altSpinjitzuColor;
    @Nullable
    protected Supplier<? extends ParticleOptions> borderParticle;
    @NotNull
    protected boolean makeSets;

    private String descriptionId;

    public Power(@NotNull ResourceLocation id)
    {
        this(id, SpinjitzuParticleOptions.DEFAULT, SpinjitzuParticleOptions.DEFAULT, (Supplier<? extends ParticleOptions>) null, false);
    }

    public Power(@NotNull ResourceLocation id, @NotNull Vector3f mainSpinjitzuColor, @NotNull Vector3f altSpinjitzuColor, @Nullable Supplier<? extends ParticleOptions> borderParticle, boolean makeSets)
    {
        this.id = id;
        this.mainSpinjitzuColor = mainSpinjitzuColor;
        this.altSpinjitzuColor = altSpinjitzuColor;
        this.borderParticle = borderParticle;
        this.makeSets = makeSets;
    }

    public Power(@NotNull ResourceLocation id, @NotNull Vector3f mainSpinjitzuColor, @NotNull Vector3f altSpinjitzuColor, @Nullable ParticleType<? extends ParticleOptions> borderParticle, boolean makeSets)
    {
        this(id, mainSpinjitzuColor, altSpinjitzuColor, borderParticle instanceof ParticleOptions ? () -> (ParticleOptions) borderParticle : null, makeSets);
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

    @Nullable
    public ParticleType<? extends ParticleOptions> getBorderParticleType()
    {
        return borderParticle.get().getType();
    }

    @Override
    public String toString() {
        return "[" + super.toString() + "]: {power:" + id + "}";
    }

    public ResourceLocation getId() {
        return id;
    }

    public boolean doMakeSets() {
        return makeSets;
    }

    public boolean is(TagKey<Power> tag)
    {
        return MinejagoRegistries.POWER.get().getTag(tag).get().contains(MinejagoRegistries.POWER.get().getHolderOrThrow(ResourceKey.create(MinejagoRegistryKeys.POWER, getId())));
    }

    public boolean is(Power power)
    {
        return power == this;
    }

    public boolean is(Supplier<Power> power)
    {
        return this == power.get();
    }

    public boolean is(ResourceKey<Power> key)
    {
        return key == ResourceKey.create(MinejagoRegistryKeys.POWER, getId());
    }
}
