package dev.thomasglasser.minejago.world.entity.power;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.registration.registries.DatapackRegistry;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MinejagoPowers {

    public static final DatapackRegistry<Power> POWERS = DatapackRegistry.builder(MinejagoRegistries.POWER).withElementCodec(Power.CODEC).withNetworkCodec(Power.CODEC).withBootstrap(MinejagoPowers::bootstrap).build();

    public static final ResourceKey<Power> NONE = create("none");
    public static final ResourceKey<Power> ICE = create("ice");
    public static final ResourceKey<Power> EARTH = create("earth");
    public static final ResourceKey<Power> FIRE = create("fire");
    public static final ResourceKey<Power> LIGHTNING = create("lightning");
    public static final ResourceKey<Power> CREATION = create("creation");

    private static ResourceKey<Power> create(String id)
    {
        return ResourceKey.create(MinejagoRegistries.POWER, Minejago.modLoc(id));
    }

    public static void init() {}

    public static void bootstrap(BootstapContext<Power> context)
    {
        context.register(NONE, Power.builder("none").build());
        context.register(ICE, Power.builder("ice").color(ChatFormatting.WHITE).defaultTagline().mainSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_LIGHT_BLUE).altSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_WHITE).borderParticle(MinejagoParticleTypes.SNOWS).hasSets().defaultDisplay().build());
        context.register(EARTH, Power.builder("earth").color(ChatFormatting.DARK_GRAY).defaultTagline().mainSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_BROWN).altSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_TAN).borderParticle(MinejagoParticleTypes.ROCKS).hasSets().defaultDisplay().build());
        context.register(FIRE, Power.builder("fire").color(ChatFormatting.RED).defaultTagline().mainSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_ORANGE).altSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_YELLOW).borderParticle(MinejagoParticleTypes.SPARKS).hasSets().defaultDisplay().build());
        context.register(LIGHTNING, Power.builder("lightning").color(ChatFormatting.BLUE).defaultTagline().mainSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_BLUE).altSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_LIGHT_BLUE).borderParticle(MinejagoParticleTypes.BOLTS).hasSets().defaultDisplay().build());
        context.register(CREATION, Power.builder("creation").color(ChatFormatting.GOLD).mainSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_DARK_GOLD).altSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_GOLD).borderParticle(MinejagoParticleTypes.SPARKLES).defaultDisplay().isSpecial().build());
    }

    public static List<ItemStack> getArmorForAll(HolderLookup.Provider access)
    {
        List<ItemStack> list = new ArrayList<>();
        access.lookupOrThrow(MinejagoRegistries.POWER).listElements().forEach(powerReference ->
        {
            Power power = powerReference.value();
            if (power.hasSets)
            {
                MinejagoArmors.POWER_SETS.forEach(armorSet ->
                        armorSet.getAll().forEach(item ->
                            list.add(PowerUtils.setPower(new ItemStack(item.get()), powerReference.key()))
                        ));
            }
        });
        return list;
    }

    public static HolderLookup.Provider getBasePowers()
    {
        final RegistryAccess.Frozen access = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        final RegistrySetBuilder builder = new RegistrySetBuilder();
        MinejagoPowers.POWERS.addToSet(builder);
        return builder.build(access);
    }
}
