package dev.thomasglasser.minejago.world.entity.power;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.Nullable;

public class Power {
    public static final Codec<Power> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TextColor.CODEC.optionalFieldOf("power_color", TextColor.fromLegacyFormat(ChatFormatting.GRAY)).forGetter(Power::getColor),
            ComponentSerialization.CODEC.optionalFieldOf("tagline", Component.empty()).forGetter(Power::getTagline),
            BuiltInRegistries.PARTICLE_TYPE.byNameCodec().optionalFieldOf("border_particle").forGetter(Power::getBorderParticleType),
            Codec.BOOL.optionalFieldOf("has_sets", false).forGetter(Power::hasSets),
            Display.CODEC.optionalFieldOf("display", Display.EMPTY).forGetter(Power::getDisplay),
            Codec.BOOL.optionalFieldOf("is_special", false).forGetter(Power::isSpecial)).apply(instance, Power::new));

    protected Component tagline;
    @Nullable
    protected Supplier<? extends ParticleOptions> borderParticle;
    protected boolean hasSets;
    protected Display display;
    protected boolean isSpecial;

    private final TextColor color;

    public static Builder builder(ResourceKey<Power> id) {
        return new Builder(id.location());
    }

    public static Builder builder(ResourceLocation id) {
        return new Builder(id);
    }

    protected Power(TextColor color, @Nullable Component tagline, @Nullable Supplier<? extends ParticleOptions> borderParticle, boolean hasSets, @Nullable Display display, boolean isSpecial) {
        this.color = color;
        this.tagline = tagline == null ? Component.empty() : tagline;
        this.borderParticle = borderParticle;
        this.hasSets = hasSets;
        this.display = display == null ? Display.EMPTY : display;
        this.isSpecial = isSpecial;
    }

    protected Power(TextColor color, @Nullable Component tagline, Optional<ParticleType<?>> borderParticle, boolean hasSets, Display display, boolean isSpecial) {
        this(color, tagline, borderParticle.orElse(null) instanceof ParticleOptions ? () -> (ParticleOptions) borderParticle.orElse(null) : null, hasSets, display, isSpecial);
    }

    @Nullable
    public ParticleOptions getBorderParticle() {
        return borderParticle == null ? null : borderParticle.get();
    }

    @Nullable
    public Optional<ParticleType<?>> getBorderParticleType() {
        return borderParticle != null ? Optional.of(borderParticle.get().getType()) : Optional.empty();
    }

    public TextColor getColor() {
        return color;
    }

    public boolean hasSets() {
        return hasSets;
    }

    public Component getFormattedName(Registry<Power> registry) {
        MutableComponent component = Component.translatable(Util.makeDescriptionId("power", registry.getKey(this)));
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

    public ResourceLocation getIcon(Registry<Power> registry) {
        return registry.getKey(this).withPrefix("textures/power/").withSuffix(".png");
    }

    public boolean is(TagKey<Power> tag, Registry<Power> registry) {
        return registry.getTag(tag).orElseThrow().contains(registry.getHolderOrThrow(registry.getResourceKey(this).orElseThrow()));
    }

    public boolean is(Power power) {
        return power == this;
    }

    public boolean is(Supplier<Power> power) {
        return this == power.get();
    }

    public boolean is(ResourceKey<Power> key, Registry<Power> registry) {
        return registry.getOrThrow(key) == this;
    }

    public record Display(Component lore, Component description) {
        public static final Display EMPTY = new Display(Component.empty(), Component.empty());

        public static final Codec<Display> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ComponentSerialization.CODEC.optionalFieldOf("lore", Component.empty()).forGetter(Display::lore),
                ComponentSerialization.CODEC.optionalFieldOf("description", Component.empty()).forGetter(Display::description)).apply(instance, Display::new));

        public static Display withDefaultKeys(ResourceLocation id) {
            return new Display(Component.translatable(Util.makeDescriptionId("power", id) + ".lore"), Component.translatable(Util.makeDescriptionId("power", id) + ".description"));
        }
    }

    public static Component defaultTagline(ResourceLocation id) {
        return Component.translatable(Util.makeDescriptionId("power", id) + ".tagline");
    }

    public static class Builder {
        private final ResourceLocation id;
        private TextColor color;
        protected Supplier<? extends ParticleOptions> borderParticle;
        protected boolean hasSets;
        protected Display display;
        protected boolean isSpecial;
        protected Component tagline;

        public Builder(ResourceLocation id) {
            this.id = id;
            this.color = TextColor.fromLegacyFormat(ChatFormatting.GRAY);
            this.tagline = Component.empty();
            this.borderParticle = null;
            this.hasSets = false;
            this.display = Display.EMPTY;
            this.isSpecial = false;
        }

        public Builder color(ChatFormatting color) {
            this.color = TextColor.fromLegacyFormat(color);
            return this;
        }

        public Builder color(TextColor color) {
            this.color = color;
            return this;
        }

        public Builder color(String colorCode) {
            this.color = TextColor.parseColor(colorCode).result().orElse(TextColor.fromLegacyFormat(ChatFormatting.GRAY));
            return this;
        }

        public Builder tagline(Component tagline) {
            this.tagline = tagline;
            return this;
        }

        public Builder defaultTagline() {
            this.tagline = Power.defaultTagline(id);
            return this;
        }

        public Builder borderParticle(Supplier<? extends ParticleOptions> particle) {
            this.borderParticle = particle;
            return this;
        }

        public Builder hasSets() {
            this.hasSets = true;
            return this;
        }

        public Builder hasSets(boolean has) {
            this.hasSets = has;
            return this;
        }

        public Builder display(Display display) {
            this.display = display;
            return this;
        }

        public Builder defaultDisplay() {
            this.display = Display.withDefaultKeys(id);
            return this;
        }

        public Builder isSpecial() {
            this.isSpecial = true;
            return this;
        }

        public Power build() {
            return new Power(color, tagline, borderParticle, hasSets, display, isSpecial);
        }
    }
}
