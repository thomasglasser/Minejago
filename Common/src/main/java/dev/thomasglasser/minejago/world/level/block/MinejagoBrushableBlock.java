package dev.thomasglasser.minejago.world.level.block;

import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBrushableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MinejagoBrushableBlock extends BrushableBlock {
    public MinejagoBrushableBlock(Block block, SoundEvent soundEvent, SoundEvent soundEvent2, Properties properties) {
        super(block, soundEvent, soundEvent2, properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MinejagoBrushableBlockEntity(pos, state);
    }
}
