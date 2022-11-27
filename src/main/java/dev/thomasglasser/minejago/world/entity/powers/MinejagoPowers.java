package dev.thomasglasser.minejago.world.entity.powers;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class MinejagoPowers {
    public static final DeferredRegister<Power> POWERS = DeferredRegister.create(new ResourceLocation(Minejago.MOD_ID, "powers"), Minejago.MOD_ID);
    public static final Supplier<IForgeRegistry<Power>> POWERS_REGISTRY = POWERS.makeRegistry(RegistryBuilder::new);

    public static RegistryObject<Power> EMPTY = POWERS.register("empty", Power::new);
    public static RegistryObject<Power> FIRE = POWERS.register("fire", () -> new Power(SpinjitzuParticleOptions.ELEMENT_ORANGE, SpinjitzuParticleOptions.ELEMENT_YELLOW, MinejagoParticleTypes.SPARKS.get()));
    public static RegistryObject<Power> EARTH = POWERS.register("earth", () -> new Power(SpinjitzuParticleOptions.ELEMENT_ORANGE, SpinjitzuParticleOptions.ELEMENT_YELLOW, MinejagoParticleTypes.SPARKS.get()));
    public static RegistryObject<Power> LIGHTNING = POWERS.register("lightning", () -> new Power(SpinjitzuParticleOptions.ELEMENT_ORANGE, SpinjitzuParticleOptions.ELEMENT_YELLOW, MinejagoParticleTypes.SPARKS.get()));
    public static RegistryObject<Power> ICE = POWERS.register("ice", () -> new Power(SpinjitzuParticleOptions.ELEMENT_ORANGE, SpinjitzuParticleOptions.ELEMENT_YELLOW, MinejagoParticleTypes.SPARKS.get()));
}
