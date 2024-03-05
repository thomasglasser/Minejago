package dev.thomasglasser.minejago.world.level.block;

import com.mojang.serialization.MapCodec;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.world.item.PotionCupHolder;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBlockEntityTypes;
import dev.thomasglasser.minejago.world.level.block.entity.TeapotBlockEntity;
import dev.thomasglasser.tommylib.api.world.item.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TeapotBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D);
    public static final ResourceLocation CONTENTS = new ResourceLocation(Minejago.MOD_ID, "teapot_contents");
    public static final BooleanProperty FILLED = BooleanProperty.create("filled");
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public TeapotBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FILLED, Boolean.FALSE));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return simpleCodec(TeapotBlock::new);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FILLED);
        builder.add(FACING);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TeapotBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, MinejagoBlockEntityTypes.TEAPOT.get(), TeapotBlockEntity::serverTick);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            if (pLevel.getBlockEntity(pPos) instanceof TeapotBlockEntity be)
            {
                ItemStack inHand = pPlayer.getItemInHand(pHand);
                if (be.getInSlot(0) == ItemStack.EMPTY)
                {
                    if (inHand.getItem() instanceof PotionCupHolder holder && holder.canBeDrained(inHand))
                    {
                        if (be.tryFill(holder.getCups(), holder.getPotion(inHand)))
                        {
                            ItemUtils.safeShrink(1, inHand, pPlayer);
                            pPlayer.addItem(holder.getDrained(pPlayer.getItemInHand(pHand)));
                            pLevel.playSound(null, pPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                        }
                    }
                    else if (be.getCups() > 0)
                    {
                        if (inHand.getItem() instanceof PotionCupHolder potionCupHolder && potionCupHolder.canBeFilled(inHand, be.getPotion(), be.getCups())) {
                            ItemUtils.safeShrink(1, inHand, pPlayer);
                            pPlayer.addItem(potionCupHolder.getFilled(be.getPotion()));
                            be.take(potionCupHolder.getCups());
                            MinejagoCriteriaTriggers.BREWED_TEA.get().trigger((ServerPlayer) pPlayer, be.getPotion().builtInRegistryHolder());
                            pLevel.playSound(null, pPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                            be.giveExperienceForCup((ServerLevel) pLevel, pPos.getCenter());
                        } else if (be.hasRecipe(inHand, pLevel)) {
                            be.insert(0, inHand);
                            ItemUtils.safeShrink(1, inHand, pPlayer);
                        }
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.FAIL;
        }
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        if (pLevel.getBlockEntity(pPos) instanceof TeapotBlockEntity teapot) teapot.setTemperature(TeapotBlock.getBiomeTemperature(pLevel, pPos) / 2);

        if (pStack.hasTag()) {
            CompoundTag tag = pStack.getTag();

            if (tag.contains("BlockEntityTag")) {
                CompoundTag dataTag = tag.getCompound("BlockEntityTag");
                pLevel.getBlockEntity(pPos).load(dataTag);
            }
            }
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pLevel.getBlockEntity(pPos) instanceof TeapotBlockEntity be && (be.isBoiling() || be.isDone()))
        {
            double d0 = (double)pPos.getX() - 0.03D + (double)pRandom.nextFloat() * 0.2D;
            double d1 = (double)pPos.getY() + 0.53D + (double)pRandom.nextFloat() * 0.3D;
            double d2 = (double)pPos.getZ() + 0.44D + (double)pRandom.nextFloat() * 0.2D;
            pLevel.addParticle(MinejagoParticleTypes.VAPORS.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    @SuppressWarnings("deprecation")
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @SuppressWarnings("deprecation")
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pBuilder) {
        BlockEntity blockentity = pBuilder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockentity instanceof TeapotBlockEntity be) {
            pBuilder = pBuilder.withDynamicDrop(CONTENTS, (consumer) -> {
                for(int i = 0; i < be.getContainerSize(); ++i) {
                    consumer.accept(be.getInSlot(i));
                }
            });
        }

        return super.getDrops(pState, pBuilder);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState)
    {
        ItemStack stack = new ItemStack(asItem());
        TeapotBlockEntity be = (TeapotBlockEntity) levelReader.getBlockEntity(blockPos);

        be.saveToItem(stack);

        return stack;
    }

    public static int getBiomeTemperature(Level level, BlockPos pos)
    {
        int temp;
        float realTemp = level.getBiome(pos).value().getBaseTemperature();
        if (level.dimension() == Level.NETHER)
        {
            temp = 100;
        } else if (level.dimension() == Level.END) {
            temp = 0;
        }
        else
        {
            RandomSource random = RandomSource.create();
            if (realTemp <= 0.05)
            {
                temp = random.nextIntBetweenInclusive(-56, -28);
            } else if (realTemp <= 0.3) {
                temp = random.nextIntBetweenInclusive(-5, 5);
            } else if (realTemp < 0.9) {
                temp = random.nextIntBetweenInclusive(10, 21);
            } else if (realTemp < 2.0) {
                temp = random.nextIntBetweenInclusive(30, 40);
            }
            else
            {
                temp = random.nextIntBetweenInclusive(50, 100);
            }
        }

        return temp;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (!state.canSurvive(level, currentPos))
        {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return pContext.getLevel().getBlockState(pContext.getClickedPos().above()).isFaceSturdy(pContext.getLevel(), pContext.getClickedPos().above(), Direction.DOWN, SupportType.CENTER) || !(pContext.getLevel().getBlockState(pContext.getClickedPos().below()).is(BlockTags.FIRE) || pContext.getLevel().getBlockState(pContext.getClickedPos().below()).is(BlockTags.CAMPFIRES)) ? this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()) : null;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos)
    {
        if (level.getBlockState(pos.below()).is(BlockTags.FIRE) || level.getBlockState(pos.below()).is(BlockTags.CAMPFIRES))
        {
            return level.getBlockState(pos.above()).isFaceSturdy(level, pos.above(), Direction.DOWN, SupportType.CENTER);
        }
        return true;
    }
}
