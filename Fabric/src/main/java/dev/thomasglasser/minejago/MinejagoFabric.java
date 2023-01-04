package dev.thomasglasser.minejago;

import net.fabricmc.api.ModInitializer;

public class MinejagoFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Minejago.init();
    }
}
