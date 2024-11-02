package dev.thomasglasser.minejago.mixin.minecraft.client.renderer.entity.state;

import dev.thomasglasser.minejago.client.renderer.entity.layers.SnapshotTesterCosmeticOptions;
import dev.thomasglasser.minejago.client.renderer.entity.state.MinejagoPlayerRenderState;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerRenderState.class)
public class PlayerRenderStateMixin implements MinejagoPlayerRenderState {
    @Unique
    private boolean minejago$renderSnapshot = false;
    @Unique
    private SnapshotTesterCosmeticOptions minejago$snapshotChoice = null;
    @Unique
    private boolean minejago$renderLegacyDev = false;
    @Unique
    private boolean minejago$renderDev = false;
    @Unique
    private boolean minejago$isSpinjitzuActive = false;
    @Unique
    private int minejago$spinjitzuStartTicks = 0;
    @Unique
    private int minejago$spinjitzuColor = 0;

    @Override
    public boolean minejago$renderSnapshot() {
        return this.minejago$renderSnapshot;
    }

    @Override
    public void minejago$setRenderSnapshot(boolean renderSnapshot) {
        this.minejago$renderSnapshot = renderSnapshot;
    }

    @Override
    public SnapshotTesterCosmeticOptions minejago$snapshotChoice() {
        return this.minejago$snapshotChoice;
    }

    @Override
    public void minejago$setSnapshotChoice(SnapshotTesterCosmeticOptions snapshotChoice) {
        this.minejago$snapshotChoice = snapshotChoice;
    }

    @Override
    public boolean minejago$renderLegacyDev() {
        return this.minejago$renderLegacyDev;
    }

    @Override
    public void minejago$setRenderLegacyDev(boolean renderLegacyDev) {
        this.minejago$renderLegacyDev = renderLegacyDev;
    }

    @Override
    public boolean minejago$renderDev() {
        return this.minejago$renderDev;
    }

    @Override
    public void minejago$setRenderDev(boolean renderDev) {
        this.minejago$renderDev = renderDev;
    }

    @Override
    public boolean minejago$isSpinjitzuActive() {
        return minejago$isSpinjitzuActive;
    }

    @Override
    public void minejago$setSpinjitzuActive(boolean spinjitzuActive) {
        this.minejago$isSpinjitzuActive = spinjitzuActive;
    }

    @Override
    public int minejago$spinjitzuStartTicks() {
        return minejago$spinjitzuStartTicks;
    }

    @Override
    public void minejago$setSpinjitzuStartTicks(int spinjitzuStartTicks) {
        this.minejago$spinjitzuStartTicks = spinjitzuStartTicks;
    }

    @Override
    public int minejago$spinjitzuColor() {
        return minejago$spinjitzuColor;
    }

    @Override
    public void minejago$setSpinjitzuColor(int spinjitzuColor) {
        this.minejago$spinjitzuColor = spinjitzuColor;
    }
}
