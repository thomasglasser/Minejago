package dev.thomasglasser.minejago.client.renderer.entity.state;

import dev.thomasglasser.minejago.client.renderer.entity.layers.SnapshotTesterCosmeticOptions;

public interface MinejagoPlayerRenderState {
    boolean minejago$renderSnapshot();

    void minejago$setRenderSnapshot(boolean renderSnapshot);

    SnapshotTesterCosmeticOptions minejago$snapshotChoice();

    void minejago$setSnapshotChoice(SnapshotTesterCosmeticOptions snapshotChoice);

    boolean minejago$renderLegacyDev();

    void minejago$setRenderLegacyDev(boolean renderLegacyDev);

    boolean minejago$renderDev();

    void minejago$setRenderDev(boolean renderDev);

    boolean minejago$isSpinjitzuActive();

    void minejago$setSpinjitzuActive(boolean spinjitzuActive);

    int minejago$spinjitzuStartTicks();

    void minejago$setSpinjitzuStartTicks(int spinjitzuStartTicks);

    int minejago$spinjitzuColor();

    void minejago$setSpinjitzuColor(int spinjitzuColor);
}
