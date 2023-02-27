package dev.thomasglasser.minejago.world.entity.powers;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.registration.registries.DatapackRegistry;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmorMaterials;
import dev.thomasglasser.minejago.world.item.armor.TrainingGiItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;

public class MinejagoPowers {

    public static final DatapackRegistry<Power> POWERS = DatapackRegistry.builder(MinejagoRegistries.POWER).withElementCodec(Power.CODEC).withNetworkCodec(Power.CODEC).withBootstrap(MinejagoPowers::bootstrap).build();

    public static final ResourceKey<Power> NONE = create("none");
    public static final ResourceKey<Power> ICE = create("ice");
    public static final ResourceKey<Power> EARTH = create("earth");
    public static final ResourceKey<Power> FIRE = create("fire");
    public static final ResourceKey<Power> LIGHTNING = create("lightning");

    private static ResourceKey<Power> create(String id)
    {
        return ResourceKey.create(MinejagoRegistries.POWER, Minejago.modLoc(id));
    }

    public static void init() {
        final RegistryAccess.Frozen access = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        final RegistrySetBuilder builder = new RegistrySetBuilder();
        MinejagoPowers.POWERS.addToSet(builder);
        HolderLookup.Provider access1 = builder.build(access);

        access1.lookupOrThrow(MinejagoRegistries.POWER).listElements().forEach(powerReference ->
        {
            if (powerReference.value().makeSets)
            {
                MinejagoArmor.PoweredArmorSet.create(powerReference.value().getId(), "training", MinejagoArmorMaterials.TRAINING_GI, TrainingGiItem.class);
            }
        });
    }

    public static void bootstrap(BootstapContext<Power> context)
    {
        context.register(NONE, new Power(Minejago.modLoc("none")));
        context.register(ICE, new Power(Minejago.modLoc("ice"), SpinjitzuParticleOptions.ELEMENT_LIGHT_BLUE, SpinjitzuParticleOptions.ELEMENT_WHITE, MinejagoParticleTypes.SNOWS, true));
        context.register(EARTH, new Power(Minejago.modLoc("earth"), SpinjitzuParticleOptions.ELEMENT_BROWN, SpinjitzuParticleOptions.ELEMENT_TAN, MinejagoParticleTypes.ROCKS, true));
        context.register(FIRE, new Power(Minejago.modLoc("fire"), SpinjitzuParticleOptions.ELEMENT_ORANGE, SpinjitzuParticleOptions.ELEMENT_YELLOW, MinejagoParticleTypes.SPARKS, true));
        context.register(LIGHTNING, new Power(Minejago.modLoc("lightning"), SpinjitzuParticleOptions.ELEMENT_BLUE, SpinjitzuParticleOptions.ELEMENT_LIGHT_BLUE, MinejagoParticleTypes.BOLTS, true));
    }
}
