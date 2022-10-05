package dev.thomasglasser.minejago.world.level.block.entity;

import dev.thomasglasser.minejago.MinejagoMod;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinejagoBannerPatterns
{
    public static final DeferredRegister<BannerPattern> BANNER_PATTERNS = DeferredRegister.create(Registry.BANNER_PATTERN_REGISTRY, MinejagoMod.MODID);

    public static final RegistryObject<BannerPattern> FOUR_WEAPONS_LEFT = BANNER_PATTERNS.register("four_weapons_left", () -> new BannerPattern("four_weapons_left"));
    public static final RegistryObject<BannerPattern> FOUR_WEAPONS_RIGHT = BANNER_PATTERNS.register("four_weapons_right", () -> new BannerPattern("four_weapons_right"));
    public static final RegistryObject<BannerPattern> EDGE_LINES = BANNER_PATTERNS.register("edge_lines", () -> new BannerPattern("edge_lines"));
}
