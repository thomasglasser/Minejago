package dev.thomasglasser.minejago.world.entity.powers;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.registration.registries.DatapackRegistry;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
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

    private static ResourceKey<Power> create(String id)
    {
        return ResourceKey.create(MinejagoRegistries.POWER, Minejago.modLoc(id));
    }

    public static void init() {}

    public static void bootstrap(BootstapContext<Power> context)
    {
        context.register(NONE, new Power(Minejago.modLoc("none")));
        context.register(ICE, new Power(Minejago.modLoc("ice"), ChatFormatting.WHITE, SpinjitzuParticleOptions.ELEMENT_LIGHT_BLUE, SpinjitzuParticleOptions.ELEMENT_WHITE, MinejagoParticleTypes.SNOWS, true, true, new Power.Display(Component.translatable(Util.makeDescriptionId("power", Minejago.modLoc("ice")) + ".lore"), Component.translatable(Util.makeDescriptionId("power", Minejago.modLoc("ice")) + ".subtitle"), Component.translatable(Util.makeDescriptionId("power", Minejago.modLoc("ice")) + ".desc"))));
        context.register(EARTH, new Power(Minejago.modLoc("earth"), ChatFormatting.YELLOW, SpinjitzuParticleOptions.ELEMENT_BROWN, SpinjitzuParticleOptions.ELEMENT_TAN, MinejagoParticleTypes.ROCKS, true, true, new Power.Display(Component.translatable(Util.makeDescriptionId("power", Minejago.modLoc("earth")) + ".lore"), Component.translatable(Util.makeDescriptionId("power", Minejago.modLoc("earth")) + ".subtitle"), Component.translatable(Util.makeDescriptionId("power", Minejago.modLoc("earth")) + ".desc"))));
        context.register(FIRE, new Power(Minejago.modLoc("fire"), ChatFormatting.RED, SpinjitzuParticleOptions.ELEMENT_ORANGE, SpinjitzuParticleOptions.ELEMENT_YELLOW, MinejagoParticleTypes.SPARKS, true, true, new Power.Display(Component.translatable(Util.makeDescriptionId("power", Minejago.modLoc("fire")) + ".lore"), Component.translatable(Util.makeDescriptionId("power", Minejago.modLoc("fire")) + ".subtitle"), Component.translatable(Util.makeDescriptionId("power", Minejago.modLoc("fire")) + ".desc"))));
        context.register(LIGHTNING, new Power(Minejago.modLoc("lightning"), ChatFormatting.BLUE, SpinjitzuParticleOptions.ELEMENT_BLUE, SpinjitzuParticleOptions.ELEMENT_LIGHT_BLUE, MinejagoParticleTypes.BOLTS, true, true, new Power.Display(Component.translatable(Util.makeDescriptionId("power", Minejago.modLoc("lightning")) + ".lore"), Component.translatable(Util.makeDescriptionId("power", Minejago.modLoc("lightning")) + ".subtitle"), Component.translatable(Util.makeDescriptionId("power", Minejago.modLoc("lightning")) + ".desc"))));
    }

    public static List<ItemStack> getArmorForAll(HolderLookup.Provider access)
    {
        List<ItemStack> list = new ArrayList<>();
        access.lookupOrThrow(MinejagoRegistries.POWER).listElements().forEach(powerReference ->
        {
            Power power = powerReference.value();
            if (power.makeSets)
            {
                MinejagoArmor.POWER_SETS.forEach(armorSet ->
                        armorSet.getAll().forEach(item ->
                            list.add(PowerUtils.setPower(new ItemStack(item.get()), powerReference.key()))
                        ));
            }
        });
        return list;
    }
}
