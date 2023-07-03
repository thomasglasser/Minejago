package dev.thomasglasser.minejago.data.worldgen.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinejagoBiomeModifierSerializers
{
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Minejago.MOD_ID);

    public static final RegistryObject<Codec<AddSpawnCostsBiomeModifier>> ADD_SPAWN_COSTS = BIOME_MODIFIER_SERIALIZERS.register("add_spawn_costs", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(AddSpawnCostsBiomeModifier::biomes),
                    ForgeRegistries.ENTITY_TYPES.getCodec().fieldOf("entity_type").forGetter(AddSpawnCostsBiomeModifier::entityType),
                    MobSpawnSettings.MobSpawnCost.CODEC.fieldOf("spawn_costs").forGetter(AddSpawnCostsBiomeModifier::cost)
            ).apply(builder, AddSpawnCostsBiomeModifier::new))
    );
}
