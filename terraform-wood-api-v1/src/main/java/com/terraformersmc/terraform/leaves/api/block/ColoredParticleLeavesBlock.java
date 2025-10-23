package com.terraformersmc.terraform.leaves.api.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.LeavesBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.particle.TintedParticleEffect;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/**
 * This leaves block provides an easy way to implement non-tinted colored leaves with non-tinted colored particles.
 * (F.e. like vanilla Cherry leaves.)
 *
 * The {@code leaf_particle_color} field accepts an RGB block color which will be applied to the particles.
 */
@SuppressWarnings("unused")
public class ColoredParticleLeavesBlock extends LeavesBlock {
	private final int leafParticleColor;

	public static final MapCodec<ColoredParticleLeavesBlock> CODEC = RecordCodecBuilder.mapCodec(
			(instance) -> instance.group(
							Codecs.rangedInclusiveFloat(0.0f, 1.0f).fieldOf("leaf_particle_chance")
									.forGetter(arg -> arg.leafParticleChance),
							Codecs.RGB.fieldOf("leaf_particle_color")
									.forGetter(arg -> arg.leafParticleColor),
							ColoredParticleLeavesBlock.createSettingsCodec()
					)
					.apply(instance, ColoredParticleLeavesBlock::new));

	/**
	 * Instantiate or extend this class as desired to create a non-tinted leaves block with colored particles.
	 *
	 * @param leafParticleChance The relative frequency of falling leaf particles emitted by the block
	 * @param blockColor The RGB color of falling leaf particles emitted by the block
	 * @param settings The block settings
	 */
	public ColoredParticleLeavesBlock(float leafParticleChance, int blockColor, Settings settings) {
		super(leafParticleChance, settings);

		this.leafParticleColor = blockColor;
	}

	@Override
	protected void spawnLeafParticle(World world, BlockPos pos, Random random) {
		TintedParticleEffect effect = TintedParticleEffect.create(ParticleTypes.TINTED_LEAVES, leafParticleColor);
		ParticleUtil.spawnParticle(world, pos, random, effect);
	}

	public MapCodec<? extends ColoredParticleLeavesBlock> getCodec() {
		return CODEC;
	}
}
