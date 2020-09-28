package com.terraformersmc.terraform.mixin.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.terraformersmc.terraform.client.network.helper.GlStateManagerHelper;
import com.terraformersmc.terraform.mixinterface.FogDensityControlBiome;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * A mixin that enables biome control of fog density
 */
@Mixin(BackgroundRenderer.class)
public class MixinBackgroundRenderer {

	/**
	 * A mixin that overrides the default fog rendering functionality
	 * @param camera The camera that the fog is being rendered to
	 * @param fogType The fog type
	 * @param viewDistance The player configurable view distance
	 * @param thickFog Whether or not the biome fog is thick or not
	 * @param callback The callback for the mixin
	 */
	@Inject(method = "Lnet/minecraft/client/render/BackgroundRenderer;applyFog(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/BackgroundRenderer$FogType;FZ)V", at = @At("HEAD"), cancellable = true)
	private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, CallbackInfo callback) {

		// Access some topical fields for easier use later
		BlockPos pos = camera.getBlockPos();
		FluidState fluidState = camera.getSubmergedFluidState();
		Entity entity = camera.getFocusedEntity();
		World world = entity.getEntityWorld();
		Biome biome = world.getBiome(pos);

		// If the entity is not inside of a fluid
		if (fluidState.getFluid() == Fluids.EMPTY) {
			// If the entity doesn't have the blindness effect
			if (!(entity instanceof LivingEntity && ((LivingEntity) entity).hasStatusEffect(StatusEffects.BLINDNESS))) {
				// At this point the only situation where fog is drawn is sky and biome fog

				float newStart, newEnd;

				// This pattern is cloned from the original method to ensure the same values
				if (thickFog) {
					newStart = viewDistance * 0.05F;
					newEnd = Math.min(viewDistance, 192.0F) * 0.5F;
				} else if (fogType == BackgroundRenderer.FogType.FOG_SKY) {
					newStart = 0.0F;
					newEnd = viewDistance;
				} else {
					newStart = viewDistance * 0.75F;
					newEnd = viewDistance;
				}

				// Use the biome multiplier if applicable
//				if (biome instanceof FogDensityControlBiome) {
//					newStart = newStart * ((FogDensityControlBiome) biome).fogMultiplier();
//					newEnd = newEnd * ((FogDensityControlBiome) biome).fogMultiplier();
//				}

				// Read the current values from OpenGL
				float oldStart = GlStateManagerHelper.getFogStart();
				float oldEnd = GlStateManagerHelper.getFogEnd();

				// Set the new value to the old value plus a marginal delta of the difference between the current and desired fog values
				RenderSystem.fogStart(oldStart + (newStart - oldStart) * 0.025f);
				RenderSystem.fogEnd(oldEnd + (newEnd - oldEnd) * 0.025f);

				RenderSystem.fogMode(GlStateManager.FogMode.LINEAR);
				RenderSystem.setupNvFogDistance();

				callback.cancel();
			}
		}
	}
}
