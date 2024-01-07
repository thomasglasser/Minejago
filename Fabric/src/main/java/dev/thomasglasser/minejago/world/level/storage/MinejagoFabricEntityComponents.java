package dev.thomasglasser.minejago.world.level.storage;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.world.entity.LivingEntity;

public class MinejagoFabricEntityComponents implements EntityComponentInitializer
{
    public static final ComponentKey<PowerComponent> POWER = ComponentRegistry.getOrCreate(Minejago.modLoc("power"), PowerComponent.class);
    public static final ComponentKey<SpinjitzuComponent> SPINJITZU = ComponentRegistry.getOrCreate(Minejago.modLoc("spinjitzu"), SpinjitzuComponent.class);
    public static final ComponentKey<FocusComponent> FOCUS = ComponentRegistry.getOrCreate(Minejago.modLoc("focus"), FocusComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(POWER, entity -> new PowerComponent());
        registry.registerForPlayers(SPINJITZU, entity -> new SpinjitzuComponent());
        registry.registerForPlayers(FOCUS, entity -> new FocusComponent());

        registry.registerFor(LivingEntity.class, POWER, entity -> new PowerComponent());
        registry.registerFor(LivingEntity.class, SPINJITZU, entity -> new SpinjitzuComponent());
        registry.registerFor(LivingEntity.class, FOCUS, entity -> new FocusComponent());
    }
}
