package dev.thomasglasser.minejago.world.entity.powers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

public class Power {
    public static final Codec<Power> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(Power::getId),
            ChatFormatting.CODEC.fieldOf("power_color").forGetter(Power::getColor),
            ExtraCodecs.VECTOR3F.optionalFieldOf("main_spinjitzu_color", SpinjitzuParticleOptions.DEFAULT).forGetter(Power::getMainSpinjitzuColor),
            ExtraCodecs.VECTOR3F.optionalFieldOf("alt_spinjitzu_color", SpinjitzuParticleOptions.DEFAULT).forGetter(Power::getAltSpinjitzuColor),
            BuiltInRegistries.PARTICLE_TYPE.byNameCodec().optionalFieldOf("border_particle").forGetter(Power::getBorderParticleType),
            Codec.BOOL.optionalFieldOf("make_sets", false).forGetter(Power::doMakeSets),
            Codec.BOOL.optionalFieldOf("selectable", false).forGetter(Power::isSelectable),
            Display.CODEC.optionalFieldOf("display", Display.EMPTY).forGetter(Power::getDisplay)
    ).apply(instance, Power::new));

    @NotNull
    private final ResourceLocation id;
    @NotNull
    private final ChatFormatting color;
    @NotNull
    protected Vector3f mainSpinjitzuColor;
    @NotNull
    protected Vector3f altSpinjitzuColor;
    @Nullable
    protected Supplier<? extends ParticleOptions> borderParticle;
    protected boolean makeSets;
    protected boolean selectable;
    protected Display display;

    private String descId;



    public Power(@NotNull ResourceLocation id)
    {
        this(id, ChatFormatting.GRAY, SpinjitzuParticleOptions.DEFAULT, SpinjitzuParticleOptions.DEFAULT, (Supplier<? extends ParticleOptions>) null, false, false, Display.EMPTY);
    }

    public Power(@NotNull ResourceLocation id, @NotNull ChatFormatting color, @NotNull Vector3f mainSpinjitzuColor, @NotNull Vector3f altSpinjitzuColor, @Nullable Supplier<? extends ParticleOptions> borderParticle, boolean makeSets, boolean selectable, Display display)
    {
        this.id = id;
        this.color = color.isColor() ? color : ChatFormatting.GRAY;
        this.mainSpinjitzuColor = mainSpinjitzuColor;
        this.altSpinjitzuColor = altSpinjitzuColor;
        this.borderParticle = borderParticle;
        this.makeSets = makeSets;
        this.selectable = selectable;
        this.display = display;
    }

    public Power(@NotNull ResourceLocation id, ChatFormatting color, @NotNull Vector3f mainSpinjitzuColor, @NotNull Vector3f altSpinjitzuColor, @Nullable ParticleType<? extends ParticleOptions> borderParticle, boolean makeSets, boolean selectable, Display display)
    {
        this(id, color, mainSpinjitzuColor, altSpinjitzuColor, borderParticle instanceof ParticleOptions ? () -> (ParticleOptions) borderParticle : null, makeSets, selectable, display);
    }

    public Power(@NotNull ResourceLocation id, ChatFormatting color, @NotNull Vector3f mainSpinjitzuColor, @NotNull Vector3f altSpinjitzuColor, @NotNull Optional<ParticleType<?>> borderParticle, boolean makeSets, boolean selectable, Display display)
    {
        this(id, color, mainSpinjitzuColor, altSpinjitzuColor, borderParticle.orElse(null), makeSets, selectable, display);
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
        return borderParticle == null ? null : borderParticle.get();
    }

    @Nullable
    public Optional<ParticleType<?>> getBorderParticleType()
    {
        return borderParticle != null ? Optional.of(borderParticle.get().getType()) : Optional.empty();
    }

    public @NotNull ChatFormatting getColor() {
        return color;
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

    public String getDescriptionId()
    {
        if (descId == null) descId = Util.makeDescriptionId("power", getId());
        return descId;
    }

    public Component getFormattedName()
    {
        return Component.translatable(getDescriptionId()).withStyle(getColor());
    }

    public boolean isSelectable()
    {
        return selectable;
    }

    public Display getDisplay() {
        return display;
    }

    public boolean is(TagKey<Power> tag, Registry<Power> registry)
    {
        return registry.getTag(tag).get().contains(registry.getHolderOrThrow(ResourceKey.create(registry.key(), getId())));
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
        return key == ResourceKey.create(MinejagoRegistries.POWER, getId());
    }

    public record Display(Component lore, Component subtitle, Component description) {
        public static final Display EMPTY = new Display(Component.empty(), Component.empty(), Component.empty());

        public static final Codec<Display> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.COMPONENT.optionalFieldOf("lore", Component.empty()).forGetter(Display::lore),
            ExtraCodecs.COMPONENT.optionalFieldOf("subtitle", Component.empty()).forGetter(Display::subtitle),
            ExtraCodecs.COMPONENT.optionalFieldOf("description", Component.empty()).forGetter(Display::description)
        ).apply(instance, Display::new));
    }
}
