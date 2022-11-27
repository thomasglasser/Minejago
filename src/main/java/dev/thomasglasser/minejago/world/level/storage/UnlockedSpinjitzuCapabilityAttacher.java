package dev.thomasglasser.minejago.world.level.storage;

import dev._100media.capabilitysyncer.core.CapabilityAttacher;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import javax.annotation.Nullable;

public class UnlockedSpinjitzuCapabilityAttacher extends CapabilityAttacher {
    private static final Class<UnlockedSpinjitzuCapability> CAPABILITY_CLASS = UnlockedSpinjitzuCapability.class;
    public static final Capability<UnlockedSpinjitzuCapability> UNLOCKED_SPINJITZU_CAPABILITY = getCapability(new CapabilityToken<>() {});
    public static final ResourceLocation UNLOCKED_SPINJITZU_CAPABILITY_RL = new ResourceLocation(Minejago.MOD_ID, "unlocked_spinjitzu_capability");

    @Nullable
    public static UnlockedSpinjitzuCapability getUnlockedSpinjitzuCapabilityUnwrap(Player player) {
        return getUnlockedSpinjitzuCapability(player).orElse(null);
    }

    public static LazyOptional<UnlockedSpinjitzuCapability> getUnlockedSpinjitzuCapability(Player player) {
        return player.getCapability(UNLOCKED_SPINJITZU_CAPABILITY);
    }

    private static void attach(AttachCapabilitiesEvent<Entity> event, Player player) {
        genericAttachCapability(event, new UnlockedSpinjitzuCapability(player), UNLOCKED_SPINJITZU_CAPABILITY, UNLOCKED_SPINJITZU_CAPABILITY_RL);
    }

    public static void register() {
        CapabilityAttacher.registerCapability(CAPABILITY_CLASS);
        CapabilityAttacher.registerPlayerAttacher(UnlockedSpinjitzuCapabilityAttacher::attach, UnlockedSpinjitzuCapabilityAttacher::getUnlockedSpinjitzuCapability, true);
    }}
