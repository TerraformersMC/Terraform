package com.terraformersmc.terraform.wood.api.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;

@SuppressWarnings("unused")
public final class PillarLogHelper {
	private PillarLogHelper() {
		return;
	}

	/**
	 * Factory to create default block settings for a PillarBlock log with
	 * the same map color on all block faces.
	 *
	 * @param color Map color for all faces of log
	 * @return New AbstractBlock.Settings
	 */
	public static AbstractBlock.Settings settings(MapColor color) {
		return AbstractBlock.Settings.create()
				.mapColor(color)
				.instrument(NoteBlockInstrument.BASS)
				.strength(2.0F)
				.sounds(BlockSoundGroup.WOOD)
				.burnable();
	}

	/**
	 * Factory to create default block settings for a PillarBlock log with
	 * different map colors on the top/bottom versus the sides.
	 *
	 * @param wood Map color for non-bark faces of log (ends)
	 * @param bark Map color for bark faces of log (sides)
	 * @return New AbstractBlock.Settings
	 */
	public static AbstractBlock.Settings settings(MapColor wood, MapColor bark) {
		return AbstractBlock.Settings.create()
				.mapColor((state) -> Direction.Axis.Y.equals(state.get(PillarBlock.AXIS)) ? wood : bark)
				.instrument(NoteBlockInstrument.BASS)
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
	public static AbstractBlock.Settings settingsNether(MapColor color) {
		return AbstractBlock.Settings.create()
				.mapColor(color)
				.instrument(NoteBlockInstrument.BASS)
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
	public static AbstractBlock.Settings settingsNether(MapColor wood, MapColor bark) {
		return AbstractBlock.Settings.create()
				.mapColor((state) -> Direction.Axis.Y.equals(state.get(PillarBlock.AXIS)) ? wood : bark)
				.instrument(NoteBlockInstrument.BASS)
				.strength(2.0F)
				.sounds(BlockSoundGroup.NETHER_STEM);
	}
}
