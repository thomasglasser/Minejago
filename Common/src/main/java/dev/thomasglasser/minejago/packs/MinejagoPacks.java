package dev.thomasglasser.minejago.packs;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.packs.PackHolder;
import net.minecraft.server.packs.PackType;

import java.util.List;

public class MinejagoPacks
{
    public static final PackHolder IMMERSION = new PackHolder(Minejago.modLoc("immersion"), false, PackType.CLIENT_RESOURCES);
    public static final PackHolder POTION_POT = new PackHolder(Minejago.modLoc("potion_pot"), false, PackType.SERVER_DATA);

    public static List<PackHolder> getPacks()
    {
        return List.of(
                IMMERSION,
                POTION_POT
        );
    }
}
