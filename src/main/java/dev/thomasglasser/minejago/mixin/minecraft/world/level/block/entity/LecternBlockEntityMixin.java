package dev.thomasglasser.minejago.mixin.minecraft.world.level.block.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LecternBlockEntity.class)
public abstract class LecternBlockEntityMixin extends BlockEntity
{
    @Shadow
    ItemStack book;

    @Shadow
    public abstract CommandSourceStack createCommandSourceStack(@Nullable Player pPlayer);

    @Shadow protected abstract void saveAdditional(CompoundTag pTag, HolderLookup.Provider provider);

    private LecternBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @ModifyReturnValue(method = "hasBook", at = @At("TAIL"))
    private boolean minejago_hasBook(boolean original)
    {
        return original || book.is(ItemTags.LECTERN_BOOKS);
    }

    @Inject(method = "resolveBook", at = @At("HEAD"), cancellable = true)
    private void minejago_resolveBook(ItemStack stack, Player player, CallbackInfoReturnable<ItemStack> cir)
    {
        if (level instanceof ServerLevel && (stack.is(MinejagoItems.WRITTEN_SCROLL.get()) || stack.is(MinejagoItems.WRITABLE_SCROLL.get()))) {
            WrittenBookItem.resolveBookComponents(stack, createCommandSourceStack(player), player);

            cir.setReturnValue(stack);
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = super.getUpdateTag(provider);

        saveAdditional(tag, provider);

        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
