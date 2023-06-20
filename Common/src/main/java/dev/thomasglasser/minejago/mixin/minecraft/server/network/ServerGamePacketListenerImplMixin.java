package dev.thomasglasser.minejago.mixin.minecraft.server.network;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.network.FilteredText;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.UnaryOperator;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin
{
    private final ServerGamePacketListenerImpl INSTANCE = ((ServerGamePacketListenerImpl)(Object)this);

    @Inject(method = "signBook",  at = @At("HEAD"))
    private void minejago_signBook(FilteredText title, List<FilteredText> pages, int index, CallbackInfo ci)
    {
        ItemStack itemStack = INSTANCE.player.getInventory().getItem(index);
        if (itemStack.is(MinejagoItems.WRITABLE_SCROLL.get())) {
            ItemStack itemStack2 = MinejagoItems.WRITTEN_SCROLL.get().getDefaultInstance();
            CompoundTag compoundTag = itemStack.getTag();
            if (compoundTag != null) {
                itemStack2.setTag(compoundTag.copy());
            }

            itemStack2.addTagElement("author", StringTag.valueOf(INSTANCE.player.getName().getString()));
            if (INSTANCE.player.isTextFilteringEnabled()) {
                itemStack2.addTagElement("title", StringTag.valueOf(title.filteredOrEmpty()));
            } else {
                itemStack2.addTagElement("filtered_title", StringTag.valueOf(title.filteredOrEmpty()));
                itemStack2.addTagElement("title", StringTag.valueOf(title.raw()));
            }

            INSTANCE.updateBookPages(pages, string -> Component.Serializer.toJson(Component.literal(string)), itemStack2);
            INSTANCE.player.getInventory().setItem(index, itemStack2);
        }
    }

    @Inject(method = "updateBookContents", at = @At("HEAD"))
    private void minejago_updateBookContents(List<FilteredText> pages, int index, CallbackInfo ci)
    {
        ItemStack itemStack = INSTANCE.player.getInventory().getItem(index);
        if (itemStack.is(MinejagoItems.WRITABLE_SCROLL.get())) {
            INSTANCE.updateBookPages(pages, UnaryOperator.identity(), itemStack);
        }
    }
}
