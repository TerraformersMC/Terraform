package com.terraformersmc.terraform.shapes.api.filler;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.impl.filler.RandomSimpleFiller;
import com.terraformersmc.terraform.shapes.impl.filler.SetFiller;
import com.terraformersmc.terraform.shapes.impl.filler.SimpleFiller;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;
import java.util.function.Consumer;

/**
 * Fillers are used to set blocks in a game level, according to the filler's type and configuration.
 * <p/>
 * <ul>
 * <li>{@link #randomSimple(LevelWriter, BlockState, RandomSource, float)}</li>
 * <li>{@link #randomSimple(LevelWriter, BlockState, int, RandomSource, float)}</li>
 * <li>{@link #set(LevelWriter, BlockState, Set)}</li>
 * <li>{@link #set(LevelWriter, BlockState, int, Set)}</li>
 * <li>{@link #simple(LevelWriter, BlockState)}</li>
 * <li>{@link #simple(LevelWriter, BlockState, int)}</li>
 * </ul>
 */
@SuppressWarnings("unused")
public interface Filler extends Consumer<Position> {
	/**
	 * Creates a random simple filler with default update flags.
	 * This filler will randomly set blocks to the specified state, with the specified probability.
	 *
	 * @param level target level
	 * @param state new block state
	 * @param random random source
	 * @param probability replacement probability
	 * @return new RandomSimpleFiller
	 */
	static Filler randomSimple(LevelWriter level, BlockState state, RandomSource random, float probability) {
		return new RandomSimpleFiller(level, state, random, probability);
	}

	/**
	 * Creates a random simple filler with specified update flags.
	 * This filler will randomly set blocks to the specified state, with the specified probability.
	 *
	 * @param level target level
	 * @param state new block state
	 * @param flags update flags
	 * @param random random source
	 * @param probability replacement probability
	 * @return new RandomSimpleFiller
	 */
	static Filler randomSimple(LevelWriter level, BlockState state, int flags, RandomSource random, float probability) {
		return new RandomSimpleFiller(level, state, flags, random, probability);
	}

	/**
	 * Creates a filler with the default update flags,
	 * which stores the target block positions to the provided set.
	 * This can be used to later take action on all the target blocks.
	 *
	 * @param level target level
	 * @param state new block state
	 * @param set set which will contain block positions
	 * @return new SetFiller
	 */
	static Filler set(LevelWriter level, BlockState state, Set<BlockPos> set) {
		return new SetFiller(level, state, set);
	}

	/**
	 * Creates a filler with the specified update flags,
	 * which stores the target block positions to the provided set.
	 * This can be used to later take action on all the target blocks.
	 *
	 * @param level target level
	 * @param state new block state
	 * @param flags update flags
	 * @param set set which will contain block positions
	 * @return new SetFiller
	 */
	static Filler set(LevelWriter level, BlockState state, int flags, Set<BlockPos> set) {
		return new SetFiller(level, state, flags, set);
	}

	/**
	 * Creates a simple filler with default update flags.
	 * This filler will set all blocks to the specified state.
	 *
	 * @param level target level
	 * @param state new block state
	 * @return new SimpleFiller
	 */
	static Filler simple(LevelWriter level, BlockState state) {
		return new SimpleFiller(level, state);
	}

	/**
	 * Creates a simple filler with specified update flags.
	 * This filler will set all blocks to the specified state.
	 *
	 * @param level target level
	 * @param state new block state
	 * @param flags update flags
	 * @return new SimpleFiller
	 */
	static Filler simple(LevelWriter level, BlockState state, int flags) {
		return new SimpleFiller(level, state, flags);
	}
}
