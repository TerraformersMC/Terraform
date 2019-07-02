package io.github.terraformersmc.terraform.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableIntBoundingBox;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class TerraformSwampTreeFeature extends AbstractTreeFeature<DefaultFeatureConfig> {

	public static final BlockState OAK_LOG = Blocks.OAK_LOG.getDefaultState();
	public static final BlockState OAK_LEAVES = Blocks.OAK_LEAVES.getDefaultState().with(Properties.PERSISTENT, false);
	private final int minHeight;
	private BlockState log;
	private BlockState leaves;

	public TerraformSwampTreeFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactory, boolean emitNeighborBlockUpdates) {
		this(configFactory, emitNeighborBlockUpdates, 5);
	}

	public TerraformSwampTreeFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactory, boolean emitNeighborBlockUpdates, int minHeight) {
		this(configFactory, emitNeighborBlockUpdates, minHeight, OAK_LOG, OAK_LEAVES);
	}

	public TerraformSwampTreeFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactory, boolean emitNeighborBlockUpdates, int minHeight, BlockState log, BlockState leaves) {
		super(configFactory, emitNeighborBlockUpdates);
		this.minHeight = minHeight;
		this.log = log;
		this.leaves = leaves;
	}

	@Override
	public boolean generate(Set<BlockPos> blocks, ModifiableTestableWorld world, Random random, BlockPos pos, MutableIntBoundingBox boundingBox) {
		int height = random.nextInt(4) + minHeight;
		pos = world.getTopPosition(Heightmap.Type.OCEAN_FLOOR, pos);
		boolean generate = true;
		if (pos.getY() >= 1 && pos.getY() + height + 1 <= 256) {
			int y;
			int var10;
			int var11;
			for (y = pos.getY(); y <= pos.getY() + 1 + height; ++y) {
				int offset = 1;
				if (y == pos.getY()) {
					offset = 0;
				}

				if (y >= pos.getY() + 1 + height - 2) {
					offset = 3;
				}

				BlockPos.Mutable checking = new BlockPos.Mutable();

				for (var10 = pos.getX() - offset; var10 <= pos.getX() + offset && generate; ++var10) {
					for (var11 = pos.getZ() - offset; var11 <= pos.getZ() + offset && generate; ++var11) {
						if (y >= 0 && y < 256) {
							checking.set(var10, y, var11);
							if (!isAirOrLeaves(world, checking)) {
								if (isWater(world, checking)) {
									if (y > pos.getY()) {
										generate = false;
									}
								} else {
									generate = false;
								}
							}
						} else {
							generate = false;
						}
					}
				}
			}

			if (!generate) {
				return false;
			} else if (isNaturalDirtOrGrass(world, pos.down()) && pos.getY() < 256 - height - 1) {
				this.setToDirt(world, pos.down());

				int var12;
				BlockPos leavesPos;
				int var17;
				int var18;
				for (y = pos.getY() - 3 + height; y <= pos.getY() + height; ++y) {
					var17 = y - (pos.getY() + height);
					var18 = 2 - var17 / 2;

					for (var10 = pos.getX() - var18; var10 <= pos.getX() + var18; ++var10) {
						var11 = var10 - pos.getX();

						for (var12 = pos.getZ() - var18; var12 <= pos.getZ() + var18; ++var12) {
							int var13 = var12 - pos.getZ();
							if (Math.abs(var11) != var18 || Math.abs(var13) != var18 || random.nextInt(2) != 0 && var17 != 0) {
								leavesPos = new BlockPos(var10, y, var12);
								if (isAirOrLeaves(world, leavesPos) || isReplaceablePlant(world, leavesPos)) {
									this.setBlockState(world, leavesPos, leaves);
								}
							}
						}
					}
				}

				for (y = 0; y < height; ++y) {
					BlockPos logPos = pos.up(y);
					if (isAirOrLeaves(world, logPos) || isWater(world, logPos)) {
						this.setBlockState(blocks, world, logPos, log, boundingBox);
					}
				}

				for (y = pos.getY() - 3 + height; y <= pos.getY() + height; ++y) {
					var17 = y - (pos.getY() + height);
					var18 = 2 - var17 / 2;
					BlockPos.Mutable outerLeavesPos = new BlockPos.Mutable();

					for (var11 = pos.getX() - var18; var11 <= pos.getX() + var18; ++var11) {
						for (var12 = pos.getZ() - var18; var12 <= pos.getZ() + var18; ++var12) {
							outerLeavesPos.set(var11, y, var12);
							if (isLeaves(world, outerLeavesPos)) {
								BlockPos westPos = outerLeavesPos.west();
								leavesPos = outerLeavesPos.east();
								BlockPos norhtPos = outerLeavesPos.north();
								BlockPos southPos = outerLeavesPos.south();
								if (random.nextInt(4) == 0 && isAir(world, westPos)) {
									this.addVines(world, westPos, VineBlock.EAST);
								}

								if (random.nextInt(4) == 0 && isAir(world, leavesPos)) {
									this.addVines(world, leavesPos, VineBlock.WEST);
								}

								if (random.nextInt(4) == 0 && isAir(world, norhtPos)) {
									this.addVines(world, norhtPos, VineBlock.SOUTH);
								}

								if (random.nextInt(4) == 0 && isAir(world, southPos)) {
									this.addVines(world, southPos, VineBlock.NORTH);
								}
							}
						}
					}
				}

				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private void addVines(ModifiableTestableWorld var1, BlockPos var2, BooleanProperty var3) {
		BlockState var4 = Blocks.VINE.getDefaultState().with(var3, true);
		this.setBlockState(var1, var2, var4);
		int var5 = 4;

		for (var2 = var2.down(); isAir(var1, var2) && var5 > 0; --var5) {
			this.setBlockState(var1, var2, var4);
			var2 = var2.down();
		}

	}

}
