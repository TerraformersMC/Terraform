package io.github.terraformersmc.terraform.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.TallSeagrassBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.Supplier;

/**
 * A custom tall seagrass block where the lower half is underwater, and the upper half is above water.
 */
public class TallCattailBlock extends TallSeagrassBlock {
	private Supplier<Item> pickItem;

	public TallCattailBlock(Supplier<Item> pickItem, Settings settings) {
		super(settings);

		this.pickItem = pickItem;
	}

	@Override
	public ItemStack getPickStack(BlockView view, BlockPos pos, BlockState state) {
		return new ItemStack(pickItem.get());
	}

	public BlockState getPlacementState(ItemPlacementContext context) {
		BlockPos pos = context.getBlockPos();

		return pos.getY() < 255 && context.getWorld().getBlockState(pos.up()).canReplace(context) ? this.getDefaultState() : null;
	}

	public FluidState getFluidState(BlockState state) {
		return state.get(HALF) == DoubleBlockHalf.UPPER ? Fluids.EMPTY.getDefaultState() : super.getFluidState(state);
	}
}
