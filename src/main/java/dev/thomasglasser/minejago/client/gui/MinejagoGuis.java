package dev.thomasglasser.minejago.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.focus.FocusData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class MinejagoGuis {
    public static final ResourceLocation FOCUS_LEVEL = Minejago.modLoc("focus_level");

    private static final ResourceLocation EMPTY_FOCUS_LOCATION = Minejago.modLoc("hud/focus_empty");
    private static final ResourceLocation OUTER_HALF_FOCUS_LOCATION = Minejago.modLoc("hud/focus_half_outer");
    private static final ResourceLocation INNER_HALF_FOCUS_LOCATION = Minejago.modLoc("hud/focus_half_inner");
    private static final ResourceLocation OUTER_FULL_FOCUS_LOCATION = Minejago.modLoc("hud/focus_full_outer");
    private static final ResourceLocation INNER_FULL_FOCUS_LOCATION = Minejago.modLoc("hud/focus_full_inner");

    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.AIR_LEVEL, FOCUS_LEVEL, (guiGraphics, deltaTracker) -> renderFocusLevel(guiGraphics));
    }

    private static void renderFocusLevel(GuiGraphics guiGraphics) {
        if (Minecraft.getInstance().gameMode.canHurtPlayer()) {
            Gui gui = Minecraft.getInstance().gui;
            Player player = gui.getCameraPlayer();
            if (player != null) {
                if (gui.getVehicleMaxHearts(gui.getPlayerVehicleWithHealth()) == 0) {
                    Minecraft.getInstance().getProfiler().push("focus");
                    int startX = guiGraphics.guiWidth() / 2 + 91;
                    int startY = guiGraphics.guiHeight() - gui.rightHeight;
                    renderFocus(guiGraphics, player, startX, startY);
                    gui.rightHeight += 10;
                    Minecraft.getInstance().getProfiler().pop();
                }
            }
        }
    }

    private static void renderFocus(GuiGraphics guiGraphics, Player player, int startX, int y) {
        FocusData data = player.getData(MinejagoAttachmentTypes.FOCUS);
        int level = data.getFocusLevel();
        int color = player.level().holderOrThrow(player.getData(MinejagoAttachmentTypes.ELEMENT).element()).value().color().getValue();

        RenderSystem.enableBlend();

        for (int i = 0; i < 10; i++) {
            int x = startX - i * 8 - 9;
            guiGraphics.blitSprite(EMPTY_FOCUS_LOCATION, x, y, 9, 9);
            if (i * 2 + 1 < level) {
                renderEye(guiGraphics, x, y, OUTER_FULL_FOCUS_LOCATION, INNER_FULL_FOCUS_LOCATION, color);
            }

            if (i * 2 + 1 == level) {
                renderEye(guiGraphics, x, y, OUTER_HALF_FOCUS_LOCATION, INNER_HALF_FOCUS_LOCATION, color);
            }
        }

        RenderSystem.disableBlend();
    }

    private static void renderEye(GuiGraphics guiGraphics, int x, int y, ResourceLocation outer, ResourceLocation inner, int color) {
        guiGraphics.blitSprite(outer, x, y, 9, 9);
        if (inner != null) {
            guiGraphics.setColor(FastColor.ARGB32.red(color) / 255f, FastColor.ARGB32.green(color) / 255f, FastColor.ARGB32.blue(color) / 255f, 1);
            guiGraphics.blitSprite(inner, x, y, 9, 9);
            guiGraphics.setColor(1, 1, 1, 1);
        }
    }
}
