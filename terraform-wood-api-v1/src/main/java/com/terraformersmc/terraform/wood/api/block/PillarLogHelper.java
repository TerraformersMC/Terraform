package com.terraformersmc.terraform.wood.api.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

@SuppressWarnings("unused")
public final class PillarLogHelper {
	@SuppressWarnings("UnnecessaryReturnStatement")
	private PillarLogHelper() {
		return;
	}

	/**
	 * Factory to create default block properties for a RotatedPillarBlock, QuarterLogBlock,
	 * or BareSmallLogBlock log with the same map color on all block faces.
	 *
	 * @param color Map color for all faces of log
	 * @return New BlockBehaviour.Properties
	 */
	public static BlockBehaviour.Properties createProperties(MapColor color) {
		return BlockBehaviour.Properties.of()
				.mapColor(color)
				.strength(2.0F)
				.sound(SoundType.WOOD)
				.ignitedByLava();
	}

	/**
	 * Factory to create default block properties for a RotatedPillarBlock or BareSmallLogBlock
	 * log with different map colors on the top/bottom versus the sides.
	 *
	 * @param wood Map color for non-bark faces of log (ends)
	 * @param bark Map color for bark faces of log (sides)
	 * @return New BlockBehaviour.Properties
	 */
	public static BlockBehaviour.Properties createProperties(MapColor wood, MapColor bark) {
		return BlockBehaviour.Properties.of()
				.mapColor((state) -> Direction.Axis.Y.equals(state.getValue(RotatedPillarBlock.AXIS)) ? wood : bark)
				.strength(2.0F)
				.sound(SoundType.WOOD)
				.ignitedByLava();
	}

	/**
	 * Factory to create default block properties for a QuarterLogBlock
	 * log with different map colors on the bark versus the cut faces.
	 *
	 * @param wood Map color for cut faces of log
	 * @param bark Map color for bark faces of log
	 * @return New BlockBehaviour.Properties
	 */
	public static BlockBehaviour.Properties createQuarterLogProperties(MapColor wood, MapColor bark) {
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
	 * Factory to create default block properties for a SmallLogBlock
	 * log with the same map color on all block faces.
	 * <p/>
	 * If there is no associated LeavesBlock, instead use
	 * {@linkplain PillarLogHelper#createProperties(MapColor)}
	 *
	 * @param leaves Associated LeavesBlock
	 * @param color Map color for all faces of log
	 * @return New BlockBehaviour.Properties
	 */
	public static BlockBehaviour.Properties createSmallLogProperties(Block leaves, MapColor color) {
		return BlockBehaviour.Properties.of()
				.mapColor((state) -> state.getValue(SmallLogBlock.HAS_LEAVES) ? leaves.defaultMapColor() : color)
				.strength(2.0F)
				.sound(SoundType.WOOD)
				.ignitedByLava();
	}

	/**
	 * Factory to create default block properties for a SmallLogBlock
	 * log with different map colors on the top/bottom versus the sides.
	 * <p/>
	 * If there is no associated LeavesBlock, instead use
	 * {@linkplain PillarLogHelper#createProperties(MapColor, MapColor)}
	 *
	 * @param leaves Associated LeavesBlock
	 * @param wood Map color for non-bark faces of log (ends)
	 * @param bark Map color for bark faces of log (sides)
	 * @return New BlockBehaviour.Properties
	 */
	public static BlockBehaviour.Properties createSmallLogProperties(Block leaves, MapColor wood, MapColor bark) {
		return BlockBehaviour.Properties.of()
				.mapColor((state) -> state.getValue(SmallLogBlock.HAS_LEAVES) ? leaves.defaultMapColor() : state.getValue(SmallLogBlock.UP) ? wood : bark)
				.strength(2.0F)
				.sound(SoundType.WOOD)
				.ignitedByLava();
	}

	/**
	 * Factory to create default block properties for a PillarBlock Nether stem with
	 * the same map color on all block faces.
	 *
	 * @param color Map color for all faces of stem
	 * @return New BlockBehaviour.Properties
	 */
	public static BlockBehaviour.Properties createNetherProperties(MapColor color) {
		return BlockBehaviour.Properties.of()
				.mapColor(color)
				.strength(2.0F)
				.sound(SoundType.STEM);
	}

	/**
	 * Factory to create default block properties for a PillarBlock Nether stem with
	 * different map colors on the top/bottom versus the sides.
	 *
	 * @param wood Map color for non-bark faces of stem (ends)
	 * @param bark Map color for bark faces of stem (sides)
	 * @return New BlockBehaviour.Properties
	 */
	public static BlockBehaviour.Properties createNetherProperties(MapColor wood, MapColor bark) {
		return BlockBehaviour.Properties.of()
				.mapColor((state) -> Direction.Axis.Y.equals(state.getValue(RotatedPillarBlock.AXIS)) ? wood : bark)
				.strength(2.0F)
				.sound(SoundType.STEM);
	}

	/**
	 * Factory to create default block properties for a QuarterLogBlock Nether
	 * stem with different map colors on the bark versus the cut faces.
	 *
	 * @param wood Map color for cut faces of log
	 * @param bark Map color for bark faces of log
	 * @return New BlockBehaviour.Properties
	 */
	public static BlockBehaviour.Properties createQuarterLogNetherProperties(MapColor wood, MapColor bark) {
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
