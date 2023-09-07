package dev.thomasglasser.minejago.world.entity.power;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
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
            TextColor.CODEC.optionalFieldOf("power_color", TextColor.fromLegacyFormat(ChatFormatting.GRAY)).forGetter(Power::getColor),
            ExtraCodecs.COMPONENT.optionalFieldOf("tagline", Component.empty()).forGetter(Power::getTagline),
            ExtraCodecs.VECTOR3F.optionalFieldOf("main_spinjitzu_color", SpinjitzuParticleOptions.DEFAULT).forGetter(Power::getMainSpinjitzuColor),
            ExtraCodecs.VECTOR3F.optionalFieldOf("alt_spinjitzu_color", SpinjitzuParticleOptions.DEFAULT).forGetter(Power::getAltSpinjitzuColor),
            BuiltInRegistries.PARTICLE_TYPE.byNameCodec().optionalFieldOf("border_particle").forGetter(Power::getBorderParticleType),
            Codec.BOOL.optionalFieldOf("has_sets", false).forGetter(Power::hasSets),
            Display.CODEC.optionalFieldOf("display", Display.EMPTY).forGetter(Power::getDisplay),
            Codec.BOOL.optionalFieldOf("is_special", false).forGetter(Power::isSpecial)
    ).apply(instance, Power::new));

    @NotNull
    private final ResourceLocation id;
    @NotNull
    private final TextColor color;
    @NotNull
    protected Vector3f mainSpinjitzuColor;
    @NotNull
    protected Vector3f altSpinjitzuColor;
    @Nullable
    protected Supplier<? extends ParticleOptions> borderParticle;
    protected boolean hasSets;
    @NotNull
    protected Display display;
    protected boolean isSpecial;
    @NotNull
    protected Component tagline;

    private String descId;
    private final ResourceLocation icon;

    public static Builder builder(ResourceLocation id)
    {
        return new Builder(id);
    }

    public static Builder builder(String id)
    {
        return new Builder(Minejago.modLoc(id));
    }

    protected Power(@NotNull ResourceLocation id, @NotNull TextColor color, @Nullable Component tagline, @NotNull Vector3f mainSpinjitzuColor, @NotNull Vector3f altSpinjitzuColor, @Nullable Supplier<? extends ParticleOptions> borderParticle, boolean hasSets, @Nullable Display display, boolean isSpecial)
    {
        this.id = id;
        this.color = color;
        this.tagline = tagline == null ? Component.empty() : tagline;
        this.mainSpinjitzuColor = mainSpinjitzuColor;
        this.altSpinjitzuColor = altSpinjitzuColor;
        this.borderParticle = borderParticle;
        this.hasSets = hasSets;
        this.display = display == null ? Display.EMPTY : display;
        this.isSpecial = isSpecial;
        this.icon = new ResourceLocation(id.getNamespace(), "textures/power/" + id.getPath() + ".png");
    }

    protected Power(@NotNull ResourceLocation id, TextColor color, @Nullable Component tagline, @NotNull Vector3f mainSpinjitzuColor, @NotNull Vector3f altSpinjitzuColor, @NotNull Optional<ParticleType<?>> borderParticle, boolean hasSets, Display display, boolean isSpecial)
    {
        this(id, color, tagline, mainSpinjitzuColor, altSpinjitzuColor, borderParticle.orElse(null) instanceof ParticleOptions ? () -> (ParticleOptions) borderParticle.orElse(null) : null, hasSets, display, isSpecial);
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

    public @NotNull TextColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "[" + super.toString() + "]: {power:" + id + "}";
    }

    public ResourceLocation getId() {
        return id;
    }

    public boolean hasSets() {
        return hasSets;
    }

    public String getDescriptionId()
    {
        if (descId == null) descId = Util.makeDescriptionId("power", getId());
        return descId;
    }

    public Component getFormattedName()
    {
        MutableComponent component = Component.translatable(getDescriptionId());
        component.withStyle((component.getStyle().withColor(color)));
        return component;
    }
    public Display getDisplay() {
        return display;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public Component getTagline() {
        return tagline;
    }

    public ResourceLocation getIcon()
    {
        return icon;
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

    public record Display(Component lore, Component description) {
        public static final Display EMPTY = new Display(Component.empty(), Component.empty());

        public static final Codec<Display> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.COMPONENT.optionalFieldOf("lore", Component.empty()).forGetter(Display::lore),
            ExtraCodecs.COMPONENT.optionalFieldOf("description", Component.empty()).forGetter(Display::description)
        ).apply(instance, Display::new));

        public static Display withDefaultKeys(ResourceLocation id)
        {
            return new Display(Component.translatable(Util.makeDescriptionId("power", id) + ".lore"), Component.translatable(Util.makeDescriptionId("power", id) + ".description"));
        }
    }

    public static Component defaultTagline(ResourceLocation id)
    {
        return Component.translatable(Util.makeDescriptionId("power", id) + ".tagline");
    }

    public static class Builder
    {
        private final ResourceLocation id;
        private TextColor color;
        protected Vector3f mainSpinjitzuColor;
        protected Vector3f altSpinjitzuColor;
        protected Supplier<? extends ParticleOptions> borderParticle;
        protected boolean hasSets;
        protected Display display;
        protected boolean isSpecial;
        protected Component tagline;

        public Builder(ResourceLocation id)
        {
            this.id = id;
            this.color = TextColor.fromLegacyFormat(ChatFormatting.GRAY);
            this.tagline = Component.empty();
            this.mainSpinjitzuColor = SpinjitzuParticleOptions.DEFAULT;
            this.altSpinjitzuColor = SpinjitzuParticleOptions.DEFAULT;
            this.borderParticle = null;
            this.hasSets = false;
            this.display = Display.EMPTY;
            this.isSpecial = false;
        }

        public Builder color(ChatFormatting color)
        {
            this.color = TextColor.fromLegacyFormat(color);
            return this;
        }

        public Builder color(TextColor color)
        {
            this.color = color;
            return this;
        }

        public Builder color(String colorCode)
        {
            this.color = TextColor.parseColor(colorCode);
            return this;
        }

        public Builder tagline(Component tagline)
        {
            this.tagline = tagline;
            return this;
        }

        public Builder defaultTagline()
        {
            this.tagline = Power.defaultTagline(id);
            return this;
        }

        public Builder mainSpinjitzuColor(Vector3f color)
        {
            this.mainSpinjitzuColor = color;
            return this;
        }

        public Builder altSpinjitzuColor(Vector3f color)
        {
            this.altSpinjitzuColor = color;
            return this;
        }

        public Builder borderParticle(Supplier<? extends ParticleOptions> particle)
        {
            this.borderParticle = particle;
            return this;
        }

        public Builder hasSets()
        {
            this.hasSets = true;
            return this;
        }

        public Builder hasSets(boolean has)
        {
            this.hasSets = has;
            return this;
        }

        public Builder display(Display display)
        {
            this.display = display;
            return this;
        }

        public Builder defaultDisplay()
        {
            this.display = Display.withDefaultKeys(id);
            return this;
        }

        public Builder isSpecial() {
            this.isSpecial = true;
            return this;
        }

        public Builder isSpecial(boolean is) {
            this.isSpecial = is;
            return this;
        }

        public Power build()
        {
            return new Power(id, color, tagline, mainSpinjitzuColor, altSpinjitzuColor, borderParticle, hasSets, display, isSpecial);
        }
    }
}
