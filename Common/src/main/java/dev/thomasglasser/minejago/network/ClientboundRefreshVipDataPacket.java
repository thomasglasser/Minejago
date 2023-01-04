package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.util.MinejagoClientUtils;

public record ClientboundRefreshVipDataPacket() {
    // ON CLIENT
    public void handle() {
        MinejagoClientUtils.refreshVip();
    }
}
