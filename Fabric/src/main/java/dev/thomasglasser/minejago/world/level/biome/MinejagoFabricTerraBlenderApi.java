package dev.thomasglasser.minejago.world.level.biome;

import dev.thomasglasser.minejago.Minejago;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class MinejagoFabricTerraBlenderApi implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new MinejagoRegion(Minejago.modLoc("overworld"), 1));

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Minejago.MOD_ID, MinejagoSurfaceRuleData.makeRules());
    }
}
