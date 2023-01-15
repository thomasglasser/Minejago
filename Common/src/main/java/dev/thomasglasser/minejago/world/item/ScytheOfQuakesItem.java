package dev.thomasglasser.minejago.world.item;

import com.google.common.collect.ImmutableMultimap;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.definitions.ItemAnimations;
import dev.thomasglasser.minejago.client.renderer.MinejagoBlockEntityWithoutLevelRenderer;
import dev.thomasglasser.minejago.data.tags.MinejagoBlockTags;
import dev.thomasglasser.minejago.network.ClientboundStartScytheAnimationPacket;
import dev.thomasglasser.minejago.network.ClientboundStopAnimationPacket;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.util.MinejagoLevelUtils;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.joml.Math;

public class ScytheOfQuakesItem extends GoldenWeaponItem implements IModeledItem
{
    private BlockEntityWithoutLevelRenderer bewlr;

    public ScytheOfQuakesItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean canPowerHandle(Power power) {
        return power == MinejagoPowers.EARTH.get();
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 200;
    }

    @Override
    public InteractionResult doUseOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        int r = (int) Math.min(player.getSpeed() * 100.0f, 100);
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        if (level.getBlockState(pos.below()).isAir() || level.getBlockState(pos.below()).getFluidState() != Fluids.EMPTY.defaultFluidState())
        {
            if (!level.isClientSide())
            {
                for (int h = 0; h < 3; h++)
                {
                    MinejagoLevelUtils.safeFallSelf(level, pos.above(h));
                    for (int i = 1; i <= r; i++) {
                        MinejagoLevelUtils.safeFallSelf(level, pos.east(i).above(h));
                        MinejagoLevelUtils.safeFallSelf(level, pos.west(i).above(h));
                        MinejagoLevelUtils.safeFallSelf(level, pos.north(i).above(h));
                        MinejagoLevelUtils.safeFallSelf(level, pos.south(i).above(h));
                        for (int j = 1; j <= r; j++) {
                            MinejagoLevelUtils.safeFallSelf(level, pos.east(i).north(j).above(h));
                            MinejagoLevelUtils.safeFallSelf(level, pos.east(i).south(j).above(h));
                            MinejagoLevelUtils.safeFallSelf(level, pos.west(i).north(j).above(h));
                            MinejagoLevelUtils.safeFallSelf(level, pos.west(i).south(j).above(h));
                        }
                    }
                    for (int i = 0; i < r; i++) {
                        MinejagoLevelUtils.safeFallSelf(level, pos.east(r+1).south(i).above(h));
                        MinejagoLevelUtils.safeFallSelf(level, pos.east(r+1).north(i).above(h));
                        MinejagoLevelUtils.safeFallSelf(level, pos.west(r+1).south(i).above(h));
                        MinejagoLevelUtils.safeFallSelf(level, pos.west(r+1).north(i).above(h));
                        MinejagoLevelUtils.safeFallSelf(level, pos.north(r+1).east(i).above(h));
                        MinejagoLevelUtils.safeFallSelf(level, pos.north(r+1).west(i).above(h));
                        MinejagoLevelUtils.safeFallSelf(level, pos.south(r+1).east(i).above(h));
                        MinejagoLevelUtils.safeFallSelf(level, pos.south(r+1).west(i).above(h));
                    }
                }
            }
            if (!pContext.getPlayer().getAbilities().instabuild) pContext.getPlayer().getCooldowns().addCooldown(pContext.getItemInHand().getItem(), 600);
        }
        else if (player.isShiftKeyDown())
        {
            if (!level.isClientSide) Services.NETWORK.sendToAllClients(ClientboundStartScytheAnimationPacket.class, ClientboundStartScytheAnimationPacket.toBytes(player.getUUID(), ItemAnimations.Animations.SLAM_START, ItemAnimations.Animations.SLAM_RUMBLE), (ServerPlayer) player);
            BlockPos[] places = new BlockPos[] {pos.north(6), pos.north(4).east(4), pos.east(6), pos.east(4).south(4), pos.south(6), pos.south(4).west(4), pos.west(6), pos.west(4).north(4)};
            for (BlockPos place: places)
            {
                if (!level.isClientSide)
                    level.explode(null, place.getX(), place.getY() + 1, place.getZ(), 4, false, Level.ExplosionInteraction.BLOCK);
            }
            if (!pContext.getPlayer().getAbilities().instabuild) pContext.getPlayer().getCooldowns().addCooldown(pContext.getItemInHand().getItem(), 1200);
        }
        else
        {
            player.startUsingItem(pContext.getHand());
            if (!level.isClientSide) Services.NETWORK.sendToAllClients(ClientboundStartScytheAnimationPacket.class, ClientboundStartScytheAnimationPacket.toBytes(player.getUUID(), ItemAnimations.Animations.BEAM_START, ItemAnimations.Animations.BEAM_ACTIVE), (ServerPlayer) player);
        }
        return InteractionResult.SUCCESS;
    }

    ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

    @Override
    public void doReleaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLivingEntity instanceof Player player1)
        {
            if (!player1.getAbilities().instabuild) player1.getCooldowns().addCooldown(pStack.getItem(), 20 * (pStack.getUseDuration() - pTimeCharged));
            if (!pLevel.isClientSide) Services.NETWORK.sendToAllClients(ClientboundStopAnimationPacket.class, ClientboundStopAnimationPacket.toBytes(pLivingEntity.getUUID()), (ServerPlayer) player1);
            player1.getAttributes().removeAttributeModifiers(builder.build());
        }
    }

    @Override
    public void doOnUsingTick(ItemStack stack, LivingEntity player, int count) {
        Level level = player.getLevel();
        if (stack.getUseDuration() - count + 1 == stack.getUseDuration())
        {
            player.stopUsingItem();
            stack.releaseUsing(player.getLevel(), player, count);
            return;
        }
        if (count % 10 == 0 && !level.isClientSide())
        {
            Vec3 loc = player.pick(Double.MAX_EXPONENT, 0.0F, false).getLocation();
            BlockPos pos = new BlockPos(loc);
            Direction direction = player.getDirection();
            if (direction == Direction.EAST)
            {
                MinejagoLevelUtils.safeDestroy(level, pos, true);
                MinejagoLevelUtils.safeDestroy(level, pos.north(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.north().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.south(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.south().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.east(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.north().east(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.south().east(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.east().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.north().east().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.south().east().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.east().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.north().east().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.south().east().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.north().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.south().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.west().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.north().west().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.south().west().above(2), true);
                if (player.getXRot() > 0)
                {
                    MinejagoLevelUtils.safeDestroy(level, pos.east().below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.north().east().below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.south().east().below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.north().below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.south().below(), true);
                }
            }
            else if (direction == Direction.NORTH)
            {
                MinejagoLevelUtils.safeDestroy(level, pos, true);
                MinejagoLevelUtils.safeDestroy(level, pos.west(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.west().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.east(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.east().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.north(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.west().north(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.east().north(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.north().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.west().north().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.east().north().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.north().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.west().north().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.east().north().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.west().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.east().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.south().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.west().south().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.east().south().above(2), true);
                if (player.getXRot() > 0)
                {
                    MinejagoLevelUtils.safeDestroy(level, pos.north().below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.west().north().below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.east().north().below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.west().below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.east().below(), true);
                }
            }
            else if (direction == Direction.SOUTH)
            {
                MinejagoLevelUtils.safeDestroy(level, pos, true);
                MinejagoLevelUtils.safeDestroy(level, pos.east(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.east().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.west(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.west().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.south(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.east().south(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.west().south(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.south().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.east().south().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.west().south().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.south().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.east().south().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.west().south().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.east().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.west().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.north().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.east().north().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.west().north().above(2), true);
                if (player.getXRot() > 0)
                {
                    MinejagoLevelUtils.safeDestroy(level, pos.south().below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.east().south().below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.west().south().below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.east().below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.west().below(), true);
                }
            }
            else if (direction == Direction.WEST)
            {
                MinejagoLevelUtils.safeDestroy(level, pos, true);
                MinejagoLevelUtils.safeDestroy(level, pos.south(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.south().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.north(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.north().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.west(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.south().west(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.north().west(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.west().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.south().west().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.north().west().above(), true);
                MinejagoLevelUtils.safeDestroy(level, pos.west().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.south().west().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.north().west().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.south().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.north().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.east().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.south().east().above(2), true);
                MinejagoLevelUtils.safeDestroy(level, pos.north().east().above(2), true);
                if (player.getXRot() > 0)
                {
                    MinejagoLevelUtils.safeDestroy(level, pos.west().below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.south().west().below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.north().west().below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.south().below(), true);
                    MinejagoLevelUtils.safeDestroy(level, pos.north().below(), true);
                }
            }
            else
            {
                Minejago.LOGGER.error("Unknown/unsupported direction for scythe staircase");
            }
            builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier("Movement modifier", -1, AttributeModifier.Operation.MULTIPLY_TOTAL));
            player.getAttributes().addTransientAttributeModifiers(builder.build());
        }
    }

    @Override
    protected void goCrazy(Player player) {
        if (!player.getLevel().isClientSide && !player.getAbilities().instabuild)
        {
            player.getLevel().explode(null, player.getX(), player.getY() + 1, player.getZ(), 8.0F, Level.ExplosionInteraction.TNT);
            Services.NETWORK.sendToAllClients(ClientboundStartScytheAnimationPacket.class, ClientboundStartScytheAnimationPacket.toBytes(player.getUUID(), ItemAnimations.Animations.SLAM_START, ItemAnimations.Animations.EMPTY), (ServerPlayer) player);
        }
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pMiningEntity) {
        return !pState.is(MinejagoBlockTags.UNBREAKABLE);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState pBlock) {
        return !pBlock.is(MinejagoBlockTags.UNBREAKABLE);
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        return 25.0f;
    }

    @Override
    public BlockEntityWithoutLevelRenderer getBEWLR() {
        if (bewlr == null) bewlr = new MinejagoBlockEntityWithoutLevelRenderer();
        return bewlr;
    }
}
