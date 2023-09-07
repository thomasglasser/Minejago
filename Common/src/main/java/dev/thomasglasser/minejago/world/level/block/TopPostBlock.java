package dev.thomasglasser.minejago.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TopPostBlock extends HorizontalDirectionalBlock
{
    private static final VoxelShape SHAPE_NORTH = box(0.0, 0.0, 14.9, 16.0, 16.0, 16.0);
    private static final VoxelShape SHAPE_SOUTH = box(0.0, 0.0, 0.0, 16.0, 16.0, 1.1);
    private static final VoxelShape SHAPE_EAST = box(0.0, 0.0, 0.0, 1.1, 16.0, 16.0);
    private static final VoxelShape SHAPE_WEST = box(14.9, 0.0, 0.0, 16.0, 16.0, 16.0);

    public TopPostBlock(Properties properties) {
        super(properties.noOcclusion());
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            default -> SHAPE_EAST;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            case NORTH -> SHAPE_NORTH;
        };
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockPos blockPos = pos.relative(direction.getOpposite());
        BlockState otherPos = level.getBlockState(blockPos);
        return otherPos.isFaceSturdy(level, blockPos, direction.getOpposite(), SupportType.CENTER);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction horizontalDirection = context.getHorizontalDirection();

        return this.defaultBlockState()
                .setValue(FACING, horizontalDirection.getOpposite());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, currentPos))
        {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
