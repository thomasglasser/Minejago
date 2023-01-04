package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.core.registries.MinejagoRegistryKeys;
import dev.thomasglasser.minejago.platform.services.IRegistryHelper;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public class ForgeRegistryHelper implements IRegistryHelper {
    @Override
    public <A> Supplier<Registry<A>> createRegistry(String name, ResourceKey<Registry<A>> registry, Class<A> type, Supplier<A> def) {
        return () -> BuiltInRegistries.registerSimple(registry, (reg) -> def.get());
    }
}
