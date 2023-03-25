package com.terraformersmc.terraform.wood.block;

import java.util.function.Supplier;

import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;

public class StrippableLogBlock extends PillarBlock {
	/**
	 * <p>This class is deprecated in favor of using PillarBlock and Fabric's StrippableBlockRegistry.
	 * However, we will keep the new factory methods unless Mojang makes map colors easier.</p>
	 *
	 * <pre>{@code
	 *     PillarBlock logBlock = StrippableLogBlock.of(woodColor, barkColor);
	 *     PillarBlock strippedBlock = StrippableLogBlock.of(woodColor);
	 *     StrippableBlockRegistry.register(logBlock, strippedBlock);
	 * }</pre>
	 *
	 * @param stripped Supplier of default BlockState for stripped variant
	 * @param top Ignored (not implemented)
	 * @param settings Block Settings for log 
	 */
	@Deprecated(forRemoval = true, since = "6.1.0")
	public StrippableLogBlock(Supplier<Block> stripped, MapColor top, Settings settings) {
		super(settings);

		if (stripped != null) {
			StrippableBlockRegistry.register(this, stripped.get());
		}
	}

	/**
	 * Factory to create a PillarBlock with default settings and
	 * the same map color on all block faces.
	 *
	 * @param color Map color for all faces of log
	 * @return New PillarBlock
	 */
	public static PillarBlock of(MapColor color) {
		return new PillarBlock(
				Block.Settings.of(
						Material.WOOD,
						color
				).strength(2.0F).sounds(BlockSoundGroup.WOOD)
		);
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
