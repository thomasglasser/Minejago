package dev.thomasglasser.minejago.world.level.storage;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.world.entity.Entity;

public class MinejagoFabricEntityComponents implements EntityComponentInitializer
{
    public static final ComponentKey<PowerComponent> POWER = ComponentRegistry.getOrCreate(Minejago.modLoc("power"), PowerComponent.class);
    public static final ComponentKey<SpinjitzuComponent> SPINJITZU = ComponentRegistry.getOrCreate(Minejago.modLoc("spinjitzu"), SpinjitzuComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(Entity.class, POWER, entity -> new EntityPowerComponent());
        registry.registerFor(Entity.class, SPINJITZU, entity -> new EntitySpinjitzuComponent());
    }
}
