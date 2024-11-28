package dev.thomasglasser.minejago.packs;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.packs.PackInfo;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import java.util.List;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.KnownPack;

public class MinejagoPacks {
    public static final PackInfo IMMERSION = new PackInfo(new KnownPack(Minejago.MOD_ID, "immersion", TommyLibServices.PLATFORM.getModVersion(Minejago.MOD_ID)), PackType.CLIENT_RESOURCES, PackInfo.BUILT_IN_OPTIONAL);
    public static final PackInfo POTION_POT = new PackInfo(new KnownPack(Minejago.MOD_ID, "potion_pot", TommyLibServices.PLATFORM.getModVersion(Minejago.MOD_ID)), PackType.SERVER_DATA, PackInfo.FEATURE_NOT_EXPERIMENT);

    public static List<PackInfo> getPacks() {
        return List.of(
                IMMERSION,
                POTION_POT);
    }
}
