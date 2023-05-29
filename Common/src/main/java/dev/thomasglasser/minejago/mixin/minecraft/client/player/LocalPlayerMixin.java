package dev.thomasglasser.minejago.mixin.minecraft.client.player;

import dev.thomasglasser.minejago.client.gui.screens.inventory.ScrollEditScreen;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin
{
    private final LocalPlayer INSTANCE = ((LocalPlayer) (Object) this);

    @Inject(method = "openItemGui", at = @At("HEAD"))
    private void minejago_openItemGui(ItemStack stack, InteractionHand hand, CallbackInfo ci)
    {
        if (stack.is(MinejagoItems.WRITABLE_SCROLL.get())) {
            MinejagoClientUtils.setScreen(new ScrollEditScreen(INSTANCE, stack, hand));
        }

    }
}
