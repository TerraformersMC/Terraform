package com.terraformersmc.terraform.client.network.helper;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;

/**
 * A helper class containing static methods to interface with the GlStateManager.
 */
public class GlStateManagerHelper {
	/**
	 * Reads the current fog start value from OpenGL.
	 * @return a float value representing the current fog start.
	 */
	public static float getFogStart() {
		RenderSystem.assertThread(RenderSystem::isOnRenderThread);
		return GL11.glGetFloat(GL11.GL_FOG_START);
	}

	/**
	 * Reads the current fog end value from OpenGL.
	 * @return a float value representing the current fog end.
	 */
	public static float getFogEnd() {
		RenderSystem.assertThread(RenderSystem::isOnRenderThread);
		return GL11.glGetFloat(GL11.GL_FOG_END);
	}
}
