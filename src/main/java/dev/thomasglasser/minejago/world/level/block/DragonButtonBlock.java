package dev.thomasglasser.minejago.world.level.block;

import com.mojang.serialization.MapCodec;
import dev.thomasglasser.minejago.world.level.block.entity.DragonButtonBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class DragonButtonBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final VoxelShape BOTTOM_SHAPE = Block.box(5, 0, 5, 11, 16, 11);
    public static final VoxelShape TOP_BASE_SHAPE = Block.box(4, 0, 4, 12, 2, 12);

    // North
    public static final VoxelShape CLOSED_DRAGON_SHAPE_NORTH = Block.box(7, 2, 0, 9, 15, 16);
    public static final VoxelShape OPEN_DRAGON_SHAPE_NORTH = Block.box(7, 5, 3, 9, 16, 16);
    public static final VoxelShape BUTTON_SHAPE_NORTH = Block.box(7, 2, 6, 9, 5, 9);
    public static final VoxelShape CLOSED_TOP_SHAPE_NORTH = Shapes.or(TOP_BASE_SHAPE, CLOSED_DRAGON_SHAPE_NORTH);
    public static final VoxelShape OPEN_TOP_SHAPE_NORTH = Shapes.or(TOP_BASE_SHAPE, OPEN_DRAGON_SHAPE_NORTH, BUTTON_SHAPE_NORTH);

    // South
    public static final VoxelShape CLOSED_DRAGON_SHAPE_SOUTH = Block.box(7, 2, 0, 9, 15, 16);
    public static final VoxelShape OPEN_DRAGON_SHAPE_SOUTH = Block.box(7, 5, 0, 9, 16, 13);
    public static final VoxelShape BUTTON_SHAPE_SOUTH = Block.box(7, 2, 7, 9, 5, 9);
    public static final VoxelShape CLOSED_TOP_SHAPE_SOUTH = Shapes.or(TOP_BASE_SHAPE, CLOSED_DRAGON_SHAPE_SOUTH);
    public static final VoxelShape OPEN_TOP_SHAPE_SOUTH = Shapes.or(TOP_BASE_SHAPE, OPEN_DRAGON_SHAPE_SOUTH, BUTTON_SHAPE_SOUTH);

    // East
    public static final VoxelShape CLOSED_DRAGON_SHAPE_EAST = Block.box(0, 2, 7, 16, 15, 9);
    public static final VoxelShape OPEN_DRAGON_SHAPE_EAST = Block.box(0, 5, 7, 13, 16, 9);
    public static final VoxelShape BUTTON_SHAPE_EAST = Block.box(6, 2, 7, 9, 5, 9);
    public static final VoxelShape CLOSED_TOP_SHAPE_EAST = Shapes.or(TOP_BASE_SHAPE, CLOSED_DRAGON_SHAPE_EAST);
    public static final VoxelShape OPEN_TOP_SHAPE_EAST = Shapes.or(TOP_BASE_SHAPE, OPEN_DRAGON_SHAPE_EAST, BUTTON_SHAPE_EAST);

    // West
    public static final VoxelShape CLOSED_DRAGON_SHAPE_WEST = Block.box(0, 2, 7, 16, 15, 9);
    public static final VoxelShape OPEN_DRAGON_SHAPE_WEST = Block.box(3, 5, 7, 16, 16, 9);
    public static final VoxelShape BUTTON_SHAPE_WEST = Block.box(7, 2, 7, 9, 5, 9);
    public static final VoxelShape CLOSED_TOP_SHAPE_WEST = Shapes.or(TOP_BASE_SHAPE, CLOSED_DRAGON_SHAPE_WEST);
    public static final VoxelShape OPEN_TOP_SHAPE_WEST = Shapes.or(TOP_BASE_SHAPE, OPEN_DRAGON_SHAPE_WEST, BUTTON_SHAPE_WEST);

    public static final Map<Direction, VoxelShape> CLOSED_DRAGON_SHAPES = Map.of(
            Direction.NORTH, CLOSED_DRAGON_SHAPE_NORTH,
            Direction.SOUTH, CLOSED_DRAGON_SHAPE_SOUTH,
            Direction.EAST, CLOSED_DRAGON_SHAPE_EAST,
            Direction.WEST, CLOSED_DRAGON_SHAPE_WEST);
    public static final Map<Direction, VoxelShape> OPEN_DRAGON_SHAPES = Map.of(
            Direction.NORTH, OPEN_DRAGON_SHAPE_NORTH,
            Direction.SOUTH, OPEN_DRAGON_SHAPE_SOUTH,
            Direction.EAST, OPEN_DRAGON_SHAPE_EAST,
            Direction.WEST, OPEN_DRAGON_SHAPE_WEST);
    public static final Map<Direction, VoxelShape> BUTTON_SHAPES = Map.of(
            Direction.NORTH, BUTTON_SHAPE_NORTH,
            Direction.SOUTH, BUTTON_SHAPE_SOUTH,
            Direction.EAST, BUTTON_SHAPE_EAST,
            Direction.WEST, BUTTON_SHAPE_WEST);

    public static final EnumProperty<Part> PART = EnumProperty.create("part", Part.class);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final int PRESSED_TICKS = 10;

    public static MapCodec<DragonButtonBlock> CODEC = simpleCodec(DragonButtonBlock::new);

    public DragonButtonBlock(Properties properties) {
        super(properties.noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(PART, Part.BOTTOM).setValue(POWERED, false));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, OPEN, PART, POWERED);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Part part = state.getValue(PART);
        boolean open = state.getValue(OPEN);
        if (part == Part.BOTTOM) {
            return BOTTOM_SHAPE;
        } else {
            Direction facing = state.getValue(FACING);
            return switch (facing) {
                case NORTH -> open ? OPEN_TOP_SHAPE_NORTH : CLOSED_TOP_SHAPE_NORTH;
                case SOUTH -> open ? OPEN_TOP_SHAPE_SOUTH : CLOSED_TOP_SHAPE_SOUTH;
                case EAST -> open ? OPEN_TOP_SHAPE_EAST : CLOSED_TOP_SHAPE_EAST;
                case WEST -> open ? OPEN_TOP_SHAPE_WEST : CLOSED_TOP_SHAPE_WEST;
                default -> throw new IllegalStateException("Unexpected value: " + facing);
            };
        }
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getShape(level, pos);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getShape(level, pos);
    }

    @Override
    protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getShape(level, pos);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide && state.getValue(PART) == Part.TOP) {
            VoxelShape dragon = state.getValue(OPEN) ? OPEN_DRAGON_SHAPES.get(state.getValue(FACING)) : CLOSED_DRAGON_SHAPES.get(state.getValue(FACING));
            Vec3 lookOffset = player.getViewVector(1.0F); // unit normal vector in look direction
            Vec3 hitVec = hitResult.getLocation();
            Vec3 relativeHitVec = hitVec
                    .add(lookOffset.multiply(0.001D, 0.001D, 0.001D))
                    .subtract(pos.getX(), pos.getY(), pos.getZ());
            if (state.getValue(OPEN) && !state.getValue(POWERED) && BUTTON_SHAPES.get(state.getValue(FACING)).bounds().contains(relativeHitVec)) {
                if (level.getBlockEntity(pos) instanceof DragonButtonBlockEntity entity) {
                    entity.triggerAnim("click", DragonButtonBlockEntity.CLICK);
                }
                press(state, level, pos, player);
            } else if (dragon.bounds().contains(relativeHitVec)) {
                level.setBlock(pos, state.cycle(OPEN), Block.UPDATE_ALL);
                level.setBlock(pos.below(), level.getBlockState(pos.below()).cycle(OPEN), Block.UPDATE_ALL);
                return InteractionResult.SUCCESS;
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    public void press(BlockState state, Level level, BlockPos pos, @Nullable Player player) {
        level.setBlock(pos, state.setValue(POWERED, Boolean.TRUE), Block.UPDATE_ALL);
        this.updateNeighbours(state, level, pos);
        level.scheduleTick(pos, this, PRESSED_TICKS);
        // TODO: Sound
        level.gameEvent(player, GameEvent.BLOCK_ACTIVATE, pos);
        if (state.getValue(PART) == Part.TOP && level.getBlockState(pos.below()).getValue(PART) == Part.BOTTOM)
            press(level.getBlockState(pos.below()), level, pos.below(), player);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!isMoving && !state.is(newState.getBlock())) {
            if (state.getValue(POWERED)) {
                this.updateNeighbours(state, level, pos);
            }

            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    protected int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return blockState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    protected int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return blockState.getValue(POWERED) && blockState.getValue(FACING) == side ? 15 : 0;
    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(POWERED)) {
            this.checkPressed(state, level, pos);
        }
    }

    protected void checkPressed(BlockState state, Level level, BlockPos pos) {
        boolean powered = state.getValue(POWERED);
        if (powered) {
            level.setBlock(pos, state.setValue(POWERED, false), Block.UPDATE_ALL);
            this.updateNeighbours(state, level, pos);
            // TODO: Sound
            level.gameEvent(null, GameEvent.BLOCK_DEACTIVATE, pos);
        }
    }

    private void updateNeighbours(BlockState state, Level level, BlockPos pos) {
        level.updateNeighborsAt(pos, this);
        level.updateNeighborsAt(pos.relative(state.getValue(FACING).getOpposite()), this);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DragonButtonBlockEntity(pos, state);
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        super.destroy(level, pos, state);
        if (state.getValue(PART) == Part.TOP && level.getBlockState(pos.below()).is(this))
            level.destroyBlock(pos.below(), false);
        else if (level.getBlockState(pos.above()).is(this))
            level.destroyBlock(pos.above(), false);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection();
        BlockPos blockpos = context.getClickedPos();
        BlockPos upPos = blockpos.relative(Direction.UP);
        Level level = context.getLevel();
        return level.getBlockState(upPos).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(upPos)
                ? this.defaultBlockState().setValue(FACING, direction.getOpposite()).setValue(OPEN, false).setValue(PART, Part.BOTTOM)
                : null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide) {
            BlockPos blockpos = pos.above();
            level.setBlock(blockpos, state.setValue(PART, Part.TOP), Block.UPDATE_ALL);
            level.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(level, pos, Block.UPDATE_ALL);
        }
    }

    public enum Part implements StringRepresentable {
        BOTTOM,
        TOP;

        @Override
        public String getSerializedName() {
            return this.toString().toLowerCase();
        }
    }
}
