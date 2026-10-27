package com.terraformersmc.terraform.dirt.api.registry;

import net.fabricmc.fabric.api.item.v1.BlockTransformerHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;

/**
 * Allows the addition of custom tillable block mappings. You probably don't need to use this directly if you're using
 * {@link TerraformDirtRegistry}.
 *
 * @deprecated Use Fabric's {@linkplain BlockTransformerHelper} instead.
 */
@Deprecated(since = "18.0.0", forRemoval = true)
@SuppressWarnings("unused")
public final class TillableBlockRegistry {
	@SuppressWarnings("UnnecessaryReturnStatement")
	private TillableBlockRegistry() {
		return;
	}

	/**
	 * Adds a custom tillable block mapping.
	 * <p/>
	 * Note that you don't need to call this yourself if you're already using {@link TerraformDirtRegistry}.
	 *
	 * @deprecated Use Fabric's {@linkplain BlockTransformerHelper} instead.
	 *
	 * @param block the block being tilled
	 * @param state the block to be replaced with
	 */
	@Deprecated(since = "18.0.0", forRemoval = true)
	public static void add(Block block, BlockState state) {
		BlockTransformerHelper.registerTilling(BlockPredicate.matchesBlocks(block), state);
	}
}
