package dev.thomasglasser.minejago.world.item;

import com.google.common.collect.ImmutableMultimap;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.animation.definitions.ItemAnimations;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.network.ClientboundStartScytheAnimationPayload;
import dev.thomasglasser.minejago.network.ClientboundStopAnimationPayload;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.tags.MinejagoPowerTags;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import dev.thomasglasser.tommylib.api.tags.ConventionalBlockTags;
import dev.thomasglasser.tommylib.api.world.level.LevelUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.joml.Math;

import javax.annotation.Nullable;
import java.util.Optional;

public class ScytheOfQuakesItem extends GoldenWeaponItem
{
    public static final ResourceLocation STAIRCASE_SPEED_MODIFIER = Minejago.modLoc("staircase_speed");

    public ScytheOfQuakesItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean canPowerHandle(ResourceKey<Power> power, Registry<Power> registry) {
        return registry.get(power).is(MinejagoPowerTags.EARTH, registry);
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity livingEntity) {
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
                    LevelUtils.safeFall(level, pos.above(h));
                    for (int i = 1; i <= r; i++) {
                        LevelUtils.safeFall(level, pos.east(i).above(h));
                        LevelUtils.safeFall(level, pos.west(i).above(h));
                        LevelUtils.safeFall(level, pos.north(i).above(h));
                        LevelUtils.safeFall(level, pos.south(i).above(h));
                        for (int j = 1; j <= r; j++) {
                            LevelUtils.safeFall(level, pos.east(i).north(j).above(h));
                            LevelUtils.safeFall(level, pos.east(i).south(j).above(h));
                            LevelUtils.safeFall(level, pos.west(i).north(j).above(h));
                            LevelUtils.safeFall(level, pos.west(i).south(j).above(h));
                        }
                    }
                    for (int i = 0; i < r; i++) {
                        LevelUtils.safeFall(level, pos.east(r+1).south(i).above(h));
                        LevelUtils.safeFall(level, pos.east(r+1).north(i).above(h));
                        LevelUtils.safeFall(level, pos.west(r+1).south(i).above(h));
                        LevelUtils.safeFall(level, pos.west(r+1).north(i).above(h));
                        LevelUtils.safeFall(level, pos.north(r+1).east(i).above(h));
                        LevelUtils.safeFall(level, pos.north(r+1).west(i).above(h));
                        LevelUtils.safeFall(level, pos.south(r+1).east(i).above(h));
                        LevelUtils.safeFall(level, pos.south(r+1).west(i).above(h));
                    }
                }
            }
            if (!pContext.getPlayer().getAbilities().instabuild) pContext.getPlayer().getCooldowns().addCooldown(pContext.getItemInHand().getItem(), 600);
            pContext.getLevel().playSound(null, pContext.getPlayer().blockPosition(), MinejagoSoundEvents.SCYTHE_OF_QUAKES_CASCADE.get(), SoundSource.PLAYERS);
        }
        else if (player.isShiftKeyDown())
        {
            if (!level.isClientSide) TommyLibServices.NETWORK.sendToAllClients(new ClientboundStartScytheAnimationPayload(player.getUUID(), ItemAnimations.ScytheOfQuakes.SLAM_START, Optional.of(ItemAnimations.ScytheOfQuakes.SLAM_RUMBLE)), player.getServer());
            BlockPos[] places = new BlockPos[] {pos.north(6), pos.north(4).east(4), pos.east(6), pos.east(4).south(4), pos.south(6), pos.south(4).west(4), pos.west(6), pos.west(4).north(4)};
            for (BlockPos place: places)
            {
                if (!level.isClientSide)
                    level.explode(null, place.getX(), place.getY() + 1, place.getZ(), 4, false, Level.ExplosionInteraction.BLOCK);
            }
            if (!pContext.getPlayer().getAbilities().instabuild) pContext.getPlayer().getCooldowns().addCooldown(pContext.getItemInHand().getItem(), 1200);
            pContext.getLevel().playSound(null, pContext.getPlayer().blockPosition(), MinejagoSoundEvents.SCYTHE_OF_QUAKES_EXPLOSION.get(), SoundSource.PLAYERS);
        }
        else
        {
            player.startUsingItem(pContext.getHand());
            if (!level.isClientSide) TommyLibServices.NETWORK.sendToAllClients(new ClientboundStartScytheAnimationPayload(player.getUUID(), ItemAnimations.ScytheOfQuakes.BEAM_START, Optional.of(ItemAnimations.ScytheOfQuakes.BEAM_ACTIVE)), player.getServer());
        }
        return InteractionResult.SUCCESS;
    }

    ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

    @Override
    public void doReleaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLivingEntity instanceof Player player1)
        {
            if (!player1.getAbilities().instabuild) player1.getCooldowns().addCooldown(pStack.getItem(), 20 * (pTimeCharged > 10? (pStack.getUseDuration(pLivingEntity) - pTimeCharged) : 1));
            if (!pLevel.isClientSide) TommyLibServices.NETWORK.sendToAllClients(new ClientboundStopAnimationPayload(pLivingEntity.getUUID()), pLevel.getServer());
            ItemAttributeModifiers original = pStack.get(DataComponents.ATTRIBUTE_MODIFIERS);
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
            if (original != null)
            {
                original.modifiers().forEach(entry -> {
                    if (!entry.modifier().id().equals(STAIRCASE_SPEED_MODIFIER))
                        builder.add(entry.attribute(), entry.modifier(), entry.slot());
                });
            }
            pStack.set(DataComponents.ATTRIBUTE_MODIFIERS, builder.build());
        }
    }

    @Override
    public void doOnUsingTick(ItemStack stack, LivingEntity player, int count) {
        Level level = player.level();
        if (stack.getUseDuration(player) - count + 1 == stack.getUseDuration(player))
        {
            player.stopUsingItem();
            stack.releaseUsing(level, player, count);
            return;
        }
        if (count % 10 == 0)
        {
            LevelUtils.beamParticles(MinejagoParticleTypes.ROCKS.get(), level, player);
            Vec3 loc = player.pick(Double.MAX_EXPONENT, 0.0F, false).getLocation();
            BlockPos pos = new BlockPos((int) loc.x, (int) loc.y, (int) loc.z);
            Direction direction = player.getDirection();
            if (direction == Direction.EAST)
            {
                LevelUtils.safeDestroy(level, pos, true);
                LevelUtils.safeDestroy(level, pos.north(), true);
                LevelUtils.safeDestroy(level, pos.north().above(), true);
                LevelUtils.safeDestroy(level, pos.south(), true);
                LevelUtils.safeDestroy(level, pos.south().above(), true);
                LevelUtils.safeDestroy(level, pos.east(), true);
                LevelUtils.safeDestroy(level, pos.above(), true);
                LevelUtils.safeDestroy(level, pos.north().east(), true);
                LevelUtils.safeDestroy(level, pos.south().east(), true);
                LevelUtils.safeDestroy(level, pos.east().above(), true);
                LevelUtils.safeDestroy(level, pos.north().east().above(), true);
                LevelUtils.safeDestroy(level, pos.south().east().above(), true);
                LevelUtils.safeDestroy(level, pos.east().above(2), true);
                LevelUtils.safeDestroy(level, pos.north().east().above(2), true);
                LevelUtils.safeDestroy(level, pos.south().east().above(2), true);
                LevelUtils.safeDestroy(level, pos.above(2), true);
                LevelUtils.safeDestroy(level, pos.north().above(2), true);
                LevelUtils.safeDestroy(level, pos.south().above(2), true);
                LevelUtils.safeDestroy(level, pos.west().above(2), true);
                LevelUtils.safeDestroy(level, pos.north().west().above(2), true);
                LevelUtils.safeDestroy(level, pos.south().west().above(2), true);
                if (player.getXRot() > 0)
                {
                    LevelUtils.safeDestroy(level, pos.east().below(), true);
                    LevelUtils.safeDestroy(level, pos.north().east().below(), true);
                    LevelUtils.safeDestroy(level, pos.south().east().below(), true);
                    LevelUtils.safeDestroy(level, pos.below(), true);
                    LevelUtils.safeDestroy(level, pos.north().below(), true);
                    LevelUtils.safeDestroy(level, pos.south().below(), true);
                }
            }
            else if (direction == Direction.NORTH)
            {
                LevelUtils.safeDestroy(level, pos, true);
                LevelUtils.safeDestroy(level, pos.west(), true);
                LevelUtils.safeDestroy(level, pos.west().above(), true);
                LevelUtils.safeDestroy(level, pos.east(), true);
                LevelUtils.safeDestroy(level, pos.east().above(), true);
                LevelUtils.safeDestroy(level, pos.north(), true);
                LevelUtils.safeDestroy(level, pos.above(), true);
                LevelUtils.safeDestroy(level, pos.west().north(), true);
                LevelUtils.safeDestroy(level, pos.east().north(), true);
                LevelUtils.safeDestroy(level, pos.north().above(), true);
                LevelUtils.safeDestroy(level, pos.west().north().above(), true);
                LevelUtils.safeDestroy(level, pos.east().north().above(), true);
                LevelUtils.safeDestroy(level, pos.north().above(2), true);
                LevelUtils.safeDestroy(level, pos.west().north().above(2), true);
                LevelUtils.safeDestroy(level, pos.east().north().above(2), true);
                LevelUtils.safeDestroy(level, pos.above(2), true);
                LevelUtils.safeDestroy(level, pos.west().above(2), true);
                LevelUtils.safeDestroy(level, pos.east().above(2), true);
                LevelUtils.safeDestroy(level, pos.south().above(2), true);
                LevelUtils.safeDestroy(level, pos.west().south().above(2), true);
                LevelUtils.safeDestroy(level, pos.east().south().above(2), true);
                if (player.getXRot() > 0)
                {
                    LevelUtils.safeDestroy(level, pos.north().below(), true);
                    LevelUtils.safeDestroy(level, pos.west().north().below(), true);
                    LevelUtils.safeDestroy(level, pos.east().north().below(), true);
                    LevelUtils.safeDestroy(level, pos.below(), true);
                    LevelUtils.safeDestroy(level, pos.west().below(), true);
                    LevelUtils.safeDestroy(level, pos.east().below(), true);
                }
            }
            else if (direction == Direction.SOUTH)
            {
                LevelUtils.safeDestroy(level, pos, true);
                LevelUtils.safeDestroy(level, pos.east(), true);
                LevelUtils.safeDestroy(level, pos.east().above(), true);
                LevelUtils.safeDestroy(level, pos.west(), true);
                LevelUtils.safeDestroy(level, pos.west().above(), true);
                LevelUtils.safeDestroy(level, pos.south(), true);
                LevelUtils.safeDestroy(level, pos.above(), true);
                LevelUtils.safeDestroy(level, pos.east().south(), true);
                LevelUtils.safeDestroy(level, pos.west().south(), true);
                LevelUtils.safeDestroy(level, pos.south().above(), true);
                LevelUtils.safeDestroy(level, pos.east().south().above(), true);
                LevelUtils.safeDestroy(level, pos.west().south().above(), true);
                LevelUtils.safeDestroy(level, pos.south().above(2), true);
                LevelUtils.safeDestroy(level, pos.east().south().above(2), true);
                LevelUtils.safeDestroy(level, pos.west().south().above(2), true);
                LevelUtils.safeDestroy(level, pos.above(2), true);
                LevelUtils.safeDestroy(level, pos.east().above(2), true);
                LevelUtils.safeDestroy(level, pos.west().above(2), true);
                LevelUtils.safeDestroy(level, pos.north().above(2), true);
                LevelUtils.safeDestroy(level, pos.east().north().above(2), true);
                LevelUtils.safeDestroy(level, pos.west().north().above(2), true);
                if (player.getXRot() > 0)
                {
                    LevelUtils.safeDestroy(level, pos.south().below(), true);
                    LevelUtils.safeDestroy(level, pos.east().south().below(), true);
                    LevelUtils.safeDestroy(level, pos.west().south().below(), true);
                    LevelUtils.safeDestroy(level, pos.below(), true);
                    LevelUtils.safeDestroy(level, pos.east().below(), true);
                    LevelUtils.safeDestroy(level, pos.west().below(), true);
                }
            }
            else if (direction == Direction.WEST)
            {
                LevelUtils.safeDestroy(level, pos, true);
                LevelUtils.safeDestroy(level, pos.south(), true);
                LevelUtils.safeDestroy(level, pos.south().above(), true);
                LevelUtils.safeDestroy(level, pos.north(), true);
                LevelUtils.safeDestroy(level, pos.north().above(), true);
                LevelUtils.safeDestroy(level, pos.west(), true);
                LevelUtils.safeDestroy(level, pos.above(), true);
                LevelUtils.safeDestroy(level, pos.south().west(), true);
                LevelUtils.safeDestroy(level, pos.north().west(), true);
                LevelUtils.safeDestroy(level, pos.west().above(), true);
                LevelUtils.safeDestroy(level, pos.south().west().above(), true);
                LevelUtils.safeDestroy(level, pos.north().west().above(), true);
                LevelUtils.safeDestroy(level, pos.west().above(2), true);
                LevelUtils.safeDestroy(level, pos.south().west().above(2), true);
                LevelUtils.safeDestroy(level, pos.north().west().above(2), true);
                LevelUtils.safeDestroy(level, pos.above(2), true);
                LevelUtils.safeDestroy(level, pos.south().above(2), true);
                LevelUtils.safeDestroy(level, pos.north().above(2), true);
                LevelUtils.safeDestroy(level, pos.east().above(2), true);
                LevelUtils.safeDestroy(level, pos.south().east().above(2), true);
                LevelUtils.safeDestroy(level, pos.north().east().above(2), true);
                if (player.getXRot() > 0)
                {
                    LevelUtils.safeDestroy(level, pos.west().below(), true);
                    LevelUtils.safeDestroy(level, pos.south().west().below(), true);
                    LevelUtils.safeDestroy(level, pos.north().west().below(), true);
                    LevelUtils.safeDestroy(level, pos.below(), true);
                    LevelUtils.safeDestroy(level, pos.south().below(), true);
                    LevelUtils.safeDestroy(level, pos.north().below(), true);
                }
            }
            else
            {
                Minejago.LOGGER.error("Unknown/unsupported direction for scythe staircase");
            }
            ItemAttributeModifiers original = stack.get(DataComponents.ATTRIBUTE_MODIFIERS);
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
            if (original != null)
            {
                original.modifiers().forEach(entry -> builder.add(entry.attribute(), entry.modifier(), entry.slot()));
            }
            builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(STAIRCASE_SPEED_MODIFIER, -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.HAND);
            stack.set(DataComponents.ATTRIBUTE_MODIFIERS, builder.build());
            level.playSound(null, player.blockPosition(), MinejagoSoundEvents.SCYTHE_OF_QUAKES_PATH.get(), SoundSource.PLAYERS);
        }
    }

    @Override
    protected void goCrazy(Player player) {
        if (!player.level().isClientSide && !player.getAbilities().instabuild)
        {
            player.level().explode(null, player.getX(), player.getY() + 1, player.getZ(), 8.0F, Level.ExplosionInteraction.TNT);
            TommyLibServices.NETWORK.sendToAllClients(new ClientboundStartScytheAnimationPayload(player.getUUID(), ItemAnimations.ScytheOfQuakes.SLAM_START, Optional.empty()), player.getServer());
        }
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pMiningEntity) {
        return !pState.is(ConventionalBlockTags.UNBREAKABLE_BLOCKS);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack itemStack, BlockState blockState)
    {
        return !blockState.is(ConventionalBlockTags.UNBREAKABLE_BLOCKS);
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        return 25.0f;
    }
    @Override
    public @Nullable SoundEvent getFailSound() {
        return MinejagoSoundEvents.SCYTHE_OF_QUAKES_FAIL.get();
    }
}
