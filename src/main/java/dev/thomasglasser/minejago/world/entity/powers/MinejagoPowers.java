package dev.thomasglasser.minejago.world.entity.powers;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class MinejagoPowers {
    public static final DeferredRegister<Power> POWERS = DeferredRegister.create(Minejago.modLoc("powers"), Minejago.MOD_ID);

    public static RegistryObject<Power> NONE = POWERS.register("none", () -> new Power("none"));
    public static RegistryObject<Power> FIRE = POWERS.register("fire", () -> new Power("fire", SpinjitzuParticleOptions.ELEMENT_ORANGE, SpinjitzuParticleOptions.ELEMENT_YELLOW, MinejagoParticleTypes.SPARKS));
    public static RegistryObject<Power> EARTH = POWERS.register("earth", () -> new Power("earth", SpinjitzuParticleOptions.ELEMENT_BROWN, SpinjitzuParticleOptions.ELEMENT_TAN, MinejagoParticleTypes.ROCKS));
    public static RegistryObject<Power> LIGHTNING = POWERS.register("lightning", () -> new Power("lightning", SpinjitzuParticleOptions.ELEMENT_BLUE, SpinjitzuParticleOptions.ELEMENT_LIGHT_BLUE, MinejagoParticleTypes.BOLTS));
    public static RegistryObject<Power> ICE = POWERS.register("ice", () -> new Power("ice", SpinjitzuParticleOptions.ELEMENT_LIGHT_BLUE, SpinjitzuParticleOptions.ELEMENT_WHITE, MinejagoParticleTypes.SNOWS));
}
