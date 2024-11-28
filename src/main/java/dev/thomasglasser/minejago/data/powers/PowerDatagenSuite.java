package dev.thomasglasser.minejago.data.powers;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.Nullable;

public class PowerDatagenSuite {
    protected static Pattern replacerPattern = Pattern.compile("(\\b[a-z](?!\\s))");

    protected String modId;
    protected @Nullable BiConsumer<String, String> mainTranslationConsumer;
    protected List<Pair<ResourceKey<Power>, Power>> powers = new ArrayList<>();
    protected Map<Supplier<? extends ParticleOptions>, List<ResourceLocation>> particleDescriptionTextures = new HashMap<>();
    protected List<ResourceKey<Power>> setsToMake = new ArrayList<>();

    private final CompletableFuture<HolderLookup.Provider> registries;

    public PowerDatagenSuite(GatherDataEvent event, final String modId, @Nullable BiConsumer<String, String> translationConsumer) {
        this.modId = modId;
        this.mainTranslationConsumer = translationConsumer;
        this.generate();
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = event.getGenerator().getPackOutput();
        RegistrySetBuilder builder = (new RegistrySetBuilder()).add(MinejagoRegistries.POWER, (pContext) -> {
            this.powers.forEach((pair) -> pContext.register(pair.getFirst(), pair.getSecond()));
        });

        DatapackBuiltinEntriesProvider datapackBuiltinEntriesProvider = new DatapackBuiltinEntriesProvider(packOutput, event.getLookupProvider(), builder, Set.of(modId)) {
            public String getName() {
                String var10000 = super.getName();
                return "PowerDatagenSuite / " + var10000 + " " + modId;
            }
        };

        generator.addProvider(event.includeServer(), datapackBuiltinEntriesProvider);

        registries = datapackBuiltinEntriesProvider.getRegistryProvider();

        generator.addProvider(event.includeClient(), new ParticleDescriptionProvider(packOutput, event.getExistingFileHelper()) {
            @Override
            protected void addDescriptions() {
                particleDescriptionTextures.forEach((particle, textures) -> spriteSet(particle.get().getType(), textures.remove(0), textures.toArray(new ResourceLocation[] {})));
            }

            public String getName() {
                String var10000 = super.getName();
                return "PowerDatagenSuite / " + var10000 + " " + modId;
            }
        });
        generator.addProvider(event.includeClient(), new ItemModelProvider(packOutput, modId, event.getExistingFileHelper()) {
            @Override
            protected void registerModels() {
                setsToMake.forEach(key -> MinejagoArmors.POWER_SETS.forEach(armorSet -> armorSet.getAll().forEach(item -> {
                    String path = key.location().getPath() + "_" + item.getId().getPath();
                    singleTexture("item/minejago_armor/" + path, mcLoc("item/generated"), "layer0", modLoc("item/" + path));
                })));
            }

            public String getName() {
                String var10000 = super.getName();
                return "PowerDatagenSuite / " + var10000 + " " + modId;
            }
        });
    }

    public PowerDatagenSuite(GatherDataEvent event, String modId) {
        this(event, modId, null);
    }

    public void generate() {}

    public PowerDatagenSuite makePowerSuite(ResourceKey<Power> key, Consumer<Power.Builder> builderConsumer, @Nullable Supplier<? extends ParticleOptions> borderParticle, List<ResourceLocation> particleTextures, boolean hasSets, Consumer<PowerConfig> configConsumer) {
        Power.Builder builder = Power.builder(key);

        builderConsumer.accept(builder);

        Power power = builder.borderParticle(borderParticle).hasSets(hasSets).build();
        powers.add(Pair.of(key, power));

        if (borderParticle != null && !particleTextures.isEmpty()) particleDescriptionTextures.put(borderParticle, particleTextures);

        PowerConfig config = new PowerConfig();
        configConsumer.accept(config);

        String nameKey = Util.makeDescriptionId("power", key.location());
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

            if (taglineKey != null) {
                translation = Objects.requireNonNullElse(config.mainTaglineTranslation, "");
                this.mainTranslationConsumer.accept(taglineKey, translation);
            }

            if (loreKey != null) {
                translation = Objects.requireNonNullElse(config.mainLoreTranslation, "");
                this.mainTranslationConsumer.accept(loreKey, translation);
            }

            if (descKey != null) {
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

    public PowerDatagenSuite makePowerSuite(ResourceKey<Power> key, Consumer<Power.Builder> builderConsumer, Supplier<? extends ParticleOptions> borderParticle, String texturePrefix, int textureCount, boolean hasSets, Consumer<PowerConfig> configConsumer) {
        List<ResourceLocation> textures = new ArrayList<>();
        for (int i = 0; i < textureCount; i++) {
            textures.add(modLoc(texturePrefix + "_" + i));
        }
        return makePowerSuite(key, builderConsumer, borderParticle, textures, hasSets, configConsumer);
    }

    public PowerDatagenSuite makePowerSuite(ResourceKey<Power> key, Consumer<Power.Builder> builderConsumer, boolean hasSets, Consumer<PowerConfig> configConsumer) {
        return makePowerSuite(key, builderConsumer, null, List.of(), hasSets, configConsumer);
    }

    public PowerDatagenSuite makePowerSuite(ResourceKey<Power> key) {
        return makePowerSuite(key, builder -> {}, null, List.of(), false, powerConfig -> {});
    }

    public static PowerDatagenSuite create(GatherDataEvent event, String modId) {
        return new PowerDatagenSuite(event, modId);
    }

    public static PowerDatagenSuite create(GatherDataEvent event, String modId, @Nullable BiConsumer<String, String> translationConsumer) {
        return new PowerDatagenSuite(event, modId, translationConsumer);
    }

    public CompletableFuture<HolderLookup.Provider> getRegistryProvider() {
        return registries;
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

        protected PowerConfig() {}

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

    public record AltTranslation(BiConsumer<String, String> consumer, String translation) {
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

    protected ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(modId, path);
    }
}
