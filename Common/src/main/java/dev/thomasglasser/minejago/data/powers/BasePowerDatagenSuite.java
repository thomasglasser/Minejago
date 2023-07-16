package dev.thomasglasser.minejago.data.powers;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.world.entity.power.Power;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasePowerDatagenSuite
{
    protected static Pattern replacerPattern = Pattern.compile("(\\b[a-z](?!\\s))");

    protected String modId;
    protected @Nullable BiConsumer<String, String> mainTranslationConsumer;
    protected List<Pair<ResourceKey<Power>, Power>> powers = new ArrayList<>();
    protected Map<Supplier<? extends ParticleOptions>, List<ResourceLocation>> particleDescriptionTextures = new HashMap<>();
    protected List<ResourceKey<Power>> setsToMake = new ArrayList<>();

    public BasePowerDatagenSuite(String modId, @Nullable BiConsumer<String, String> translationConsumer) {
        this.modId = modId;
        this.mainTranslationConsumer = translationConsumer;
        this.generate();
    }

    public void generate() {
    }

    public BasePowerDatagenSuite makePowerSuite(ResourceKey<Power> key, Consumer<Power.Builder> builderConsumer, @Nullable Supplier<? extends ParticleOptions> borderParticle, List<ResourceLocation> particleTextures, boolean hasSets, Consumer<PowerConfig> configConsumer)
    {
        Power.Builder builder = Power.builder(key.location());

        builderConsumer.accept(builder);

        Power power = builder.borderParticle(borderParticle).hasSets(hasSets).build();
        powers.add(Pair.of(key, power));
        
        if (borderParticle != null && !particleTextures.isEmpty()) particleDescriptionTextures.put(borderParticle, particleTextures);

        PowerConfig config = new PowerConfig();
        configConsumer.accept(config);

        String nameKey = power.getDescriptionId();
        String taglineKey = power.getTagline().getContents() instanceof TranslatableContents translatableContents ? translatableContents.getKey() : null;
        String loreKey = power.getDisplay().lore().getContents() instanceof TranslatableContents translatableContents ? translatableContents.getKey() : null;
        String descKey = power.getDisplay().description().getContents() instanceof TranslatableContents translatableContents ? translatableContents.getKey() : null;

        if (this.mainTranslationConsumer != null) {
            String translation;

            if (config.mainNameTranslation == null) {
                Matcher var10000 = replacerPattern.matcher(key.location().getPath().replace("_", " "));
                translation = var10000.replaceAll((matcher) -> matcher.group().toUpperCase());
            } else {
                translation = config.mainNameTranslation;
            }
            this.mainTranslationConsumer.accept(nameKey, translation);

            if (taglineKey != null)
            {
                translation = Objects.requireNonNullElse(config.mainTaglineTranslation, "");
                this.mainTranslationConsumer.accept(taglineKey, translation);
            }

            if (loreKey != null)
            {
                translation = Objects.requireNonNullElse(config.mainLoreTranslation, "");
                this.mainTranslationConsumer.accept(loreKey, translation);
            }

            if (descKey != null)
            {
                translation = Objects.requireNonNullElse(config.mainDescTranslation, "");
                this.mainTranslationConsumer.accept(descKey, translation);
            }
        }

        config.altNameTranslations.forEach((altTranslation) -> {
            altTranslation.finish(nameKey);
        });

        if (taglineKey != null)
            config.altTaglineTranslations.forEach((altTranslation) -> altTranslation.finish(taglineKey));

        if (loreKey != null)
            config.altLoreTranslations.forEach((altTranslation) -> altTranslation.finish(loreKey));

        if (descKey != null)
            config.altDescTranslations.forEach((altTranslation) -> altTranslation.finish(descKey));

        if (hasSets) setsToMake.add(key);

        return this;
    }

    public BasePowerDatagenSuite makePowerSuite(ResourceKey<Power> key, Consumer<Power.Builder> builderConsumer, Supplier<? extends ParticleOptions> borderParticle, String texturePrefix, int textureCount, boolean hasSets, Consumer<PowerConfig> configConsumer)
    {
        List<ResourceLocation> textures = new ArrayList<>();
        for (int i = 0; i < textureCount; i++) {
            textures.add(modLoc(texturePrefix + "_" + i));
        }
        return makePowerSuite(key, builderConsumer, borderParticle, textures, hasSets, configConsumer);
    }

    public BasePowerDatagenSuite makePowerSuite(ResourceKey<Power> key, Consumer<Power.Builder> builderConsumer, boolean hasSets, Consumer<PowerConfig> configConsumer)
    {
        return makePowerSuite(key, builderConsumer, null, List.of(), hasSets, configConsumer);
    }

    public BasePowerDatagenSuite makePowerSuite(ResourceKey<Power> key)
    {
        return makePowerSuite(key, builder -> {}, null, List.of(), false, powerConfig -> {});
    }

    public static class PowerConfig {
        protected String mainNameTranslation;
        protected Set<AltTranslation> altNameTranslations = new HashSet<>();
        protected String mainTaglineTranslation;
        protected Set<AltTranslation> altTaglineTranslations = new HashSet<>();
        protected String mainLoreTranslation;
        protected Set<AltTranslation> altLoreTranslations = new HashSet<>();
        protected String mainDescTranslation;
        protected Set<AltTranslation> altDescTranslations = new HashSet<>();

        protected PowerConfig() {
        }

        public PowerConfig name(String translation) {
            this.mainNameTranslation = translation;
            return this;
        }
        public PowerConfig name(BiConsumer<String, String> translationConsumer, String translation) {
            this.altNameTranslations.add(new AltTranslation(translationConsumer, translation));
            return this;
        }
        public PowerConfig tagline(String translation) {
            this.mainTaglineTranslation = translation;
            return this;
        }
        public PowerConfig tagline(BiConsumer<String, String> translationConsumer, String translation) {
            this.altTaglineTranslations.add(new AltTranslation(translationConsumer, translation));
            return this;
        }
        public PowerConfig lore(String translation) {
            this.mainLoreTranslation = translation;
            return this;
        }
        public PowerConfig lore(BiConsumer<String, String> translationConsumer, String translation) {
            this.altLoreTranslations.add(new AltTranslation(translationConsumer, translation));
            return this;
        }
        public PowerConfig desc(String translation) {
            this.mainDescTranslation = translation;
            return this;
        }
        public PowerConfig desc(BiConsumer<String, String> translationConsumer, String translation) {
            this.altDescTranslations.add(new AltTranslation(translationConsumer, translation));
            return this;
        }
    }

    record AltTranslation(BiConsumer<String, String> consumer, String translation) {
        AltTranslation(BiConsumer<String, String> consumer, String translation) {
            this.consumer = consumer;
            this.translation = translation;
        }

        public void finish(String key) {
            this.consumer.accept(key, this.translation);
        }

        public BiConsumer<String, String> consumer() {
            return this.consumer;
        }

        public String translation() {
            return this.translation;
        }
    }

    protected ResourceLocation modLoc(String path)
    {
        return new ResourceLocation(modId, path);
    }
}
