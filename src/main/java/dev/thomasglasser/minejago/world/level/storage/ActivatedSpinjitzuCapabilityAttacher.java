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

public class ActivatedSpinjitzuCapabilityAttacher extends CapabilityAttacher {
    private static final Class<ActivatedSpinjitzuCapability> CAPABILITY_CLASS = ActivatedSpinjitzuCapability.class;
    public static final Capability<ActivatedSpinjitzuCapability> ACTIVATED_SPINJITZU_CAPABILITY = getCapability(new CapabilityToken<>() {});
    public static final ResourceLocation ACTIVATED_SPINJITZU_CAPABILITY_RL = new ResourceLocation(Minejago.MOD_ID, "activated_spinjitzu_capability");

    @Nullable
    public static ActivatedSpinjitzuCapability getActivatedSpinjitzuCapabilityUnwrap(Player player) {
        return getActivatedSpinjitzuCapability(player).orElse(null);
    }

    public static LazyOptional<ActivatedSpinjitzuCapability> getActivatedSpinjitzuCapability(Player player) {
        return player.getCapability(ACTIVATED_SPINJITZU_CAPABILITY);
    }

    private static void attach(AttachCapabilitiesEvent<Entity> event, Player player) {
        genericAttachCapability(event, new ActivatedSpinjitzuCapability(player), ACTIVATED_SPINJITZU_CAPABILITY, ACTIVATED_SPINJITZU_CAPABILITY_RL);
    }

    public static void register() {
        CapabilityAttacher.registerCapability(CAPABILITY_CLASS);
        CapabilityAttacher.registerPlayerAttacher(ActivatedSpinjitzuCapabilityAttacher::attach, ActivatedSpinjitzuCapabilityAttacher::getActivatedSpinjitzuCapability, false);
    }}
