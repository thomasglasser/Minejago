package dev.thomasglasser.minejago.world.entity.shadow;

import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.focus.FocusData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ShadowSource extends AbstractShadowCopy {
    @Nullable
    private ListTag inventory;

    public ShadowSource(EntityType<? extends ShadowSource> entityType, Level level) {
        super(entityType, level);
        this.inventory = null;
    }

    public ShadowSource(LivingEntity owner) {
        super(MinejagoEntityTypes.SHADOW_SOURCE.get(), owner);
        getData(MinejagoAttachmentTypes.FOCUS).setMeditationType(FocusData.MeditationType.MEGA);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            owner.setItemSlot(slot, ItemStack.EMPTY);
        }
        if (owner instanceof Player player) {
            this.inventory = player.getInventory().save(new ListTag());
            player.getInventory().clearContent();
        } else
            this.inventory = null;
    }

    @Override
    protected float getEquipmentDropChance(EquipmentSlot slot) {
        return 0.0f;
    }

    public @Nullable ListTag getInventory() {
        return this.inventory;
    }

    public void clearInventory() {
        this.inventory = null;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (getOwner() instanceof ServerPlayer owner) {
            MinejagoEntityEvents.stopShadowForm(owner);
            owner.hurt(source, amount);
            if (!isRemoved())
                remove(RemovalReason.KILLED);
            return true;
        }
        return false;
    }

    @Override
    public void remove(RemovalReason reason) {
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.getChunkSource().removeRegionTicket(TicketType.PLAYER, chunkPosition(), 3, chunkPosition());
        }
        if (inventory != null) {
            for (int i = 0; i < inventory.size(); i++) {
                CompoundTag compoundtag = inventory.getCompound(i);
                ItemStack itemstack = ItemStack.parse(registryAccess(), compoundtag).orElse(ItemStack.EMPTY);
                if (!itemstack.isEmpty()) {
                    ItemEntity itementity = new ItemEntity(level(), getX(), getY(), getZ(), itemstack);
                    itementity.setDefaultPickUpDelay();
                    level().addFreshEntity(itementity);
                }
            }
            clearInventory();
        }
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack itemstack = getItemBySlot(slot);
            if (!itemstack.isEmpty()) {
                ItemEntity itementity = new ItemEntity(level(), getX(), getY(), getZ(), itemstack);
                itementity.setDefaultPickUpDelay();
                level().addFreshEntity(itementity);
            }
        }
        super.remove(reason);
    }

    @Override
    public float getScale() {
        LivingEntity owner = getOwner();
        if (owner != null) {
            return (float) owner.getAttributeBaseValue(Attributes.SCALE);
        }
        return super.getScale();
    }
}
