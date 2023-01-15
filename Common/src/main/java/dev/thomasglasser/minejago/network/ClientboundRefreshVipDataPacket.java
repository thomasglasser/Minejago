package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import net.minecraft.resources.ResourceLocation;

public class ClientboundRefreshVipDataPacket {
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_refresh_vip_data");

    // ON CLIENT
    public void handle() {
        MinejagoClientUtils.refreshVip();
    }
}
