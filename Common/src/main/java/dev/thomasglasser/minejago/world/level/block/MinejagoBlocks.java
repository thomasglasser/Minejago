package dev.thomasglasser.minejago.world.level.block;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.BlockRegistryObject;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MinejagoBlocks
{
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, Minejago.MOD_ID);

    public static final BlockRegistryObject<Block> TEAPOT = registerAndWrap("teapot", () -> new TeapotBlock(BlockBehaviour.Properties.of(Material.CLAY, MaterialColor.COLOR_ORANGE).instabreak().noOcclusion()));
    public static final BlockRegistryObject<Block> JASPOT = registerAndWrap("jaspot", () -> new TeapotBlock(BlockBehaviour.Properties.of(Material.CLAY, MaterialColor.COLOR_LIGHT_BLUE).instabreak().noOcclusion()));
    public static final Map<DyeColor, BlockRegistryObject<Block>> TEAPOTS = teapots();

    private static BlockRegistryObject<Block> registerAndWrap(String name, Supplier<Block> supplier)
    {
        RegistryObject<Block> object = BLOCKS.register(name, supplier);
        return BlockRegistryObject.wrap(object);
    }

    private static Map<DyeColor, BlockRegistryObject<Block>> teapots()
    {
        Map<DyeColor, BlockRegistryObject<Block>> map = new HashMap<>();
        for (DyeColor color : DyeColor.values())
        {
            map.put(color, registerAndWrap(color.getName() + "_teapot", () -> new TeapotBlock(BlockBehaviour.Properties.of(Material.CLAY, color).instabreak().noOcclusion())));
        }
        return map;
    }

    public static void init() {}
}
