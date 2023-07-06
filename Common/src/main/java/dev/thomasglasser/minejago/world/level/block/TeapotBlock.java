package dev.thomasglasser.minejago.world.level.block;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.util.MinejagoItemUtils;
import dev.thomasglasser.minejago.world.item.ITeapotLiquidHolder;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotionBrewing;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBlockEntityTypes;
import dev.thomasglasser.minejago.world.level.block.entity.TeapotBlockEntity;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TeapotBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D);
    public static final ResourceLocation CONTENTS = new ResourceLocation(Minejago.MOD_ID, "teapot_contents");
    public static final BooleanProperty FILLED = BooleanProperty.create("filled");

    public TeapotBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FILLED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FILLED);
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
                if ((be.isDone() || be.isBoiling()) && inHand.is(MinejagoItems.TEACUP.get()) && be.getCups() > 0)
                {
                    MinejagoItemUtils.safeShrink(1, inHand, pPlayer);
                    pPlayer.addItem(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), be.getPotion()));
                    be.take(1);
                    MinejagoCriteriaTriggers.BREWED_TEA.trigger((ServerPlayer) pPlayer, be.getPotion());
                    pLevel.playSound(null, pPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                    be.giveExperienceForCup((ServerLevel) pLevel, pPos.getCenter());
                }
                else
                {
                    if ((be.isBoiling() || be.isDone()) && (((PotionBrewing.hasPotionMix(PotionUtils.setPotion(new ItemStack(Items.POTION), be.getPotion()), inHand) && be.getPotion() != Potions.AWKWARD) || (PotionBrewing.hasPotionMix(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD), inHand)) && be.getPotion() == MinejagoPotions.REGULAR_TEA.get()) || (be.hasRecipe(inHand, pLevel)) || (MinejagoPotionBrewing.hasTeaMix(PotionUtils.setPotion(new ItemStack(Items.POTION), be.getPotion()), inHand))))
                    {
                        if (!be.hasRecipe(inHand, pLevel) && (PotionBrewing.hasPotionMix(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD), inHand)) && be.getPotion() == MinejagoPotions.REGULAR_TEA.get())
                        {
                            be.setPotion(Potions.AWKWARD);
                        }
                        be.setItem(0, inHand);
                        MinejagoItemUtils.safeShrink(1, inHand, pPlayer);
                    }
                    else if (!be.getInSlot(0).isEmpty()) {
                        pPlayer.addItem(be.getInSlot(0));
                        be.extract(0, 1);
                        if (be.getPotion() == Potions.AWKWARD)
                            be.setPotion(MinejagoPotions.REGULAR_TEA.get());
                    }
                    else if (inHand.getItem() instanceof ITeapotLiquidHolder holder)
                    {
                        if (be.tryFill(holder.getCups(), holder.getPotion(inHand)))
                        {
                            if (!pPlayer.getAbilities().instabuild)
                                MinejagoItemUtils.safeShrink(1, inHand, pPlayer);
                            pPlayer.addItem(holder.getDrained(pPlayer.getItemInHand(pHand)));
                            pLevel.playSound(null, pPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                        }
                    }
                }
            }
            return InteractionResult.CONSUME;
        }
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        if (!pLevel.isClientSide && pLevel.getBlockEntity(pPos) instanceof TeapotBlockEntity teapot) teapot.setTemperature(TeapotBlock.getBiomeTemperature(pLevel, pPos));

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
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        ItemStack stack = new ItemStack(asItem());
        TeapotBlockEntity be = (TeapotBlockEntity) level.getBlockEntity(pos);

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

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return pContext.getLevel().getBlockState(pContext.getClickedPos().above()).isFaceSturdy(pContext.getLevel(), pContext.getClickedPos().above(), Direction.DOWN, SupportType.CENTER) || !(pContext.getLevel().getBlockState(pContext.getClickedPos().below()).is(BlockTags.FIRE) || pContext.getLevel().getBlockState(pContext.getClickedPos().below()).is(BlockTags.CAMPFIRES)) ? this.defaultBlockState() : null;
    }
}
