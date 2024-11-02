package dev.thomasglasser.minejago.mixin.minecraft.server.network;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.Filterable;
import net.minecraft.server.network.FilteredText;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.WritableBookContent;
import net.minecraft.world.item.component.WrittenBookContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin {
    @Shadow
    public ServerPlayer player;

    @Shadow
    protected abstract Filterable<String> filterableFromOutgoing(FilteredText p_332041_);

    @Unique
    private final ServerGamePacketListenerImpl minejago$INSTANCE = ((ServerGamePacketListenerImpl) (Object) this);

    @Inject(method = "signBook", at = @At("HEAD"))
    private void minejago_signBook(FilteredText title, List<FilteredText> pages, int index, CallbackInfo ci) {
        ItemStack itemStack = minejago$INSTANCE.player.getInventory().getItem(index);
        if (itemStack.is(MinejagoItems.WRITABLE_SCROLL.get())) {
            ItemStack itemStack2 = itemStack.transmuteCopy(MinejagoItems.WRITTEN_SCROLL.get(), 1);
            itemStack2.remove(DataComponents.WRITABLE_BOOK_CONTENT);
            List<Filterable<Component>> list = pages.stream().map((filteredText) -> filterableFromOutgoing(filteredText).<Component>map(Component::literal)).toList();
            itemStack2.set(DataComponents.WRITTEN_BOOK_CONTENT, new WrittenBookContent(this.filterableFromOutgoing(title), this.player.getName().getString(), 0, list, true));
            player.getInventory().setItem(index, itemStack2);
        }
    }

    @Inject(method = "updateBookContents", at = @At("HEAD"))
    private void minejago_updateBookContents(List<FilteredText> pages, int index, CallbackInfo ci) {
        ItemStack itemStack = minejago$INSTANCE.player.getInventory().getItem(index);
        if (itemStack.is(MinejagoItems.WRITABLE_SCROLL.get())) {
            List<Filterable<String>> list = pages.stream().map(this::filterableFromOutgoing).toList();
            itemStack.set(DataComponents.WRITABLE_BOOK_CONTENT, new WritableBookContent(list));
        }
    }
}
