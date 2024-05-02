package dev.thomasglasser.minejago.world.level.gameevent;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.registration.RegistrationProvider;
import dev.thomasglasser.tommylib.api.registration.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.HashMap;
import java.util.Map;

public class MinejagoGameEvents {
    public static final RegistrationProvider<GameEvent> GAME_EVENTS = RegistrationProvider.get(BuiltInRegistries.GAME_EVENT, Minejago.MOD_ID);

    public static final Map<ResourceKey<GameEvent>, Integer> VIBRATION_FREQUENCY_FOR_EVENT = new HashMap<>();
    public static RegistryObject<GameEvent> SPINJITZU = register("spinjitzu", 9);

    private static RegistryObject<GameEvent> register(String name, int freq)
    {
        RegistryObject<GameEvent> event = GAME_EVENTS.register(name, ()-> new GameEvent(GameEvent.DEFAULT_NOTIFICATION_RADIUS));
        VIBRATION_FREQUENCY_FOR_EVENT.put(ResourceKey.create(Registries.GAME_EVENT, Minejago.modLoc(name)), freq);
        return event;
    }

    public static int getGameEventFrequency(ResourceKey<GameEvent> gameEvent)
    {
        return VIBRATION_FREQUENCY_FOR_EVENT.getOrDefault(gameEvent, 0);
    }

    public static void init() {}
}