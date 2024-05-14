package dev.thomasglasser.minejago.world.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class FilledTeacupItem extends PotionItem implements PotionCupHolder
{
    public FilledTeacupItem(Properties pProperties) {
        super(pProperties);
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        Player player = pEntityLiving instanceof Player ? (Player)pEntityLiving : null;
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, pStack);
        }

        if (!pLevel.isClientSide && pStack.has(DataComponents.POTION_CONTENTS)) {
            for(MobEffectInstance mobEffectInstance : pStack.get(DataComponents.POTION_CONTENTS).getAllEffects()) {
                pEntityLiving.addEffect(new MobEffectInstance(mobEffectInstance));
            }
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                pStack.shrink(1);
            }
        }

        if (player == null || !player.getAbilities().instabuild) {
            if (pStack.isEmpty()) {
                return new ItemStack(MinejagoItems.TEACUP.get());
            }

            if (player != null) {
                player.getInventory().add(new ItemStack(MinejagoItems.TEACUP.get()));
            }
        }

        pEntityLiving.gameEvent(GameEvent.DRINK);
        return pStack;
    }

    /**
     * Called when this item is used when targeting a Block
     */
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        Player player = pContext.getPlayer();
        ItemStack itemstack = pContext.getItemInHand();
        BlockState blockstate = level.getBlockState(blockpos);
        if (pContext.getClickedFace() != Direction.DOWN && blockstate.is(BlockTags.CONVERTABLE_TO_MUD) && itemstack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).is(Potions.WATER))
        {
            level.playSound(null, blockpos, SoundEvents.GENERIC_SPLASH, SoundSource.PLAYERS, 1.0F, 1.0F);
            player.setItemInHand(pContext.getHand(), ItemUtils.createFilledResult(itemstack, player, new ItemStack(MinejagoItems.TEACUP.get())));
            player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
            if (!level.isClientSide) {
                ServerLevel serverlevel = (ServerLevel)level;

                for(int i = 0; i < 5; ++i) {
                    serverlevel.sendParticles(ParticleTypes.SPLASH, (double)blockpos.getX() + level.random.nextDouble(), (double)(blockpos.getY() + 1), (double)blockpos.getZ() + level.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
                }
            }

            level.playSound(null, blockpos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PLACE, blockpos);
            level.setBlockAndUpdate(blockpos, Blocks.MUD.defaultBlockState());
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public int getCups() {
        return 1;
    }

    @Override
    public boolean canBeDrained(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getDrained(ItemStack stack) {
        return new ItemStack(MinejagoItems.TEACUP.get());
    }

    @Override
    public ItemStack getFilled(Holder<Potion> potion) {
        return null;
    }

    @Override
    public boolean canBeFilled(ItemStack stack, Holder<Potion> potion, int cups) {
        return false;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack)
    {
        return UseAnim.DRINK;
    }
}
