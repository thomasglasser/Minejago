package dev.thomasglasser.minejago.packs;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.server.packs.PackType;

import java.util.List;

public class MinejagoPacks
{
    public static final PackHolder IMMERSION = new PackHolder(Minejago.modLoc("minejago_immersion_pack"), "resource_pack.minejago_immersion_pack.name", false, PackType.CLIENT_RESOURCES);
    public static final PackHolder POTION_POT = new PackHolder(Minejago.modLoc("minejago_potion_pot_pack"), "resource_pack.minejago_potion_pot_pack.name", false, PackType.SERVER_DATA);

    public static List<PackHolder> getPacks()
    {
        return List.of(
                IMMERSION,
                POTION_POT
        );
    }
}
