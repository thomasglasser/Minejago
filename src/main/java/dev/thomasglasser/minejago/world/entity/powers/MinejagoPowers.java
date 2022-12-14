package dev.thomasglasser.minejago.world.entity.powers;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmorMaterials;
import dev.thomasglasser.minejago.world.item.armor.TrainingGiItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class MinejagoPowers {
    public static final DeferredRegister<Power> POWERS = DeferredRegister.create(Minejago.modLoc("powers"), Minejago.MOD_ID);
    public static Supplier<IForgeRegistry<Power>> REGISTRY = MinejagoPowers.POWERS.makeRegistry(RegistryBuilder::new);

    public static RegistryObject<Power> NONE = register("none", () -> new Power("none"), false);
    public static RegistryObject<Power> FIRE = register("fire", () -> new Power("fire", SpinjitzuParticleOptions.ELEMENT_ORANGE, SpinjitzuParticleOptions.ELEMENT_YELLOW, MinejagoParticleTypes.SPARKS), true);
    public static RegistryObject<Power> EARTH = register("earth", () -> new Power("earth", SpinjitzuParticleOptions.ELEMENT_BROWN, SpinjitzuParticleOptions.ELEMENT_TAN, MinejagoParticleTypes.ROCKS), true);
    public static RegistryObject<Power> LIGHTNING = register("lightning", () -> new Power("lightning", SpinjitzuParticleOptions.ELEMENT_BLUE, SpinjitzuParticleOptions.ELEMENT_LIGHT_BLUE, MinejagoParticleTypes.BOLTS), true);
    public static RegistryObject<Power> ICE = register("ice", () -> new Power("ice", SpinjitzuParticleOptions.ELEMENT_LIGHT_BLUE, SpinjitzuParticleOptions.ELEMENT_WHITE, MinejagoParticleTypes.SNOWS), true);

    public static RegistryObject<Power> register(String name, Supplier<Power> powerSupplier, boolean makeSets)
    {
        RegistryObject<Power> power = POWERS.register(name, powerSupplier);
        if (makeSets) new MinejagoArmor.PoweredArmorSet(name, "training_gi", MinejagoArmorMaterials.TRAINING_GI, TrainingGiItem.class);
        return power;
    }
}
