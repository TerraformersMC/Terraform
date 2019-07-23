package com.terraformersmc.terraform.surface;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class CliffSurfaceConfig extends TernarySurfaceConfig {
	private final BlockState cliffMaterial;

	public CliffSurfaceConfig(BlockState top, BlockState under, BlockState underwater, BlockState cliff) {
		super(top, under, underwater);

		this.cliffMaterial = cliff;
	}

	public BlockState getCliffMaterial() {
		return this.cliffMaterial;
	}

	public static CliffSurfaceConfig deserialize(Dynamic<?> dynamic) {
		TernarySurfaceConfig ternary = TernarySurfaceConfig.deserialize(dynamic);

		BlockState cliff = dynamic.get("cliff_material").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());

		return new CliffSurfaceConfig(ternary.getTopMaterial(), ternary.getUnderMaterial(), ternary.getUnderwaterMaterial(), cliff);
	}
}
