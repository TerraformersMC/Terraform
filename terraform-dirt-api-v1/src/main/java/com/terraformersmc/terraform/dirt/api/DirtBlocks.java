package com.terraformersmc.terraform.dirt.api;

import com.terraformersmc.terraform.dirt.api.block.*;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;

/**
 * Stores a collection of DirtBlocks.
 * These blocks must already be registered to the block registry.
 *
 * @param dirtBlock mandatory Block of the base dirt
 * @param coarseDirtBlock optional Block of the coarse dirt
 * @param farmBlock optional TerraformFarmlandBlock of the cultivated dirt
 * @param grassBlock optional TerraformGrassBlock of the grassy dirt
 * @param myceliumBlock optional TerraformMyceliumBlock of the mushroomy dirt
 * @param pathBlock optional TerraformPathBlock of the flattened dirt path
 * @param podzolBlock optional TerraformSnowyBlock of the podzol dirt
 * @param rootedDirtBlock optional TerraformRootedDirtBlock of the rooted dirt
 */
@SuppressWarnings("unused")
public record DirtBlocks(
	Block dirtBlock,
	@Nullable Block coarseDirtBlock,
	@Nullable TerraformFarmlandBlock farmBlock,
	@Nullable TerraformGrassBlock grassBlock,
	@Nullable TerraformMyceliumBlock myceliumBlock,
	@Nullable TerraformPathBlock pathBlock,
	@Nullable TerraformSnowyBlock podzolBlock,
	@Nullable TerraformRootedDirtBlock rootedDirtBlock
) {
	/**
	 * Legacy DirtBlocks constructor for pre-v18.0.0 compatibility.
	 *
	 * @deprecated Without intent to remove for now.  Please use the full constructor instead.
	 *
	 * @param dirtBlock mandatory Block of the base dirt
	 * @param grassBlock optional TerraformGrassBlock of the grassy dirt
	 * @param pathBlock optional TerraformDirtPathBlock of the flattened dirt path
	 * @param podzolBlock  optional TerraformSnowyBlock of the podzol dirt
	 * @param farmBlock optional TerraformFarmlandBlock of the cultivated dirt
	 */
	@Deprecated(since = "18.0.0")
	public DirtBlocks(
		Block dirtBlock,
		@Nullable TerraformGrassBlock grassBlock,
		@Nullable TerraformPathBlock pathBlock,
		@Nullable TerraformSnowyBlock podzolBlock,
		@Nullable TerraformFarmlandBlock farmBlock
	) {
		this(dirtBlock, null, farmBlock, grassBlock, null, pathBlock, podzolBlock, null);
	}

	/**
	 * @return Array of instance blocks which can transform into farm blocks
	 */
	public Block[] turnsIntoFarmland() {
		ArrayList<Block> targets = new ArrayList<>(16);

		targets.add(dirtBlock);
		if (grassBlock != null) {
			targets.add(grassBlock);
		}
		if (pathBlock != null) {
			targets.add(pathBlock);
		}

		return targets.toArray(Block[]::new);
	}

	/**
	 * @return Array of instance blocks which can transform into path blocks
	 */
	public Block[] turnsIntoDirtPath() {
		ArrayList<Block> targets = new ArrayList<>(16);

		targets.add(dirtBlock);
		if (coarseDirtBlock != null) {
			targets.add(coarseDirtBlock);
		}
		if (grassBlock != null) {
			targets.add(grassBlock);
		}
		if (myceliumBlock != null) {
			targets.add(myceliumBlock);
		}
		if (podzolBlock != null) {
			targets.add(podzolBlock);
		}
		if (rootedDirtBlock != null) {
			targets.add(rootedDirtBlock);
		}

		return targets.toArray(Block[]::new);
	}
}
