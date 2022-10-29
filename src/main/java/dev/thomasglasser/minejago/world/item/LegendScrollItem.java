package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.gui.screens.LegendScrollScreen;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SimpleFoiledItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class LegendScrollItem extends SimpleFoiledItem
{
    public LegendScrollItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (pLevel.isClientSide() && getLegend(stack) != Legends.EMPTY)
        {
            Minecraft.getInstance().setScreen(new LegendScrollScreen(Component.empty(), getLegend(stack)));
            pPlayer.playSound(MinejagoSoundEvents.FOUR_WEAPONS_LEGEND_SCROLL.get(), 1.0F, 1.0F);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable(this.getDescriptionId() + "." + getLegend(pStack).getLegend()).withStyle(ChatFormatting.GRAY));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    public void setLegend(Legends legend, ItemStack stack) {
        stack.getOrCreateTag().putString("legend", legend.getLegend());
    }

    public Legends getLegend(ItemStack stack)
    {
        return Legends.of(stack.getOrCreateTag().getString("legend"));
    }

    public enum Legends
    {
        FOUR_WEAPONS("four_weapons"),
        EMPTY("empty");

        private final String legend;

        Legends(String legend) {
            this.legend = legend;
        }

        public String getLegend()
        {
            return legend;
        }

        public static Legends of(String legend)
        {
            switch (legend)
            {
                case "four_weapons" -> {
                    return FOUR_WEAPONS;
                }

                default -> {
                    return Legends.EMPTY;
                }
            }
        }
    }
}
