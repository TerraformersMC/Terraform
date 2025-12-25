package com.terraformersmc.terraform.wood.api.data;

import com.terraformersmc.terraform.wood.api.block.*;
import net.minecraft.resources.Identifier;

/**
 * Convenience Identifiers for Terraform wood API models.
 *
 * <p>
 * For usage examples of these models and the wood API in general,
 * see <a href="https://github.com/TerraformersMC/Terrestria">the Terrestria project source code</a>.
 * </p>
 */
@SuppressWarnings({"unused", "SameParameterValue"})
public final class WoodModels {
	/**
	 * Extend this model for {@linkplain QuarterLogBlock} block models.  F.e.:
	 *
	 * <pre>{@code
	 * {
	 *   "parent": "terraform:block/quarter_log",
	 *   "textures": {
	 *     "particle": "terrestria:block/cypress_log",
	 *     "end": "terrestria:block/cypress_quarter_log_top",
	 *     "inside": "terrestria:block/cypress_quarter_log",
	 *     "side": "terrestria:block/cypress_log"
	 *   }
	 * }
	 * }</pre>
	 */
	public static final Identifier BLOCK_QUARTER_LOG = block("quarter_log");

	/**
	 * Extend this model for {@linkplain SmallLogBlock} block models when each direction is false.  F.e.:
	 *
	 * <pre>{@code
	 * {
	 *   "parent": "terraform:block/small_log",
	 *   "textures": {
	 *     "particle": "terrestria:block/sakura_log",
	 *     "side": "terrestria:block/sakura_log"
	 *   }
	 * }
	 * }</pre>
	 */
	public static final Identifier BLOCK_SMALL_LOG = block("small_log");

	/**
	 * Extend this model for {@linkplain SmallLogBlock} block models when each direction is true.  F.e.:
	 *
	 * <pre>{@code
	 * {
	 *   "parent": "terraform:block/small_log_branch",
	 *   "textures": {
	 *     "particle": "terrestria:block/sakura_log",
	 *     "end": "terrestria:block/sakura_log_top",
	 *     "side": "terrestria:block/sakura_log"
	 *   }
	 * }
	 * }</pre>
	 */
	public static final Identifier BLOCK_SMALL_LOG_BRANCH = block("small_log_branch");

	/**
	 * Extend this model for {@linkplain SmallLogBlock} block models when leaves=true and each direction is false.  F.e.:
	 *
	 * <pre>{@code
	 * {
	 *   "parent": "terraform:block/small_log_leaves",
	 *   "textures": {
	 *     "particle": "terrestria:block/sakura_leaves",
	 *     "leaves": "terrestria:block/sakura_leaves"
	 *   }
	 * }
	 * }</pre>
	 */
	public static final Identifier BLOCK_SMALL_LOG_LEAVES = block("small_log_leaves");

	/**
	 * Extend this model for {@linkplain SmallLogBlock} block models when leaves=true and each direction is true.  F.e.:
	 *
	 * <pre>{@code
	 * {
	 *   "parent": "terraform:block/small_log_leaves_cutout",
	 *   "textures": {
	 *     "particle": "terrestria:block/sakura_leaves",
	 *     "leaves": "terrestria:block/sakura_leaves"
	 *   }
	 * }
	 * }</pre>
	 */
	public static final Identifier BLOCK_SMALL_LOG_LEAVES_CUTOUT = block("small_log_leaves_cutout");


	/**
	 * Extend this model for {@linkplain SmallLogBlock} item models.  F.e.:
	 *
	 * <pre>{@code
	 * {
	 *   "parent": "terraform:item/small_log",
	 *   "textures": {
	 *     "particle": "terrestria:block/sakura_log",
	 *     "all": "terrestria:block/sakura_log"
	 *   }
	 * }
	 * }</pre>
	 */
	public static final Identifier ITEM_SMALL_LOG = item("small_log");


	@SuppressWarnings("UnnecessaryReturnStatement")
	private WoodModels() {
		return;
	}

	private static Identifier block(String path) {
		return Identifier.fromNamespaceAndPath("terraform", "block/" + path);
	}

	private static Identifier item(String path) {
		return Identifier.fromNamespaceAndPath("terraform", "item/" + path);
	}
}
