package dev.thomasglasser.minejago.mixin.minecraft.client.player;

import dev.thomasglasser.minejago.client.gui.screens.inventory.ScrollEditScreen;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.WritableBookContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Unique
    private final LocalPlayer minejago$INSTANCE = ((LocalPlayer) (Object) this);

    @Inject(method = "openItemGui", at = @At("HEAD"), cancellable = true)
    private void minejago_openItemGui(ItemStack stack, InteractionHand hand, CallbackInfo ci) {
        if (stack.is(MinejagoItems.WRITABLE_SCROLL.get())) {
            WritableBookContent writablebookcontent = stack.get(DataComponents.WRITABLE_BOOK_CONTENT);
            ClientUtils.setScreen(new ScrollEditScreen(minejago$INSTANCE, stack, hand, writablebookcontent));
            ci.cancel();
        }
    }
}
