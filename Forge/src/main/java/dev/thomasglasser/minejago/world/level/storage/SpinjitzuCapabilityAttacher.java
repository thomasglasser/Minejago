package dev.thomasglasser.minejago.world.level.storage;

import dev._100media.capabilitysyncer.core.CapabilityAttacher;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import javax.annotation.Nullable;

public class SpinjitzuCapabilityAttacher extends CapabilityAttacher {
    private static final Class<SpinjitzuCapability> CAPABILITY_CLASS = SpinjitzuCapability.class;
    public static final Capability<SpinjitzuCapability> SPINJITZU_CAPABILITY = getCapability(new CapabilityToken<>() {});
    public static final ResourceLocation SPINJITZU_CAPABILITY_RL = new ResourceLocation(Minejago.MOD_ID, "spinjitzu_capability");

    @Nullable
    public static SpinjitzuCapability getSpinjitzuCapabilityUnwrap(LivingEntity player) {
        return getSpinjitzuCapability(player).orElse(new SpinjitzuCapability(player));
    }

    public static LazyOptional<SpinjitzuCapability> getSpinjitzuCapability(LivingEntity player) {
        return player.getCapability(SPINJITZU_CAPABILITY);
    }

    private static void attach(AttachCapabilitiesEvent<Entity> event, LivingEntity player) {
        genericAttachCapability(event, new SpinjitzuCapability(player), SPINJITZU_CAPABILITY, SPINJITZU_CAPABILITY_RL);
    }

    public static void register() {
        CapabilityAttacher.registerCapability(CAPABILITY_CLASS);
        CapabilityAttacher.registerEntityAttacher(LivingEntity.class, SpinjitzuCapabilityAttacher::attach, SpinjitzuCapabilityAttacher::getSpinjitzuCapability, false);
    }}
