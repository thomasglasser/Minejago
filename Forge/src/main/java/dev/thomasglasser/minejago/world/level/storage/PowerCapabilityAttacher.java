package dev.thomasglasser.minejago.world.level.storage;

import dev._100media.capabilitysyncer.core.CapabilityAttacher;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
    public static PowerCapability getPowerCapabilityUnwrap(LivingEntity player) {
        return getPowerCapability(player).orElse(null);
    }

    public static LazyOptional<PowerCapability> getPowerCapability(LivingEntity player) {
        return player.getCapability(POWER_CAPABILITY);
    }

    private static void attach(AttachCapabilitiesEvent<Entity> event, LivingEntity player) {
        genericAttachCapability(event, new PowerCapability(player), POWER_CAPABILITY, POWER_CAPABILITY_RL);
    }

    public static void register() {
        CapabilityAttacher.registerCapability(CAPABILITY_CLASS);
        CapabilityAttacher.registerEntityAttacher(LivingEntity.class, PowerCapabilityAttacher::attach, PowerCapabilityAttacher::getPowerCapability);
    }}
