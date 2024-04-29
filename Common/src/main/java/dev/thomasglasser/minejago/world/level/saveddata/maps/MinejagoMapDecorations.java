package dev.thomasglasser.minejago.world.level.saveddata.maps;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.registration.RegistrationProvider;
import dev.thomasglasser.tommylib.api.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;

public class MinejagoMapDecorations
{
    public static final RegistrationProvider<MapDecorationType> MAP_DECORATION_TYPES = RegistrationProvider.get(Registries.MAP_DECORATION_TYPE, Minejago.MOD_ID);

    public static final RegistryObject<MapDecorationType> NUNCHUCKS_OF_LIGHTNING = register("nunchucks_of_lightning", true, -1, true, true);
    public static final RegistryObject<MapDecorationType> SWORD_OF_FIRE = register("sword_of_fire", true, -1, true, true);
    public static final RegistryObject<MapDecorationType> SCYTHE_OF_QUAKES = register("scythe_of_quakes", true, -1, true, true);
    public static final RegistryObject<MapDecorationType> SHURIKENS_OF_ICE = register("shurikens_of_ice", true, -1, true, true);

    private static RegistryObject<MapDecorationType> register(String name, boolean showOnItemFrame, int mapColor, boolean explorationMapElement, boolean trackCount)
    {
        return MAP_DECORATION_TYPES.register(name, () -> new MapDecorationType(Minejago.modLoc(name), showOnItemFrame, mapColor, explorationMapElement, trackCount));
    }

    public static void init() {}
}
