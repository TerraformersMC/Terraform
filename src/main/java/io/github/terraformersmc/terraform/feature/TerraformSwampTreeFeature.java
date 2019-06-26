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
        boolean var6 = true;
        if (pos.getY() >= 1 && pos.getY() + height + 1 <= 256) {
            int var7;
            int var10;
            int var11;
            for (var7 = pos.getY(); var7 <= pos.getY() + 1 + height; ++var7) {
                int var8 = 1;
                if (var7 == pos.getY()) {
                    var8 = 0;
                }

                if (var7 >= pos.getY() + 1 + height - 2) {
                    var8 = 3;
                }

                BlockPos.Mutable var9 = new BlockPos.Mutable();

                for (var10 = pos.getX() - var8; var10 <= pos.getX() + var8 && var6; ++var10) {
                    for (var11 = pos.getZ() - var8; var11 <= pos.getZ() + var8 && var6; ++var11) {
                        if (var7 >= 0 && var7 < 256) {
                            var9.set(var10, var7, var11);
                            if (!isAirOrLeaves(world, var9)) {
                                if (isWater(world, var9)) {
                                    if (var7 > pos.getY()) {
                                        var6 = false;
                                    }
                                }
                                else {
                                    var6 = false;
                                }
                            }
                        }
                        else {
                            var6 = false;
                        }
                    }
                }
            }

            if (!var6) {
                return false;
            }
            else if (isNaturalDirtOrGrass(world, pos.down()) && pos.getY() < 256 - height - 1) {
                this.setToDirt(world, pos.down());

                int var12;
                BlockPos var14;
                int var17;
                int var18;
                for (var7 = pos.getY() - 3 + height; var7 <= pos.getY() + height; ++var7) {
                    var17 = var7 - (pos.getY() + height);
                    var18 = 2 - var17 / 2;

                    for (var10 = pos.getX() - var18; var10 <= pos.getX() + var18; ++var10) {
                        var11 = var10 - pos.getX();

                        for (var12 = pos.getZ() - var18; var12 <= pos.getZ() + var18; ++var12) {
                            int var13 = var12 - pos.getZ();
                            if (Math.abs(var11) != var18 || Math.abs(var13) != var18 || random.nextInt(2) != 0 && var17 != 0) {
                                var14 = new BlockPos(var10, var7, var12);
                                if (isAirOrLeaves(world, var14) || isReplaceablePlant(world, var14)) {
                                    this.setBlockState(world, var14, OAK_LEAVES);
                                }
                            }
                        }
                    }
                }

                for (var7 = 0; var7 < height; ++var7) {
                    BlockPos var19 = pos.up(var7);
                    if (isAirOrLeaves(world, var19) || isWater(world, var19)) {
                        this.setBlockState(blocks, world, var19, OAK_LOG, boundingBox);
                    }
                }

                for (var7 = pos.getY() - 3 + height; var7 <= pos.getY() + height; ++var7) {
                    var17 = var7 - (pos.getY() + height);
                    var18 = 2 - var17 / 2;
                    BlockPos.Mutable var20 = new BlockPos.Mutable();

                    for (var11 = pos.getX() - var18; var11 <= pos.getX() + var18; ++var11) {
                        for (var12 = pos.getZ() - var18; var12 <= pos.getZ() + var18; ++var12) {
                            var20.set(var11, var7, var12);
                            if (isLeaves(world, var20)) {
                                BlockPos var21 = var20.west();
                                var14 = var20.east();
                                BlockPos var15 = var20.north();
                                BlockPos var16 = var20.south();
                                if (random.nextInt(4) == 0 && isAir(world, var21)) {
                                    this.addVines(world, var21, VineBlock.EAST);
                                }

                                if (random.nextInt(4) == 0 && isAir(world, var14)) {
                                    this.addVines(world, var14, VineBlock.WEST);
                                }

                                if (random.nextInt(4) == 0 && isAir(world, var15)) {
                                    this.addVines(world, var15, VineBlock.SOUTH);
                                }

                                if (random.nextInt(4) == 0 && isAir(world, var16)) {
                                    this.addVines(world, var16, VineBlock.NORTH);
                                }
                            }
                        }
                    }
                }

                return true;
            }
            else {
                return false;
            }
        }
        else {
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
