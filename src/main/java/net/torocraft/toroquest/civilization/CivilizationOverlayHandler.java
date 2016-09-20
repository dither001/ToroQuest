package net.torocraft.toroquest.civilization;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.torocraft.toroquest.util.ToroUtils;

public class CivilizationOverlayHandler {

	private final Random random = new Random();
	private final Minecraft minecraft = Minecraft.getMinecraft();

	@SubscribeEvent
	public void onPostRenderOverlay(RenderGameOverlayEvent.Pre event) {
		ScaledResolution resolution = event.getResolution();
		int width = resolution.getScaledWidth();
		int height = resolution.getScaledHeight();
		EntityPlayerSP player = minecraft.thePlayer;
		Province civ = CivilizationUtil.getPlayerCurrentProvince(player);

		if (event.getType() == ElementType.HEALTH) {
			//			if (civ != null)
			drawCivilizationOverlay(width, height, civ);
		}
	}

	private void drawCivilizationOverlay(int width, int height, Province civ) {
		int left = width / 2 - 8;
		int top = height - 48;

		ToroUtils.drawOverlayIcon(left, top + 4, 0, 1);
		if (civ == null) {
			ToroUtils.drawOverlayIcon(left, top, 0, 0);
			return;
		}

		if (civ.civilization.toString().equals(CivilizationType.EARTH)) {
			ToroUtils.drawOverlayIcon(left, top, 0, 0);
		} else if (civ.civilization.toString().equals(CivilizationType.WIND)) {
			ToroUtils.drawOverlayIcon(left, top, 1, 0);
		} else if (civ.civilization.toString().equals(CivilizationType.FIRE)) {
			ToroUtils.drawOverlayIcon(left, top, 2, 0);
		} else if (civ.civilization.toString().equals(CivilizationType.MOON)) {
			ToroUtils.drawOverlayIcon(left, top, 3, 0);
		} else if (civ.civilization.toString().equals(CivilizationType.SUN)) {
			ToroUtils.drawOverlayIcon(left, top, 4, 0);
		}
	}

}