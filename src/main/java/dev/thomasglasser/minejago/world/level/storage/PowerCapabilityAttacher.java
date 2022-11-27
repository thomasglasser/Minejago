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

public class PowerCapabilityAttacher extends CapabilityAttacher {
    private static final Class<PowerCapability> CAPABILITY_CLASS = PowerCapability.class;
    public static final Capability<PowerCapability> POWER_CAPABILITY = getCapability(new CapabilityToken<>() {});
    public static final ResourceLocation POWER_CAPABILITY_RL = new ResourceLocation(Minejago.MOD_ID, "power_capability");

    @Nullable
    public static PowerCapability getPowerCapabilityUnwrap(Player player) {
        return getPowerCapability(player).orElse(null);
    }

    public static LazyOptional<PowerCapability> getPowerCapability(Player player) {
        return player.getCapability(POWER_CAPABILITY);
    }

    private static void attach(AttachCapabilitiesEvent<Entity> event, Player player) {
        genericAttachCapability(event, new PowerCapability(player), POWER_CAPABILITY, POWER_CAPABILITY_RL);
    }

    public static void register() {
        CapabilityAttacher.registerCapability(CAPABILITY_CLASS);
        CapabilityAttacher.registerPlayerAttacher(PowerCapabilityAttacher::attach, PowerCapabilityAttacher::getPowerCapability, true);
    }}
