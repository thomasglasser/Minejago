package dev.thomasglasser.minejago.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.item.LegendScrollItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class LegendScrollScreen extends Screen
{
    private final ResourceLocation BACKGROUND = new ResourceLocation(Minejago.MODID, "textures/gui/legend_scroll/legend_scroll.png");
    private final int BACKGROUND_WIDTH = 300;
    private final int BACKGROUND_HEIGHT = 220;

    private double frame = 1;
    private LegendScrollItem.Legends legend;
    private ResourceLocation legendLoc;
    private final int LEGEND_WIDTH = 192;
    private final int LEGEND_HEIGHT = 108;

    public LegendScrollScreen(Component pTitle, LegendScrollItem.Legends legend) {
        super(pTitle);
        this.legend = legend;
        legendLoc = new ResourceLocation(Minejago.MODID, "textures/gui/legend_scroll/" + legend.getLegend() + "/" + String.format("%05d", (int)frame) + ".png");
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        this.setFocused((GuiEventListener)null);
        this.renderScroll(pPoseStack);
        this.renderLegend(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void renderScroll(PoseStack poseStack)
    {
        int i = (this.width - BACKGROUND_WIDTH) / 2;
        int j = (this.height - BACKGROUND_HEIGHT) / 2;
        RenderSystem.setShaderTexture(0, BACKGROUND);
        blit(poseStack, i, j, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
    }

    public void renderLegend(PoseStack poseStack)
    {
        int i = (this.width - LEGEND_WIDTH) / 2;
        int j = (this.height - LEGEND_HEIGHT) / 2;
        RenderSystem.setShaderTexture(0, legendLoc);
        blit(poseStack, i, j, LEGEND_WIDTH, LEGEND_HEIGHT, 0, 0, LEGEND_WIDTH, LEGEND_HEIGHT, LEGEND_WIDTH, LEGEND_HEIGHT);

    }

    @Override
    public void tick() {
        frame+=1.5;
        legendLoc = new ResourceLocation(Minejago.MODID, "textures/gui/legend_scroll/" + legend.getLegend() + "/" + String.format("%05d", (int)frame) + ".png");
        if (frame > 1866)
        {
            this.getMinecraft().setScreen(null);
        }
    }

    @Override
    public void onClose() {
        this.getMinecraft().getSoundManager().stop(new ResourceLocation(Minejago.MODID, legend.getLegend() + "_legend_scroll"), this.getMinecraft().player.getSoundSource());
        super.onClose();
    }
}
