package dev.thomasglasser.minejago.client.gui;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.focus.FocusConfig;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.focus.FocusDataHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class Guis
{
	private static final ResourceLocation EMPTY_FOCUS_LOCATION = Minejago.modLoc("textures/gui/focus_empty.png");
	private static final ResourceLocation OUTER_HALF_FOCUS_LOCATION = Minejago.modLoc("textures/gui/focus_half_outer.png");
	private static final ResourceLocation INNER_HALF_FOCUS_LOCATION = Minejago.modLoc("textures/gui/focus_half_inner.png");
	private static final ResourceLocation OUTER_FULL_FOCUS_LOCATION = Minejago.modLoc("textures/gui/focus_full_outer.png");
	private static final ResourceLocation INNER_FULL_FOCUS_LOCATION = Minejago.modLoc("textures/gui/focus_full_inner.png");

	public static void renderFocusBar(GuiGraphics guiGraphics, float partialTick)
	{
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.gameMode != null && minecraft.gameMode.canHurtPlayer()) {
			Gui gui = minecraft.gui;
			Player player = MinejagoClientUtils.getMainClientPlayer();
			LivingEntity livingEntity = gui.getPlayerVehicleWithHealth();
			int x = gui.getVehicleMaxHearts(livingEntity);
			if (x == 0) {
				minecraft.getProfiler().popPush("focus");

				FocusData focusData = ((FocusDataHolder)player).getFocusData();
				int k = focusData.getFocusLevel();
				int n = guiGraphics.guiWidth() / 2 + 91;
				int o = guiGraphics.guiHeight() - 49;

				for(int y = 0; y < 10; ++y) {
					int z = o;

					if (focusData.getSaturationLevel() <= 0.0F && partialTick % (k * 3 + 1) == 0) {
						z = o + (RandomSource.create().nextInt(3) - 1);
					}

					int color = MinejagoPowers.getPowerOrThrow(minecraft.level.registryAccess(), Services.DATA.getPowerData(player).power()).getColor().getValue();
					
					int ac = n - y * 8 - 9;

					int xOff = FocusConfig.X_OFFSET.get();
					int yOff = FocusConfig.Y_OFFSET.get();

					ac += xOff;
					z -= yOff;

					if ((Math.abs(xOff) < 80 && Math.abs(yOff) < 10) && (player.isEyeInFluid(FluidTags.WATER) || Math.min(player.getAirSupply(), player.getMaxAirSupply()) < player.getMaxAirSupply()))
						z -= 10;

					guiGraphics.blit(EMPTY_FOCUS_LOCATION, ac, z, 0, 0, 9, 9, 9, 9);
					if (y * 2 + 1 < k) {
						renderEyes(guiGraphics, ac, z, OUTER_FULL_FOCUS_LOCATION, INNER_FULL_FOCUS_LOCATION, color);
					}

					if (y * 2 + 1 == k) {
						renderEyes(guiGraphics, ac, z, OUTER_HALF_FOCUS_LOCATION, INNER_HALF_FOCUS_LOCATION, color);
					}
				}
			}

			minecraft.getProfiler().pop();
		}
	}

	private static void renderEyes(GuiGraphics guiGraphics, int x, int y, ResourceLocation outer, ResourceLocation inner, int color)
	{
		guiGraphics.innerBlit(inner, x, x + 9, y, y + 9, 0, 0, 1, 0, 1, FastColor.ARGB32.red(color) / 255f, FastColor.ARGB32.green(color) / 255f, FastColor.ARGB32.blue(color) / 255f, 1);
		guiGraphics.blit(outer, x, y, 0, 0, 9, 9, 9, 9);
	}
}
