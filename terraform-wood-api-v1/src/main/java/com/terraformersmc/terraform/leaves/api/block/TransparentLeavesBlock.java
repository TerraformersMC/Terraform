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

	/**
	 * Default constructor with leaf particle chance of {@code 0.01}, for backward compatibility.
	 * <p/>
	 * NOTE: this class no longer supports non-tintable leaves.
	 *
	 * @param properties The block properties
	 */
	@Deprecated(since = "14.0.0")
	public TransparentLeavesBlock(BlockBehaviour.Properties properties) {
		super(0.01f, properties);
	}

	@Override
	public int getLightBlock(BlockState state) {
		return 0;
	}
}
