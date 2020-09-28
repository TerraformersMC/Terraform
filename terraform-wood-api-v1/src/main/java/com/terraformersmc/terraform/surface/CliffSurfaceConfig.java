package com.terraformersmc.terraform.surface;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class CliffSurfaceConfig extends TernarySurfaceConfig {
	public static final Codec<CliffSurfaceConfig> CODEC = RecordCodecBuilder.create((instance) ->
			instance.group(
					BlockState.CODEC.fieldOf("top_material").forGetter(CliffSurfaceConfig::getTopMaterial),
					BlockState.CODEC.fieldOf("under_material").forGetter(CliffSurfaceConfig::getUnderMaterial),
					BlockState.CODEC.fieldOf("underwater_material").forGetter(CliffSurfaceConfig::getUnderwaterMaterial),
					BlockState.CODEC.fieldOf("cliff_material").forGetter(CliffSurfaceConfig::getCliffMaterial)).apply(instance, CliffSurfaceConfig::new));
	private final BlockState cliffMaterial;

	public CliffSurfaceConfig(BlockState top, BlockState under, BlockState underwater, BlockState cliff) {
		super(top, under, underwater);

		this.cliffMaterial = cliff;
	}

	public BlockState getCliffMaterial() {
		return this.cliffMaterial;
	}
}
