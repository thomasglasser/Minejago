package dev.thomasglasser.minejago.data.powers;

import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ForgePowerDatagenSuite extends BasePowerDatagenSuite
{
    private final CompletableFuture<HolderLookup.Provider> registries;

    public ForgePowerDatagenSuite(GatherDataEvent event, String modid) {
        this(event, modid, null);
    }

    public ForgePowerDatagenSuite(GatherDataEvent event, final String modid, @Nullable BiConsumer<String, String> translationConsumer) {
        super(modid, translationConsumer);
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = event.getGenerator().getPackOutput();
        RegistrySetBuilder builder = (new RegistrySetBuilder()).add(MinejagoRegistries.POWER, (pContext) -> {
            this.powers.forEach((pair) -> pContext.register(pair.getFirst(), pair.getSecond()));
        });

        DatapackBuiltinEntriesProvider datapackBuiltinEntriesProvider = new DatapackBuiltinEntriesProvider(packOutput, event.getLookupProvider(), builder, Set.of(modid)) {
            public String getName() {
                String var10000 = super.getName();
                return "PowerDatagenSuite / " + var10000 + " " + modid;
            }
        };

        generator.addProvider(event.includeServer(), datapackBuiltinEntriesProvider);

        registries = datapackBuiltinEntriesProvider.getRegistryProvider();

        generator.addProvider(event.includeClient(), new ParticleDescriptionProvider(packOutput, event.getExistingFileHelper()) {
            @Override
            protected void addDescriptions() {
                particleDescriptionTextures.forEach((particle, textures) ->
                {
                    spriteSet(particle.get().getType(), textures.remove(0), textures.toArray(new ResourceLocation[] {}));
                });
            }

            public String getName() {
                String var10000 = super.getName();
                return "PowerDatagenSuite / " + var10000 + " " + modid;
            }
        });
        generator.addProvider(event.includeClient(), new ItemModelProvider(packOutput, modid, event.getExistingFileHelper()) {
            @Override
            protected void registerModels() {
                setsToMake.forEach(key ->
                        MinejagoArmors.POWER_SETS.forEach(armorSet ->
                                armorSet.getAll().forEach(item ->
                                {
                                    String path = key.location().getPath() + "_" + BuiltInRegistries.ITEM.getKey(item.get()).getPath();
                                    singleTexture("item/minejago_armor/" + path, mcLoc("item/generated"), "layer0", modLoc("item/" + path));
                                })));
            }

            public String getName() {
                String var10000 = super.getName();
                return "PowerDatagenSuite / " + var10000 + " " + modid;
            }
        });
    }

    public static ForgePowerDatagenSuite create(GatherDataEvent event, String modid) {
        return new ForgePowerDatagenSuite(event, modid);
    }

    public static ForgePowerDatagenSuite create(GatherDataEvent event, String modid, @Nullable BiConsumer<String, String> translationConsumer) {
        return new ForgePowerDatagenSuite(event, modid, translationConsumer);
    }

    public CompletableFuture<HolderLookup.Provider> getRegistryProvider() {
        return registries;
    }
}
