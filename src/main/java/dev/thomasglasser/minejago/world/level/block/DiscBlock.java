package dev.thomasglasser.minejago.world.level.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DiscBlock extends HorizontalDirectionalBlock {
    public static final EnumProperty<Row> ROW = EnumProperty.create("row", Row.class);
    public static final EnumProperty<Column> COLUMN = EnumProperty.create("column", Column.class);

    private static final VoxelShape SHAPE_NORTH = box(0.0, 0.0, 14.0, 16.0, 16.0, 16.0);
    private static final VoxelShape SHAPE_SOUTH = box(0.0, 0.0, 0.0, 16.0, 16.0, 2.0);
    private static final VoxelShape SHAPE_EAST = box(0.0, 0.0, 0.0, 2.0, 16.0, 16.0);
    private static final VoxelShape SHAPE_WEST = box(14.0, 0.0, 0.0, 16.0, 16.0, 16.0);

    public DiscBlock(Properties properties) {
        super(properties.noOcclusion());
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(ROW, Row.TOP)
                        .setValue(COLUMN, Column.LEFT));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return simpleCodec(DiscBlock::new);
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
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        super.destroy(level, pos, state);
        Direction facing = state.getValue(FACING);
        if (state.getValue(ROW) == Row.TOP) {
            if (state.getValue(COLUMN) == Column.LEFT) {
                if (level.getBlockState(pos.relative(facing.getCounterClockWise())).is(this))
                    level.destroyBlock(pos.relative(facing.getCounterClockWise()), false);
                if (level.getBlockState(pos.below()).is(this))
                    level.destroyBlock(pos.below(), false);
                if (level.getBlockState(pos.below().relative(facing.getCounterClockWise())).is(this))
                    level.destroyBlock(pos.below().relative(facing.getCounterClockWise()), false);
            } else {
                if (level.getBlockState(pos.relative(facing.getClockWise())).is(this))
                    level.destroyBlock(pos.relative(facing.getClockWise()), false);
                if (level.getBlockState(pos.below()).is(this))
                    level.destroyBlock(pos.below(), false);
                if (level.getBlockState(pos.below().relative(facing.getClockWise())).is(this))
                    level.destroyBlock(pos.below().relative(facing.getClockWise()), false);
            }
        } else {
            if (state.getValue(COLUMN) == Column.LEFT) {
                if (level.getBlockState(pos.relative(facing.getCounterClockWise())).is(this))
                    level.destroyBlock(pos.relative(facing.getCounterClockWise()), false);
                if (level.getBlockState(pos.above()).is(this))
                    level.destroyBlock(pos.above(), false);
                if (level.getBlockState(pos.above().relative(facing.getCounterClockWise())).is(this))
                    level.destroyBlock(pos.above().relative(facing.getCounterClockWise()), false);
            } else {
                if (level.getBlockState(pos.relative(facing.getClockWise())).is(this))
                    level.destroyBlock(pos.relative(facing.getClockWise()), false);
                if (level.getBlockState(pos.above()).is(this))
                    level.destroyBlock(pos.above(), false);
                if (level.getBlockState(pos.above().relative(facing.getClockWise())).is(this))
                    level.destroyBlock(pos.above().relative(facing.getClockWise()), false);
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        BlockPos blockPos = pos.relative(direction.getOpposite());
        BlockState blockState = level.getBlockState(blockPos);
        return blockState.isFaceSturdy(level, blockPos, direction);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();
        Direction horizontalDirection = context.getHorizontalDirection();
        if (!level.getBlockState(blockPos.relative(horizontalDirection).relative(horizontalDirection.getOpposite()).below()).canBeReplaced(context)) return null;
        if (!level.getBlockState(blockPos.relative(horizontalDirection).relative(horizontalDirection.getOpposite()).relative(horizontalDirection.getClockWise())).canBeReplaced(context)) return null;
        if (!level.getBlockState(blockPos.relative(horizontalDirection).below().relative(horizontalDirection.getOpposite()).relative(horizontalDirection.getClockWise())).canBeReplaced(context)) return null;

        return this.defaultBlockState()
                .setValue(FACING, horizontalDirection.getOpposite())
                .setValue(ROW, Row.TOP)
                .setValue(COLUMN, Column.LEFT);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        level.setBlock(pos.below(), state.setValue(ROW, Row.BOTTOM).setValue(COLUMN, Column.LEFT), UPDATE_ALL);
        level.setBlock(pos.relative(placer.getDirection().getClockWise()), state.setValue(ROW, Row.TOP).setValue(COLUMN, Column.RIGHT), UPDATE_ALL);
        level.setBlock(pos.below().relative(placer.getDirection().getClockWise()), state.setValue(ROW, Row.BOTTOM).setValue(COLUMN, Column.RIGHT), UPDATE_ALL);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, currentPos)) {
            state.getBlock().destroy(level, currentPos, state);
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ROW, COLUMN);
    }

    public enum Row implements StringRepresentable {
        TOP("top"),
        BOTTOM("bottom");

        private final String name;

        Row(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }

    public enum Column implements StringRepresentable {
        LEFT("left"),
        RIGHT("right");

        private final String name;

        Column(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
