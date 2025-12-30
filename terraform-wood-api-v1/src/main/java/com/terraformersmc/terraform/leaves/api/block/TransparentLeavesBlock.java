package com.terraformersmc.terraform.leaves.api.block;

import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A tintable leaf block that does not block light.
 */
@SuppressWarnings("unused")
public class TransparentLeavesBlock extends TintedParticleLeavesBlock {
	/**
	 * @param leafParticleChance The relative frequency of falling leaf particles emitted by the block
	 * @param properties The block properties
	 */
	public TransparentLeavesBlock(float leafParticleChance, BlockBehaviour.Properties properties) {
		super(leafParticleChance, properties);
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 0;
	}
}
