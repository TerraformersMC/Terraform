package com.terraformersmc.terraform.wood.api.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

@SuppressWarnings("unused")
public final class PillarLogHelper {
	private PillarLogHelper() {
		return;
	}

	/**
	 * Factory to create default block settings for a PillarBlock, QuarterLogBlock,
	 * or BareSmallLogBlock log with the same map color on all block faces.
	 *
	 * @param color Map color for all faces of log
	 * @return New AbstractBlock.Settings
	 */
	public static BlockBehaviour.Properties createSettings(MapColor color) {
		return BlockBehaviour.Properties.of()
				.mapColor(color)
				.strength(2.0F)
				.sound(SoundType.WOOD)
				.ignitedByLava();
	}

	/**
	 * Factory to create default block settings for a PillarBlock or BareSmallLogBlock
	 * log with different map colors on the top/bottom versus the sides.
	 *
	 * @param wood Map color for non-bark faces of log (ends)
	 * @param bark Map color for bark faces of log (sides)
	 * @return New AbstractBlock.Settings
	 */
	public static BlockBehaviour.Properties createSettings(MapColor wood, MapColor bark) {
		return BlockBehaviour.Properties.of()
				.mapColor((state) -> Direction.Axis.Y.equals(state.getValue(RotatedPillarBlock.AXIS)) ? wood : bark)
				.strength(2.0F)
				.sound(SoundType.WOOD)
				.ignitedByLava();
	}

	/**
	 * Factory to create default block settings for a QuarterLogBlock
	 * log with different map colors on the bark versus the cut faces.
	 *
	 * @param wood Map color for cut faces of log
	 * @param bark Map color for bark faces of log
	 * @return New AbstractBlock.Settings
	 */
	public static BlockBehaviour.Properties createQuarterLogSettings(MapColor wood, MapColor bark) {
		return BlockBehaviour.Properties.of()
				.mapColor(
						(state) ->
								switch (state.getValue(RotatedPillarBlock.AXIS)) {
									case Y -> wood;
									case X ->
											switch (state.getValue(QuarterLogBlock.BARK_SIDE)) {
												case NORTHWEST, SOUTHWEST -> bark;
												case NORTHEAST, SOUTHEAST -> wood;
											};
									case Z ->
											switch (state.getValue(QuarterLogBlock.BARK_SIDE)) {
												case SOUTHEAST, SOUTHWEST -> bark;
												case NORTHEAST, NORTHWEST -> wood;
											};
								}
				)
				.strength(2.0F)
				.sound(SoundType.WOOD)
				.ignitedByLava();
	}

	/**
	 * Factory to create default block settings for a SmallLogBlock
	 * log with the same map color on all block faces.
	 *
	 * If there is no associated LeavesBlock, instead use
	 * {@linkplain PillarLogHelper#createSettings(MapColor)}
	 *
	 * @param leaves Associated LeavesBlock
	 * @param color Map color for all faces of log
	 * @return New AbstractBlock.Settings
	 */
	public static BlockBehaviour.Properties createSmallLogSettings(Block leaves, MapColor color) {
		return BlockBehaviour.Properties.of()
				.mapColor((state) -> state.getValue(SmallLogBlock.HAS_LEAVES) ? leaves.defaultMapColor() : color)
				.strength(2.0F)
				.sound(SoundType.WOOD)
				.ignitedByLava();
	}

	/**
	 * Factory to create default block settings for a SmallLogBlock
	 * log with different map colors on the top/bottom versus the sides.
	 *
	 * If there is no associated LeavesBlock, instead use
	 * {@linkplain PillarLogHelper#createSettings(MapColor, MapColor)}
	 *
	 * @param leaves Associated LeavesBlock
	 * @param wood Map color for non-bark faces of log (ends)
	 * @param bark Map color for bark faces of log (sides)
	 * @return New AbstractBlock.Settings
	 */
	public static BlockBehaviour.Properties createSmallLogSettings(Block leaves, MapColor wood, MapColor bark) {
		return BlockBehaviour.Properties.of()
				.mapColor((state) -> state.getValue(SmallLogBlock.HAS_LEAVES) ? leaves.defaultMapColor() : state.getValue(SmallLogBlock.UP) ? wood : bark)
				.strength(2.0F)
				.sound(SoundType.WOOD)
				.ignitedByLava();
	}

	/**
	 * Factory to create default block settings for a PillarBlock Nether stem with
	 * the same map color on all block faces.
	 *
	 * @param color Map color for all faces of stem
	 * @return New AbstractBlock.Settings
	 */
	public static BlockBehaviour.Properties createNetherSettings(MapColor color) {
		return BlockBehaviour.Properties.of()
				.mapColor(color)
				.strength(2.0F)
				.sound(SoundType.STEM);
	}

	/**
	 * Factory to create default block settings for a PillarBlock Nether stem with
	 * different map colors on the top/bottom versus the sides.
	 *
	 * @param wood Map color for non-bark faces of stem (ends)
	 * @param bark Map color for bark faces of stem (sides)
	 * @return New AbstractBlock.Settings
	 */
	public static BlockBehaviour.Properties createNetherSettings(MapColor wood, MapColor bark) {
		return BlockBehaviour.Properties.of()
				.mapColor((state) -> Direction.Axis.Y.equals(state.getValue(RotatedPillarBlock.AXIS)) ? wood : bark)
				.strength(2.0F)
				.sound(SoundType.STEM);
	}

	/**
	 * Factory to create default block settings for a QuarterLogBlock Nether
	 * stem with different map colors on the bark versus the cut faces.
	 *
	 * @param wood Map color for cut faces of log
	 * @param bark Map color for bark faces of log
	 * @return New AbstractBlock.Settings
	 */
	public static BlockBehaviour.Properties createQuarterLogNetherSettings(MapColor wood, MapColor bark) {
		return BlockBehaviour.Properties.of()
				.mapColor(
						(state) ->
								switch (state.getValue(RotatedPillarBlock.AXIS)) {
									case Y -> wood;
									case X ->
											switch (state.getValue(QuarterLogBlock.BARK_SIDE)) {
												case NORTHWEST, SOUTHWEST -> bark;
												case NORTHEAST, SOUTHEAST -> wood;
											};
									case Z ->
											switch (state.getValue(QuarterLogBlock.BARK_SIDE)) {
												case SOUTHEAST, SOUTHWEST -> bark;
												case NORTHEAST, NORTHWEST -> wood;
											};
								}
				)
				.strength(2.0F)
				.sound(SoundType.STEM);
	}
}
