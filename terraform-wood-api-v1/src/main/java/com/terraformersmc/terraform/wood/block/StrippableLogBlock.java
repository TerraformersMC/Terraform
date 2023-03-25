package com.terraformersmc.terraform.wood.block;

import java.util.function.Supplier;

import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;

@Deprecated(forRemoval = true)
public class StrippableLogBlock extends PillarBlock {
	/**
	 * <p>This class is deprecated in favor of using PillarBlock and Fabric's StrippableBlockRegistry.</p>
	 *
	 * <pre>{@code
	 *     logBlock = new PillarBlock(settings);
	 *     strippedBlock = new PillarBlock(settings);
	 *     StrippableBlockRegistry.register(logBlock, strippedBlock);
	 * }</pre>
	 *
	 * @param stripped Supplier of default BlockState for stripped variant
	 * @param top Ignored (not implemented)
	 * @param settings Block Settings for log 
	 */
	public StrippableLogBlock(Supplier<Block> stripped, MapColor top, Settings settings) {
		super(settings);

		if (stripped != null) {
			StrippableBlockRegistry.register(this, stripped.get());
		}
	}

	/**
	 * Factory to create a PillarBlock with the provided settings and
	 * the same map color on the top/bottom and sides.
	 *
	 * @param settings Block Settings for log
	 * @param color Map color for all faces of log
	 * @return New PillarBlock
	 */
	public static PillarBlock of(Block.Settings settings, MapColor color) {
		return new PillarBlock(settings.mapColor(color));
	}

	/**
	 * Factory to create a PillarBlock with default settings and
	 * different map colors on the top/bottom versus the sides.
	 *
	 * @param wood Map color for non-bark faces of log (ends)
	 * @param bark Map color for bark faces of log (sides)
	 * @return New PillarBlock
	 */
	public static PillarBlock of(MapColor wood, MapColor bark) {
		return new PillarBlock(
				Block.Settings.of(
						Material.WOOD,
						(state) -> Direction.Axis.Y.equals(state.get(PillarBlock.AXIS)) ? wood : bark
				).strength(2.0F).sounds(BlockSoundGroup.WOOD)
		);
	}
}
