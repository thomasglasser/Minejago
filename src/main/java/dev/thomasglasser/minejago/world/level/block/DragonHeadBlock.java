package dev.thomasglasser.minejago.world.level.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.world.level.block.entity.DragonHeadBlockEntity;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class DragonHeadBlock extends HorizontalDirectionalBlock implements EntityBlock {
    private final Supplier<EntityType<?>> entityType;

    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

    public static MapCodec<DragonHeadBlock> CODEC;

    public DragonHeadBlock(Supplier<EntityType<?>> entity, Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(ACTIVATED, false));
        this.entityType = entity;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (!blockState.getValue(ACTIVATED)) {
            level.setBlock(blockPos, blockState.setValue(ACTIVATED, true), Block.UPDATE_ALL);
            return InteractionResult.SUCCESS;
        }
        return super.useWithoutItem(blockState, level, blockPos, player, blockHitResult);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction horizontalDirection = context.getHorizontalDirection();

        return this.defaultBlockState()
                .setValue(FACING, horizontalDirection.getClockWise())
                .setValue(ACTIVATED, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ACTIVATED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DragonHeadBlockEntity(pos, state);
    }

    public EntityType<?> getEntityType() {
        return entityType.get();
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return DragonHeadBlockEntity::tick;
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        if (CODEC == null)
            CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity_type").forGetter(DragonHeadBlock::getEntityType),
                    Properties.CODEC.fieldOf("properties").forGetter(BlockBehaviour::properties)).apply(instance, (entityType, properties) -> new DragonHeadBlock(() -> entityType, properties)));
        return CODEC;
    }
}
