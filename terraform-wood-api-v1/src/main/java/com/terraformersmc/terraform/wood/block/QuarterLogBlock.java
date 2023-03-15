package com.terraformersmc.terraform.wood.block;

import java.util.function.Supplier;

import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

/**
 * A log block that has 4 different corners that combine together to form a huge and continuous 2x2 log.
 * Used for the mega variants of Redwood, Fir, etc
 */
public class QuarterLogBlock extends PillarBlock {
	public static final EnumProperty<BarkSide> BARK_SIDE = EnumProperty.of("bark_side", BarkSide.class);

	public QuarterLogBlock(Block.Settings settings) {
		super(settings);
	}

	/**
	 * <p>This constructor is deprecated in favor of using the new QuarterLogBlock.of() factories
	 * and Fabric's StrippableBlockRegistry.</p>
	 *
	 * <pre>{@code
	 *     logBlock = QuarterLogBlock.of(settings, color);
	 *     strippedBlock = QuarterLogBlock.of(settings, color);
	 *     StrippableBlockRegistry.register(logBlock, strippedBlock);
	 * }</pre>
	 *
	 * @param stripped Supplier of default BlockState for stripped variant
	 * @param color Ignored (never implemented)
	 * @param settings Block Settings for log
	 */
	@Deprecated(forRemoval = true)
	public QuarterLogBlock(Supplier<Block> stripped, MapColor color, Block.Settings settings) {
		this(settings);

		if (stripped != null) {
			StrippableBlockRegistry.register(this, stripped.get());
		}
	}

	/**
	 * Factory to create a QuarterLogBlock with the provided settings and
	 * the same map color on the top/bottom and sides.
	 *
	 * @param settings Block Settings for log
	 * @param color Map color for all faces of log
	 * @return New QuarterLogBlock
	 */
	public static QuarterLogBlock of(Block.Settings settings, MapColor color) {
		return new QuarterLogBlock(settings.mapColor(color));
	}

	/**
	 * Factory to create a QuarterLogBlock with default settings and
	 * different map colors on the exposed wood versus the bark sides.
	 *
	 * @param wood Map color for non-bark faces of log
	 * @param bark Map color for bark faces of log
	 * @return New QuarterLogBlock
	 */
	public static QuarterLogBlock of(MapColor wood, MapColor bark) {
		return new QuarterLogBlock(
				Block.Settings.of(
						Material.WOOD,
						(state) ->
							switch (state.get(PillarBlock.AXIS)) {
								case Y -> wood;
								case X ->
									switch (state.get(QuarterLogBlock.BARK_SIDE)) {
										case NORTHWEST, SOUTHWEST -> bark;
										case NORTHEAST, SOUTHEAST -> wood;
									};
								case Z ->
									switch (state.get(QuarterLogBlock.BARK_SIDE)) {
										case SOUTHEAST, SOUTHWEST -> bark;
										case NORTHEAST, NORTHWEST -> wood;
									};
							}
				).strength(2.0F).sounds(BlockSoundGroup.WOOD)
		);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);

		builder.add(BARK_SIDE);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		Vec3d pos = context.getHitPos();
		BlockPos blockPos = context.getBlockPos();

		float hitX = (float) (pos.getX() - blockPos.getX());
		float hitY = (float) (pos.getY() - blockPos.getY());
		float hitZ = (float) (pos.getZ() - blockPos.getZ());

		BarkSide side = BarkSide.fromHit(context.getSide().getAxis(), hitX, hitY, hitZ);

		return super.getPlacementState(context).with(BARK_SIDE, side);
	}

	/**
	 * Represents the two sides of the log the bark appears on. SOUTHEAST for example indicates that the South and East
	 * sides have bark applied, while the North and West sides are bare.
	 */
	public enum BarkSide implements StringIdentifiable {
		SOUTHWEST("southwest"),
		NORTHWEST("northwest"),
		NORTHEAST("northeast"),
		SOUTHEAST("southeast");

		final String name;

		BarkSide(String name) {
			this.name = name;
		}

		/**
		 * Creates a new BarkSide from the fractional hit vectors, which can be derived in the manner seen above in
		 * getPlacementState. The placement logic is to ensure that the side the user clicks on is the farthest from
		 * the bark sides, providing a consistent placing experience.
		 *
		 * @param axis The axis of the log, used to determine how the hit vectors are read.
		 * @param hitX The fractional hit on the X axis, from 0.0 to 1.0.
		 * @param hitY The fractional hit on the Y axis, from 0.0 to 1.0.
		 * @param hitZ The fractional hit on the Z axis, from 0.0 to 1.0.
		 * @return A bark side according to the placement logic
		 */
		public static BarkSide fromHit(Direction.Axis axis, float hitX, float hitY, float hitZ) {
			boolean hitEast;
			boolean hitSouth;

			switch (axis) {
				case Y:
					hitEast = hitX >= 0.5;
					hitSouth = hitZ >= 0.5;
					break;
				case X:
					hitEast = hitY <= 0.5;
					hitSouth = hitZ >= 0.5;
					break;
				default:
					hitEast = hitX >= 0.5;
					hitSouth = hitY >= 0.5;
					break;
			}

			// Logic of placement: The quadrant the player clicks on should be the one farthest from the bark sides.
			return BarkSide.fromHalves(!hitEast, !hitSouth);
		}

		/**
		 * Creates a bark side from the east and south components.
		 *
		 * @param east True for east, false for west
		 * @param south True for south, false for north
		 * @return A BarkSide that is the composite of the two halves: (true, true) -> SOUTHEAST, for example.
		 */
		public static BarkSide fromHalves(boolean east, boolean south) {
			if (east) {
				if (south) {
					return BarkSide.SOUTHEAST;
				} else {
					return BarkSide.NORTHEAST;
				}
			} else {
				if (south) {
					return BarkSide.SOUTHWEST;
				} else {
					return BarkSide.NORTHWEST;
				}
			}
		}

		@Override
		public String toString() {
			return this.name;
		}

		@Override
		public String asString() {
			return this.name;
		}
	}
}
