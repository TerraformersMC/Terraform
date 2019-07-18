package io.github.terraformersmc.terraform.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class StrippedBlocks {
	private static boolean changed = false;
	private static final Map<Block, Block> blocks = new HashMap<>();
	private static final Map<Block, Function<BlockState, BlockState>> special = new HashMap<>();

	public static void add(Block origin, Block stripped) {
		Preconditions.checkArgument(origin instanceof PillarBlock, "origin block must be a PillarBlock");
		Preconditions.checkArgument(stripped instanceof PillarBlock, "stripped block must be a PillarBlock");

		blocks.put(origin, stripped);
		changed = true;
	}

	public static void addSpecial(Block origin, Function<BlockState, BlockState> stripper) {
		special.put(origin, stripper);
	}

	public static boolean isStale() {
		return changed;
	}

	public static void inject(ImmutableMap.Builder<Block, Block> target) {
		// The server and client may attempt to run this concurrently.
		synchronized (blocks) {
			target.putAll(blocks);
			blocks.clear();
			changed = false;
		}
	}

	public static Optional<Function<BlockState, BlockState>> getSpecialHandler(Block origin) {
		return Optional.ofNullable(special.get(origin));
	}
}
