package dev.thomasglasser.minejago.world.level.gameevent;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MinejagoGameEvents {
    public static final Map<ResourceLocation, Integer> VIBRATION_FREQUENCY_FOR_EVENT = new HashMap<>();
    public static Supplier<GameEvent> SPINJITZU = register("spinjitzu", 9);

    private static Supplier<GameEvent> register(String name, int freq)
    {
        Supplier<GameEvent> event = Services.REGISTRATION.register(BuiltInRegistries.GAME_EVENT, name, ()-> new GameEvent(GameEvent.DEFAULT_NOTIFICATION_RADIUS));
        VIBRATION_FREQUENCY_FOR_EVENT.put(Minejago.modLoc(name), freq);
        return event;
    }

    public static int getGameEventFrequency(GameEvent gameEvent)
    {
        return VIBRATION_FREQUENCY_FOR_EVENT.getOrDefault(BuiltInRegistries.GAME_EVENT.getKey(gameEvent), VibrationSystem.getGameEventFrequency(gameEvent));
    }

    public static void init() {}
}