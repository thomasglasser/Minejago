package dev.thomasglasser.minejago.world.entity.element;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public record Element(TextColor color, Component tagline, Optional<ParticleOptions> borderParticle, boolean hasSets, boolean hasSpecialSets, Display display) {

    public static final Codec<Element> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TextColor.CODEC.optionalFieldOf("color", TextColor.fromLegacyFormat(ChatFormatting.GRAY)).forGetter(Element::color),
            ComponentSerialization.CODEC.optionalFieldOf("tagline", Component.empty()).forGetter(Element::tagline),
            ParticleTypes.CODEC.optionalFieldOf("border_particle").forGetter(Element::borderParticle),
            Codec.BOOL.optionalFieldOf("has_sets", false).forGetter(Element::hasSets),
            Codec.BOOL.optionalFieldOf("has_special_sets", false).forGetter(Element::hasSpecialSets),
            Display.CODEC.optionalFieldOf("display", Display.EMPTY).forGetter(Element::display)).apply(instance, Element::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, Element> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodecWithRegistries(TextColor.CODEC), Element::color,
            ComponentSerialization.STREAM_CODEC, Element::tagline,
            ByteBufCodecs.optional(ParticleTypes.STREAM_CODEC), Element::borderParticle,
            ByteBufCodecs.BOOL, Element::hasSets,
            ByteBufCodecs.BOOL, Element::hasSpecialSets,
            Display.STREAM_CODEC, Element::display,
            Element::new);

    public static ResourceLocation getIcon(ResourceKey<Element> key) {
        return key.location().withPrefix("textures/" + MinejagoRegistries.ELEMENT.location().getPath() + "/").withSuffix(".png");
    }

    public static ResourceLocation getIcon(Holder<Element> element) {
        return getIcon(element.unwrapKey().orElseThrow());
    }

    public static Component getFormattedName(Holder<Element> element) {
        ResourceKey<Element> key = element.unwrapKey().orElseThrow();
        MutableComponent component = Component.translatable(key.location().toLanguageKey(key.registry().getPath()));
        component.withStyle((component.getStyle().withColor(element.value().color())));
        return component;
    }

    public static Builder builder(ResourceKey<Element> id) {
        return new Builder(id.location());
    }

    public static Builder builder(ResourceLocation id) {
        return new Builder(id);
    }
    public static class Builder {
        protected final ResourceLocation id;
        protected TextColor color;
        protected Component tagline;
        protected ParticleOptions borderParticle;
        protected boolean hasSets;
        protected boolean hasSpecialSets;
        protected Display display;

        public Builder(ResourceLocation id) {
            this.id = id;
            this.color = TextColor.fromLegacyFormat(ChatFormatting.GRAY);
            this.tagline = Component.empty();
            this.borderParticle = null;
            this.hasSets = false;
            this.hasSpecialSets = false;
            this.display = Display.EMPTY;
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
            this.tagline = Component.translatable(id.toLanguageKey(MinejagoRegistries.ELEMENT.location().getPath()) + ".tagline");
            return this;
        }

        public Builder borderParticle(ParticleOptions particle) {
            this.borderParticle = particle;
            return this;
        }

        public Builder borderParticle(Supplier<? extends ParticleOptions> particle) {
            this.borderParticle = particle.get();
            return this;
        }

        public Builder hasSets() {
            this.hasSets = true;
            return this;
        }

        public Builder hasSpecialSets() {
            this.hasSpecialSets = true;
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

        public Element build() {
            return new Element(color, tagline, Optional.ofNullable(borderParticle), hasSets, hasSpecialSets, display);
        }
    }

    public record Display(Component lore, Component description) {
        public static final Display EMPTY = new Display(Component.empty(), Component.empty());

        public static final Codec<Display> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ComponentSerialization.CODEC.optionalFieldOf("lore", Component.empty()).forGetter(Display::lore),
                ComponentSerialization.CODEC.optionalFieldOf("description", Component.empty()).forGetter(Display::description)).apply(instance, Display::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, Display> STREAM_CODEC = StreamCodec.composite(
                ComponentSerialization.STREAM_CODEC, Display::lore,
                ComponentSerialization.STREAM_CODEC, Display::description,
                Display::new);

        public static Display withDefaultKeys(ResourceLocation id) {
            return new Display(Component.translatable(id.toLanguageKey(MinejagoRegistries.ELEMENT.location().getPath()) + ".lore"), Component.translatable(id.toLanguageKey(MinejagoRegistries.ELEMENT.location().getPath()) + ".description"));
        }
    }
}
