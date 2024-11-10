package com.terraformersmc.terraform.wood.api.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;

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
	public static AbstractBlock.Settings createSettings(MapColor color) {
		return AbstractBlock.Settings.create()
				.mapColor(color)
				.strength(2.0F)
				.sounds(BlockSoundGroup.WOOD)
				.burnable();
	}

	/**
	 * Factory to create default block settings for a PillarBlock or BareSmallLogBlock
	 * log with different map colors on the top/bottom versus the sides.
	 *
	 * @param wood Map color for non-bark faces of log (ends)
	 * @param bark Map color for bark faces of log (sides)
	 * @return New AbstractBlock.Settings
	 */
	public static AbstractBlock.Settings createSettings(MapColor wood, MapColor bark) {
		return AbstractBlock.Settings.create()
				.mapColor((state) -> Direction.Axis.Y.equals(state.get(PillarBlock.AXIS)) ? wood : bark)
				.strength(2.0F)
				.sounds(BlockSoundGroup.WOOD)
				.burnable();
	}

	/**
	 * Factory to create default block settings for a QuarterLogBlock
	 * log with different map colors on the bark versus the cut faces.
	 *
	 * @param wood Map color for cut faces of log
	 * @param bark Map color for bark faces of log
	 * @return New AbstractBlock.Settings
	 */
	public static AbstractBlock.Settings createQuarterLogSettings(MapColor wood, MapColor bark) {
		return AbstractBlock.Settings.create()
				.mapColor(
						(state) ->
								switch (state.get(PillarBlock.AXIS)) {
									case Y -> wood;
									case X ->
											switch (state.get(QuarterLogBlock.BARK_SIDE)) {
												case NORTHWEST, SOUTHWEST -> bark;
												case NORTHEAST, SOUTHEAST -> wood;
											};
									case Z ->
											switch (state.get(QuarterLogBlock.BARK_SIDE)) {
												case SOUTHEAST, SOUTHWEST -> bark;
												case NORTHEAST, NORTHWEST -> wood;
											};
								}
				)
				.strength(2.0F)
				.sounds(BlockSoundGroup.WOOD)
				.burnable();
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
	public static AbstractBlock.Settings createSmallLogSettings(Block leaves, MapColor color) {
		return AbstractBlock.Settings.create()
				.mapColor((state) -> state.get(SmallLogBlock.HAS_LEAVES) ? leaves.getDefaultMapColor() : color)
				.strength(2.0F)
				.sounds(BlockSoundGroup.WOOD)
				.burnable();
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
	public static AbstractBlock.Settings createSmallLogSettings(Block leaves, MapColor wood, MapColor bark) {
		return AbstractBlock.Settings.create()
				.mapColor((state) -> state.get(SmallLogBlock.HAS_LEAVES) ? leaves.getDefaultMapColor() : state.get(SmallLogBlock.UP) ? wood : bark)
				.strength(2.0F)
				.sounds(BlockSoundGroup.WOOD)
				.burnable();
	}

	/**
	 * Factory to create default block settings for a PillarBlock Nether stem with
	 * the same map color on all block faces.
	 *
	 * @param color Map color for all faces of stem
	 * @return New AbstractBlock.Settings
	 */
	public static AbstractBlock.Settings createNetherSettings(MapColor color) {
		return AbstractBlock.Settings.create()
				.mapColor(color)
				.strength(2.0F)
				.sounds(BlockSoundGroup.NETHER_STEM);
	}

	/**
	 * Factory to create default block settings for a PillarBlock Nether stem with
	 * different map colors on the top/bottom versus the sides.
	 *
	 * @param wood Map color for non-bark faces of stem (ends)
	 * @param bark Map color for bark faces of stem (sides)
	 * @return New AbstractBlock.Settings
	 */
	public static AbstractBlock.Settings createNetherSettings(MapColor wood, MapColor bark) {
		return AbstractBlock.Settings.create()
				.mapColor((state) -> Direction.Axis.Y.equals(state.get(PillarBlock.AXIS)) ? wood : bark)
				.strength(2.0F)
				.sounds(BlockSoundGroup.NETHER_STEM);
	}

	/**
	 * Factory to create default block settings for a QuarterLogBlock Nether
	 * stem with different map colors on the bark versus the cut faces.
	 *
	 * @param wood Map color for cut faces of log
	 * @param bark Map color for bark faces of log
	 * @return New AbstractBlock.Settings
	 */
	public static AbstractBlock.Settings createQuarterLogNetherSettings(MapColor wood, MapColor bark) {
		return AbstractBlock.Settings.create()
				.mapColor(
						(state) ->
								switch (state.get(PillarBlock.AXIS)) {
									case Y -> wood;
									case X ->
											switch (state.get(QuarterLogBlock.BARK_SIDE)) {
												case NORTHWEST, SOUTHWEST -> bark;
												case NORTHEAST, SOUTHEAST -> wood;
											};
									case Z ->
											switch (state.get(QuarterLogBlock.BARK_SIDE)) {
												case SOUTHEAST, SOUTHWEST -> bark;
												case NORTHEAST, NORTHWEST -> wood;
											};
								}
				)
				.strength(2.0F)
				.sounds(BlockSoundGroup.NETHER_STEM);
	}
}
