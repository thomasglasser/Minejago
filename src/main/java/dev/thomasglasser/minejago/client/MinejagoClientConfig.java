package dev.thomasglasser.minejago.client;

import dev.thomasglasser.minejago.client.renderer.entity.layers.BetaTesterCosmeticOptions;
import net.neoforged.neoforge.common.ModConfigSpec;

public class MinejagoClientConfig {
    private static final MinejagoClientConfig INSTANCE = new MinejagoClientConfig();

    public final ModConfigSpec configSpec;

    // Cosmetics
    public static final String COSMETICS = "cosmetics";
    public final ModConfigSpec.BooleanValue displayBetaTesterCosmetic;
    public final ModConfigSpec.EnumValue<BetaTesterCosmeticOptions> betaTesterCosmeticChoice;
    public final ModConfigSpec.BooleanValue displayDevTeamCosmetic;
    public final ModConfigSpec.BooleanValue displayLegacyDevTeamCosmetic;

    public MinejagoClientConfig() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push(COSMETICS);
        displayBetaTesterCosmetic = builder
                .define("display_beta_tester_cosmetic", true);
        betaTesterCosmeticChoice = builder
                .defineEnum("beta_tester_cosmetic_choice", BetaTesterCosmeticOptions.BAMBOO_HAT);
        displayDevTeamCosmetic = builder
                .define("display_dev_team_cosmetic", true);
        displayLegacyDevTeamCosmetic = builder
                .define("display_legacy_dev_team_cosmetic", true);
        builder.pop();

        configSpec = builder.build();
    }

    public ModConfigSpec getConfigSpec() {
        return configSpec;
    }

    public static MinejagoClientConfig get() {
        return INSTANCE;
    }
}
