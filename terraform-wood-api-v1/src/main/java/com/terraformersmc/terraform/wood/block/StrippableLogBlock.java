package com.terraformersmc.terraform.wood.block;

import java.util.function.Supplier;

import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;

/**
 * <p>This class is deprecated in favor of using PillarBlock and Fabric's StrippableBlockRegistry.
 * However, we are providing factory methods in PillarLogHelper until Mojang makes map colors easier.</p>
 *
 * <pre>{@code
 *     PillarBlock logBlock = PillarLogHelper.of(woodColor, barkColor);
 *     PillarBlock strippedBlock = PillarLogHelper.of(woodColor);
 *     StrippableBlockRegistry.register(logBlock, strippedBlock);
 * }</pre>
 */
@Deprecated(forRemoval = true, since = "6.1.0")
public class StrippableLogBlock extends PillarBlock {
	/**
	 * <p>This class is deprecated in favor of using PillarBlock and Fabric's StrippableBlockRegistry.
	 * However, we are providing factory methods in PillarLogHelper until Mojang makes map colors easier.</p>
	 *
	 * <pre>{@code
	 *     PillarBlock logBlock = PillarLogHelper.of(woodColor, barkColor);
	 *     PillarBlock strippedBlock = PillarLogHelper.of(woodColor);
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
	 * Use {@code PillarLogHelper.of(color) } instead.
	 *
	 * @param color Map color for all faces of log
	 * @return New PillarBlock
	 */
	public static PillarBlock of(MapColor color) {
		return new PillarBlock(Block.Settings.of()
				.mapColor(color)
				.strength(2.0F)
				.sounds(BlockSoundGroup.WOOD)
				.burnable()
		);
	}

	/**
	 * Use {@code PillarLogHelper.of(woodColor, barkColor) } instead.
	 *
	 * @param wood Map color for non-bark faces of log (ends)
	 * @param bark Map color for bark faces of log (sides)
	 * @return New PillarBlock
	 */
	public static PillarBlock of(MapColor wood, MapColor bark) {
		return new PillarBlock(Block.Settings.of()
				.mapColor((state) -> Direction.Axis.Y.equals(state.get(PillarBlock.AXIS)) ? wood : bark)
				.strength(2.0F)
				.sounds(BlockSoundGroup.WOOD)
				.burnable()
		);
	}
}
