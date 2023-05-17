package dev.thomasglasser.minejago.world.entity.npc;

import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.network.ClientboundOpenPowerSelectionScreenPacket;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowersConfig;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Wu extends Character
{
    public static final String POWER_GIVEN_KEY = "gui.power_selection.power_given";
    private static List<ResourceKey<Power>> powersToGive = new ArrayList<>();

    public Wu(EntityType<? extends Wu> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Character.createAttributes()
                .add(Attributes.MAX_HEALTH, ((RangedAttribute)Attributes.MAX_HEALTH).getMaxValue())
                .add(Attributes.ATTACK_KNOCKBACK, ((RangedAttribute)Attributes.ATTACK_KNOCKBACK).getMaxValue());
    }

    @Override
    public boolean isInvulnerableTo(DamageSource pSource) {
        if (!(pSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)))
            return true;
        return super.isInvulnerableTo(pSource);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, MinejagoItems.BAMBOO_STAFF.get().getDefaultInstance());
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        populateDefaultEquipmentSlots(random, pDifficulty);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        Registry<Power> registry = level.registryAccess().registry(MinejagoRegistries.POWER).orElseThrow();

        if (!MinejagoPowersConfig.DRAIN_POOL.get() || powersToGive.isEmpty())
        {
            powersToGive = new ArrayList<>(registry.registryKeySet());
            if (!MinejagoPowersConfig.ALLOW_NONE.get())
                powersToGive.removeIf(powerResourceKey -> registry.get(powerResourceKey) != null && !registry.get(powerResourceKey).isSelectable());
        }

        if (player instanceof ServerPlayer serverPlayer && hand == InteractionHand.MAIN_HAND)
        {
            if (!Services.DATA.getPowerData(serverPlayer).given() || MinejagoPowersConfig.ALLOW_CHANGE.get()) {
                if (MinejagoPowersConfig.ALLOW_CHOOSE.get())
                {
                    Services.NETWORK.sendToClient(ClientboundOpenPowerSelectionScreenPacket.class, ClientboundOpenPowerSelectionScreenPacket.toBytes(powersToGive), serverPlayer);
                }
                else
                {
                    ResourceKey<Power> key = Services.DATA.getPowerData(serverPlayer).power();
                    if (key != MinejagoPowers.NONE && MinejagoPowersConfig.DRAIN_POOL.get()) powersToGive.add(key);
                    ResourceKey<Power> power = powersToGive.remove(random.nextInt(powersToGive.size()));
                    Services.DATA.setPowerData(new PowerData(power, true), serverPlayer);
                    serverPlayer.displayClientMessage(Component.translatable(POWER_GIVEN_KEY), true);
                }
            }
        }
        return super.mobInteract(player, hand);
    }

    public static List<ResourceKey<Power>> getPowersToGive() {
        return powersToGive;
    }
}
