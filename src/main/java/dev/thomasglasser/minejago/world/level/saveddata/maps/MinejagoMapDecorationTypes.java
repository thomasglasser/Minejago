package dev.thomasglasser.minejago.world.level.saveddata.maps;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;

public class MinejagoMapDecorationTypes {
    public static final DeferredRegister<MapDecorationType> MAP_DECORATION_TYPES = DeferredRegister.create(Registries.MAP_DECORATION_TYPE, Minejago.MOD_ID);

    public static final DeferredHolder<MapDecorationType, MapDecorationType> FOUR_WEAPONS = register("four_weapons", true, -1, true, true);
    public static final DeferredHolder<MapDecorationType, MapDecorationType> NINJAGO_CITY = register("ninjago_city", true, -1, true, true);
    public static final DeferredHolder<MapDecorationType, MapDecorationType> MONASTERY_OF_SPINJITZU = register("monastery_of_spinjitzu", true, -1, true, true);
    public static final DeferredHolder<MapDecorationType, MapDecorationType> CAVE_OF_DESPAIR = register("cave_of_despair", true, -1, true, true);
    public static final DeferredHolder<MapDecorationType, MapDecorationType> ICE_TEMPLE = register("ice_temple", true, -1, true, true);
    public static final DeferredHolder<MapDecorationType, MapDecorationType> FLOATING_RUINS = register("floating_ruins", true, -1, true, true);
    public static final DeferredHolder<MapDecorationType, MapDecorationType> FIRE_TEMPLE = register("fire_temple", true, -1, true, true);

    private static DeferredHolder<MapDecorationType, MapDecorationType> register(String name, boolean showOnItemFrame, int mapColor, boolean explorationMapElement, boolean trackCount) {
        return MAP_DECORATION_TYPES.register(name, () -> new MapDecorationType(Minejago.modLoc(name), showOnItemFrame, mapColor, explorationMapElement, trackCount));
    }

    public static void init() {}
}
