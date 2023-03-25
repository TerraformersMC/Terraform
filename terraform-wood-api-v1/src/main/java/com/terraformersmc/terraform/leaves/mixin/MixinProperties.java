package com.terraformersmc.terraform.leaves.mixin;

import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Properties.class)
public class MixinProperties {
	/*
	 * Inject our larger "distance" property [1,14] to replace vanilla's [1,7].
	 * This allows leaves to extend up to 13 blocks from a log instead of 6.
	 *
	 * (The logic implementing the limit is based on the hard-coded MAX_DISTANCE
	 * constant in LeavesBlock, so vanilla leaf behavior is unchanged by this.)
	 */
	@Shadow
	@Final
	@Mutable
	@SuppressWarnings("unused")
	public static IntProperty DISTANCE_1_7 = IntProperty.of("distance", 1, 14);
}
