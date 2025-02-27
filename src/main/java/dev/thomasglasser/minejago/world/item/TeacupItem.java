package dev.thomasglasser.minejago.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class TeacupItem extends BottleItem {
    private final ItemLike teacup;

    public TeacupItem(Properties pProperties, ItemLike teacup) {
        super(pProperties);
        this.teacup = teacup;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        HitResult hitresult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.SOURCE_ONLY);
        if (hitresult.getType() != HitResult.Type.MISS) {
            if (hitresult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockpos = ((BlockHitResult) hitresult).getBlockPos();
                if (!pLevel.mayInteract(pPlayer, blockpos)) {
                    return InteractionResultHolder.pass(itemstack);
                }

                if (pLevel.getFluidState(blockpos).is(FluidTags.WATER)) {
                    pLevel.playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    pLevel.gameEvent(pPlayer, GameEvent.FLUID_PICKUP, blockpos);
                    return InteractionResultHolder.success(this.turnBottleIntoItem(itemstack, pPlayer, PotionContents.createItemStack(teacup.asItem(), Potions.WATER)));
                }
            }

        }
        return InteractionResultHolder.pass(itemstack);
    }
}
