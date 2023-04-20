package com.terraformersmc.terraform.wood.block;

import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;

@SuppressWarnings("unused")
public class PillarLogHelper {
	/**
	 * Factory to create a PillarBlock log with default settings and
	 * the same map color on all block faces.
	 *
	 * @param color Map color for all faces of log
	 * @return New PillarBlock
	 */
	public static PillarBlock of(MapColor color) {
		return new PillarBlock(
				Block.Settings.of(
						Material.GENERIC,
						color
				).strength(2.0F).sounds(BlockSoundGroup.WOOD)
		);
	}

	/**
	 * Factory to create a PillarBlock log with default settings and
	 * different map colors on the top/bottom versus the sides.
	 *
	 * @param wood Map color for non-bark faces of log (ends)
	 * @param bark Map color for bark faces of log (sides)
	 * @return New PillarBlock
	 */
	public static PillarBlock of(MapColor wood, MapColor bark) {
		return new PillarBlock(
				Block.Settings.of(
						Material.GENERIC,
						(state) -> Direction.Axis.Y.equals(state.get(PillarBlock.AXIS)) ? wood : bark
				).strength(2.0F).sounds(BlockSoundGroup.WOOD)
		);
	}

	/**
	 * Factory to create a PillarBlock Nether stem with default settings and
	 * the same map color on all block faces.
	 *
	 * @param color Map color for all faces of stem
	 * @return New PillarBlock
	 */
	public static PillarBlock ofNether(MapColor color) {
		return new PillarBlock(
				Block.Settings.of(
						Material.GENERIC,
						color
				).strength(2.0F).sounds(BlockSoundGroup.NETHER_STEM)
		);
	}

	/**
	 * Factory to create a PillarBlock Nether stem with default settings and
	 * different map colors on the top/bottom versus the sides.
	 *
	 * @param wood Map color for non-bark faces of stem (ends)
	 * @param bark Map color for bark faces of stem (sides)
	 * @return New PillarBlock
	 */
	public static PillarBlock ofNether(MapColor wood, MapColor bark) {
		return new PillarBlock(
				Block.Settings.of(
						Material.GENERIC,
						(state) -> Direction.Axis.Y.equals(state.get(PillarBlock.AXIS)) ? wood : bark
				).strength(2.0F).sounds(BlockSoundGroup.NETHER_STEM)
		);
	}
}
