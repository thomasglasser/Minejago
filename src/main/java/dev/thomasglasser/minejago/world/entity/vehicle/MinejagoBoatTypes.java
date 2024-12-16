package dev.thomasglasser.minejago.world.entity.vehicle;

import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import java.util.function.Supplier;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class MinejagoBoatTypes {
    public static final EnumProxy<Boat.Type> ENCHANTED = new EnumProxy<>(Boat.Type.class,
            (Supplier<Block>) () -> MinejagoBlocks.ENCHANTED_WOOD_SET.planks().get(),
            "minejago:enchanted",
            (Supplier<Item>) () -> MinejagoBlocks.ENCHANTED_WOOD_SET.boatItem().get(),
            (Supplier<Item>) () -> MinejagoBlocks.ENCHANTED_WOOD_SET.chestBoatItem().get(),
            (Supplier<Item>) () -> Items.STICK,
            false);
}
