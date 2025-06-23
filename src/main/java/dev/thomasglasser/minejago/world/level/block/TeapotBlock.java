package dev.thomasglasser.minejago.world.level.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.datamaps.MinejagoDataMaps;
import dev.thomasglasser.minejago.datamaps.PotionDrainable;
import dev.thomasglasser.minejago.datamaps.PotionFillable;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBlockEntityTypes;
import dev.thomasglasser.minejago.world.level.block.entity.TeapotBlockEntity;
import dev.thomasglasser.tommylib.api.world.item.ItemUtils;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.component.ItemContainerContents;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TeapotBlock extends BaseEntityBlock {
    public static final String POTION = "container.teapot.potion";
    public static final String POTION_AND_ITEM = "container.teapot.potion_and_item";
    public static final MapCodec<TeapotBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            propertiesCodec(),
            ItemStack.ITEM_NON_AIR_CODEC.optionalFieldOf("display_cup", MinejagoItems.FILLED_TEACUP).forGetter(TeapotBlock::getDisplayCup)).apply(instance, TeapotBlock::new));
    public static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D);
    public static final ResourceLocation CONTENTS = Minejago.modLoc("teapot_contents");
    public static final IntegerProperty CUPS = IntegerProperty.create("cups", 0, 6);
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    private final Holder<Item> displayCup;

    public TeapotBlock(Properties pProperties, @Nullable Holder<Item> displayCup) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(CUPS, 0));
        this.displayCup = displayCup != null ? displayCup : MinejagoItems.FILLED_TEACUP;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CUPS);
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

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (pLevel.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        } else {
            if (pLevel.getBlockEntity(pPos) instanceof TeapotBlockEntity be) {
                ItemStack inHand = pPlayer.getItemInHand(pHand);
                if (be.getStackInSlot(0).isEmpty()) {
                    PotionDrainable drainable = inHand.getItemHolder().getData(MinejagoDataMaps.POTION_DRAINABLES);
                    if (drainable != null) {
                        if (be.tryFill(drainable.cups(), drainable.potion().orElseGet(() -> inHand.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).potion().orElseThrow()))) {
                            ItemUtils.safeShrink(1, inHand, pPlayer);
                            if (!pPlayer.getAbilities().instabuild)
                                pPlayer.addItem(drainable.remainder());
                            pLevel.playSound(null, pPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                        }
                    } else if (be.getCups() > 0) {
                        PotionFillable fillable = inHand.getItemHolder().getData(MinejagoDataMaps.POTION_FILLABLES);
                        if (fillable != null && fillable.cups() <= be.getCups()) {
                            ItemUtils.safeShrink(1, inHand, pPlayer);
                            ItemStack filled = fillable.filled().copy();
                            filled.set(DataComponents.POTION_CONTENTS, new PotionContents(be.getPotion()));
                            pPlayer.addItem(filled);
                            be.take(fillable.cups());
                            MinejagoCriteriaTriggers.BREWED_TEA.get().trigger((ServerPlayer) pPlayer, be.getPotion().getKey());
                            pLevel.playSound(null, pPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                            be.giveExperienceForCup((ServerLevel) pLevel, pPos.getCenter());
                        } else if (be.hasRecipe(inHand, pLevel)) {
                            be.insertItem(0, inHand, false);
                            ItemUtils.safeShrink(1, inHand, pPlayer);
                        }
                    }
                    return ItemInteractionResult.SUCCESS;
                }
            }
            return ItemInteractionResult.FAIL;
        }
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pLevel.getBlockEntity(pPos) instanceof TeapotBlockEntity be && (be.isBoiling() || be.isDone())) {
            double d0 = (double) pPos.getX() - 0.03D + (double) pRandom.nextFloat() * 0.2D;
            double d1 = (double) pPos.getY() + 0.53D + (double) pRandom.nextFloat() * 0.3D;
            double d2 = (double) pPos.getZ() + 0.44D + (double) pRandom.nextFloat() * 0.2D;
            pLevel.addParticle(MinejagoParticleTypes.VAPORS.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    public static int getBiomeTemperature(Level level, BlockPos pos) {
        int temp;
        float realTemp = level.getBiome(pos).value().getBaseTemperature();
        if (level.dimension() == Level.NETHER) {
            temp = 100;
        } else if (level.dimension() == Level.END) {
            temp = 0;
        } else {
            RandomSource random = RandomSource.create();
            if (realTemp <= 0.05) {
                temp = random.nextIntBetweenInclusive(-56, -28);
            } else if (realTemp <= 0.3) {
                temp = random.nextIntBetweenInclusive(-5, 5);
            } else if (realTemp < 0.9) {
                temp = random.nextIntBetweenInclusive(10, 21);
            } else if (realTemp < 2.0) {
                temp = random.nextIntBetweenInclusive(30, 40);
            } else {
                temp = random.nextIntBetweenInclusive(50, 100);
            }
        }

        return temp;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
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
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (level.getBlockState(pos.below()).is(BlockTags.FIRE) || level.getBlockState(pos.below()).is(BlockTags.CAMPFIRES)) {
            return level.getBlockState(pos.above()).isFaceSturdy(level, pos.above(), Direction.DOWN, SupportType.CENTER);
        }
        return true;
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof TeapotBlockEntity teapotBlockEntity) {
            if (!level.isClientSide && player.isCreative() && !teapotBlockEntity.isEmpty()) {
                ItemStack itemstack = asItem().getDefaultInstance();
                itemstack.applyComponents(blockentity.collectComponents());
                ItemEntity itementity = new ItemEntity(
                        level, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, itemstack);
                itementity.setDefaultPickUpDelay();
                level.addFreshEntity(itementity);
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        BlockEntity blockentity = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockentity instanceof TeapotBlockEntity teapotBlockEntity) {
            params = params.withDynamicDrop(CONTENTS, p_56219_ -> {
                for (int i = 0; i < teapotBlockEntity.getContainerSize(); i++) {
                    p_56219_.accept(teapotBlockEntity.getStackInSlot(i));
                }
            });
        }

        return super.getDrops(state, params);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        ItemContainerContents contents = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        if (contents.getSlots() > 0) {
            tooltipComponents.add(Component.translatable(contents.getStackInSlot(0).isEmpty() ? POTION : POTION_AND_ITEM, (contents.getStackInSlot(1).get(DataComponents.POTION_CONTENTS).potion().orElseThrow() == Potions.WATER ? Blocks.WATER.getName() : contents.getStackInSlot(1).getHoverName()), contents.getStackInSlot(0).getHoverName()).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        ItemStack itemstack = super.getCloneItemStack(level, pos, state);
        level.getBlockEntity(pos, MinejagoBlockEntityTypes.TEAPOT.get()).ifPresent(p_323411_ -> p_323411_.saveToItem(itemstack, level.registryAccess()));
        return itemstack;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (level.getBlockEntity(pos) instanceof TeapotBlockEntity teapotBlockEntity)
            teapotBlockEntity.setTemperature(getBiomeTemperature(level, pos));
    }

    public Holder<Item> getDisplayCup() {
        return displayCup;
    }
}
