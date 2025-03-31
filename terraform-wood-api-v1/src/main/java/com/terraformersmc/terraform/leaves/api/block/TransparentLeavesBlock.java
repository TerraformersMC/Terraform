package com.terraformersmc.terraform.leaves.api.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.TintedParticleLeavesBlock;

/**
 * A tintable leaf block that does not block light.
 */
@SuppressWarnings("unused")
public class TransparentLeavesBlock extends TintedParticleLeavesBlock {
	/**
	 * @param leafParticleChance The relative frequency of falling leaf particles emitted by the block
	 * @param settings The block settings
	 */
	public TransparentLeavesBlock(float leafParticleChance, AbstractBlock.Settings settings) {
		super(leafParticleChance, settings);
	}

	/**
	 * Default constructor with leaf particle chance of {@code 0.01}, for backward compatibility.
	 *
	 * NOTE: this class no longer supports non-tintable leaves.
	 *
	 * @param settings The block settings
	 */
	@Deprecated(since = "14.0.0")
	public TransparentLeavesBlock(AbstractBlock.Settings settings) {
		super(0.01f, settings);
	}

	@Override
	public int getOpacity(BlockState state) {
		return 0;
	}
}
