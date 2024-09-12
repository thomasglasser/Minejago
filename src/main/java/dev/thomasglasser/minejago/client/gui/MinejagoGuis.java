package dev.thomasglasser.minejago.client.gui;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoClientConfig;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class MinejagoGuis {
    private static final ResourceLocation EMPTY_FOCUS_LOCATION = Minejago.modLoc("textures/gui/focus_empty.png");
    private static final ResourceLocation OUTER_HALF_FOCUS_LOCATION = Minejago.modLoc("textures/gui/focus_half_outer.png");
    private static final ResourceLocation INNER_HALF_FOCUS_LOCATION = Minejago.modLoc("textures/gui/focus_half_inner.png");
    private static final ResourceLocation OUTER_FULL_FOCUS_LOCATION = Minejago.modLoc("textures/gui/focus_full_outer.png");
    private static final ResourceLocation INNER_FULL_FOCUS_LOCATION = Minejago.modLoc("textures/gui/focus_full_inner.png");
    private static final ResourceLocation HALF_MEGA_FOCUS_LOCATION = Minejago.modLoc("textures/gui/mega_focus_half.png");
    private static final ResourceLocation OUTER_FULL_MEGA_FOCUS_LOCATION = Minejago.modLoc("textures/gui/mega_focus_full_outer.png");
    private static final ResourceLocation INNER_FULL_MEGA_FOCUS_LOCATION = Minejago.modLoc("textures/gui/mega_focus_full_inner.png");

    public static void renderFocusBar(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.gameMode != null && minecraft.gameMode.canHurtPlayer() && !minecraft.options.hideGui) {
            Gui gui = minecraft.gui;
            Player player = ClientUtils.getMainClientPlayer();
            LivingEntity livingEntity = gui.getPlayerVehicleWithHealth();
            int vehicleMaxHearts = gui.getVehicleMaxHearts(livingEntity);
            if (vehicleMaxHearts == 0) {
                minecraft.getProfiler().popPush("focus");

                FocusData focusData = player.getData(MinejagoAttachmentTypes.FOCUS);
                int focusLevel = focusData.getFocusLevel();
                int startX = guiGraphics.guiWidth() / 2 + 91;
                int startY = guiGraphics.guiHeight() - 49;

                for (int i = 0; i < 10; ++i) {
                    int y = startY;

                    if (focusData.getSaturationLevel() <= 0.0F && gui.getGuiTicks() % (focusLevel * 3 + 1) == 0) {
                        y = startY + (minecraft.level.random.nextInt(3) - 1);
                    }

                    int color = minecraft.level.holderOrThrow(player.getData(MinejagoAttachmentTypes.POWER).power()).value().getColor().getValue();

                    int x = startX - i * 8 - 9;

                    int xOff = MinejagoClientConfig.get().xOffset.get();
                    int yOff = MinejagoClientConfig.get().yOffset.get();

                    x += xOff;
                    y -= yOff;

                    if ((Math.abs(xOff) < 80 && Math.abs(yOff) < 10) && (player.isEyeInFluid(FluidTags.WATER) || Math.min(player.getAirSupply(), player.getMaxAirSupply()) < player.getMaxAirSupply()))
                        y -= 10;

                    guiGraphics.blit(EMPTY_FOCUS_LOCATION, x, y, 0, 0, 9, 9, 9, 9);
                    if (i * 2 + 1 < focusLevel)
                        renderEyes(guiGraphics, x, y, OUTER_FULL_FOCUS_LOCATION, INNER_FULL_FOCUS_LOCATION, color);
                    if (i * 2 + 21 < focusLevel)
                        renderEyes(guiGraphics, x, y, OUTER_FULL_MEGA_FOCUS_LOCATION, INNER_FULL_MEGA_FOCUS_LOCATION, color);

                    if (i * 2 + 1 == focusLevel) {
                        renderEyes(guiGraphics, x, y, OUTER_HALF_FOCUS_LOCATION, INNER_HALF_FOCUS_LOCATION, color);
                    }
                    if (i * 2 + 21 == focusLevel)
                        renderEyes(guiGraphics, x, y, HALF_MEGA_FOCUS_LOCATION, null, color);
                }
            }

            minecraft.getProfiler().pop();
        }
    }

    private static void renderEyes(GuiGraphics guiGraphics, int x, int y, ResourceLocation outer, @Nullable ResourceLocation inner, int color) {
        if (inner != null) guiGraphics.innerBlit(inner, x, x + 9, y, y + 9, 0, 0, 1, 0, 1, FastColor.ARGB32.red(color) / 255f, FastColor.ARGB32.green(color) / 255f, FastColor.ARGB32.blue(color) / 255f, 1);
        guiGraphics.blit(outer, x, y, 0, 0, 9, 9, 9, 9);
    }
}
