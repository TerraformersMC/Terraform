package com.terraformersmc.terraform.leaves.api.data;

import net.minecraft.util.Identifier;

/**
 * Convenience Identifiers for Terraform leaves API models.
 *
 * <p>
 * For usage examples of these models and the leaves API in general,
 * see <a href="https://github.com/TerraformersMC/Terrestria">the Terrestria project source code</a>.
 * </p>
 */
@SuppressWarnings({"unused", "SameParameterValue"})
public final class LeavesModels {
	/**
	 * Extend this model for pillar-textured {@linkplain net.minecraft.block.LeavesBlock} models.  F.e.:
	 *
	 * <pre>{@code
	 * {
	 *   "parent": "terraform:block/pillar_leaves",
	 *   "textures": {
	 *     "particle": "terrestria:block/willow_leaves",
	 *     "end": "terrestria:block/willow_leaves_top",
	 *     "side": "terrestria:block/willow_leaves"
	 *   }
	 * }
	 * }</pre>
	 */
	public static final Identifier BLOCK_PILLAR_LEAVES = block("pillar_leaves");


	@SuppressWarnings("UnnecessaryReturnStatement")
	private LeavesModels() {
		return;
	}

	private static Identifier block(String path) {
		return Identifier.of("terraform", "block/" + path);
	}

	private static Identifier item(String path) {
		return Identifier.of("terraform", "item/" + path);
	}
}
