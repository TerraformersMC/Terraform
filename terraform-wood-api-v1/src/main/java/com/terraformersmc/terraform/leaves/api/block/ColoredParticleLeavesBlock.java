package com.terraformersmc.terraform.leaves.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingParticlesLeavesBlock;
import net.minecraft.world.level.block.sounds.AmbientLeavesBlockSoundPlayer;

/**
 * This leaves block provides an easy way to implement non-tinted colored leaves with non-tinted colored particles.
 * (F.e. like vanilla Cherry leaves.)
 * <p/>
 * The {@code leaf_particle_color} field accepts an RGB block color which will be applied to the particles.
 */
@SuppressWarnings("unused")
public class ColoredParticleLeavesBlock extends FallingParticlesLeavesBlock {
	private final int leafParticleColor;

	/**
	 * Instantiate or extend this class as desired to create a non-tinted leaves block with colored particles.
	 *
	 * @param leafParticleChance The relative frequency of falling leaf particles emitted by the block
	 * @param blockColor The RGB color of falling leaf particles emitted by the block
	 * @param ambientLeavesBlockSoundPlayer The desired ambient leaves block sound player
	 * @param properties The block properties
	 */
	public ColoredParticleLeavesBlock(float leafParticleChance, int blockColor, AmbientLeavesBlockSoundPlayer ambientLeavesBlockSoundPlayer, Properties properties) {
		super(leafParticleChance, ambientLeavesBlockSoundPlayer, properties);

		this.leafParticleColor = blockColor;
	}

	@Override
	protected void spawnFallingLeavesParticle(Level level, BlockPos pos, RandomSource random) {
		ColorParticleOption effect = ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, leafParticleColor);
		ParticleUtils.spawnParticleBelow(level, pos, random, effect);
	}
}
