package com.terraformersmc.terraform.tree.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.WouldSurvivePredicate;

/**
 * Provides alternative sapling planting conditions for drier climates.
 */
@SuppressWarnings("unused")
public class TerraformDesertSaplingBlock extends SaplingBlock {
	private final boolean onlyDry;

	/**
	 * Creates a sapling that can be placed wherever normal saplings can be placed, and also on sand.
	 *
	 * @param treeGrower tree grower implementation
	 * @param properties sapling properties
	 */
	public TerraformDesertSaplingBlock(TreeGrower treeGrower, Properties properties) {
		this(treeGrower, false, properties);
	}

	/**
	 * Creates a sapling with the selected placement requirements depending on the value of onlyDry:
	 * <ul>
	 * <li>true - "supports_dry_vegetation" block tag (sand, terracotta, dirt, farmland)</li>
	 * <li>false - "supports_vegetation" block tag (dirt, grass, moss, mud, farmland) plus sand</li>
	 * </ul>
	 *
	 * @param treeGrower tree grower implementation
	 * @param onlyDry only dry substrates, or also wet ones?
	 * @param properties sapling properties
	 */
	public TerraformDesertSaplingBlock(TreeGrower treeGrower, boolean onlyDry, Properties properties) {
		super(treeGrower, properties);

		this.onlyDry = onlyDry;
	}

	/**
	 * This method is used by {@linkplain VegetationBlock#canSurvive}, which in turn determines where a player
	 * can plant the sapling.  If the tree feature placement uses the {@linkplain WouldSurvivePredicate} to check
	 * whether a tree can be placed, this method also ultimately determines worldgen placement eligibility.
	 *
	 * @param state the block state of the block below the sapling
	 * @param level the level in which the sapling is being placed
	 * @param pos the position of the block below the sapling
	 * @return true if the block below the sapling matches the relevant block tag(s)
	 */
	@Override
	public boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		if (onlyDry) {
			return state.is(BlockTags.SUPPORTS_DRY_VEGETATION);
		} else {
			return state.is(BlockTags.SAND) || super.mayPlaceOn(state, level, pos);
		}
	}
}
