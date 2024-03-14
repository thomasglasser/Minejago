package dev.thomasglasser.minejago.packs;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.packs.PackInfo;
import net.minecraft.server.packs.PackType;

import java.util.List;

public class MinejagoPacks
{
    public static final PackInfo IMMERSION = new PackInfo(Minejago.modLoc("immersion"), false, PackType.CLIENT_RESOURCES);
    public static final PackInfo POTION_POT = new PackInfo(Minejago.modLoc("potion_pot"), false, PackType.SERVER_DATA);

    public static List<PackInfo> getPacks()
    {
        return List.of(
                IMMERSION,
                POTION_POT
        );
    }
}
