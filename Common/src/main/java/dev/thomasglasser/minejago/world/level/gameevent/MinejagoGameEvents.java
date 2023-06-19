package dev.thomasglasser.minejago.world.level.gameevent;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;

import java.util.HashMap;
import java.util.Map;

public class MinejagoGameEvents {
    public static final RegistrationProvider<GameEvent> GAME_EVENTS = RegistrationProvider.get(Registries.GAME_EVENT, Minejago.MOD_ID);
    public static final Map<ResourceLocation, Integer> VIBRATION_FREQUENCY_FOR_EVENT = new HashMap<>();
    public static RegistryObject<GameEvent> SPINJITZU = register("spinjitzu", 9);

    private static RegistryObject<GameEvent> register(String name, int freq)
    {
        RegistryObject<GameEvent> event = GAME_EVENTS.register(name, ()-> new GameEvent(name, GameEvent.DEFAULT_NOTIFICATION_RADIUS));
        VIBRATION_FREQUENCY_FOR_EVENT.put(Minejago.modLoc(name), freq);
        return event;
    }

    public static int getGameEventFrequency(GameEvent gameEvent)
    {
        return VIBRATION_FREQUENCY_FOR_EVENT.getOrDefault(BuiltInRegistries.GAME_EVENT.getKey(gameEvent), VibrationSystem.getGameEventFrequency(gameEvent));
    }

    public static void init() {}
}