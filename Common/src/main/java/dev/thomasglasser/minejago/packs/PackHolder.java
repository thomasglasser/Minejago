package dev.thomasglasser.minejago.packs;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

public record PackHolder(ResourceLocation id, String titleKey, boolean required, PackType type) {
}
