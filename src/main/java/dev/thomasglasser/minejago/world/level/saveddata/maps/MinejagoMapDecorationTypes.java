package dev.thomasglasser.minejago.world.level.saveddata.maps;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;

public class MinejagoMapDecorationTypes {
    public static final DeferredRegister<MapDecorationType> MAP_DECORATION_TYPES = DeferredRegister.create(Registries.MAP_DECORATION_TYPE, Minejago.MOD_ID);

    public static final DeferredHolder<MapDecorationType, MapDecorationType> NUNCHUCKS_OF_LIGHTNING = register("nunchucks_of_lightning", true, -1, true, true);
    public static final DeferredHolder<MapDecorationType, MapDecorationType> SWORD_OF_FIRE = register("sword_of_fire", true, -1, true, true);
    public static final DeferredHolder<MapDecorationType, MapDecorationType> SCYTHE_OF_QUAKES = register("scythe_of_quakes", true, -1, true, true);
    public static final DeferredHolder<MapDecorationType, MapDecorationType> SHURIKENS_OF_ICE = register("shurikens_of_ice", true, -1, true, true);

    private static DeferredHolder<MapDecorationType, MapDecorationType> register(String name, boolean showOnItemFrame, int mapColor, boolean explorationMapElement, boolean trackCount) {
        return MAP_DECORATION_TYPES.register(name, () -> new MapDecorationType(Minejago.modLoc(name), showOnItemFrame, mapColor, explorationMapElement, trackCount));
    }

    public static void init() {}
}
