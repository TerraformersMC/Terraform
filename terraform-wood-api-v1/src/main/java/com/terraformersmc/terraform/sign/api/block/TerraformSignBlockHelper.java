package com.terraformersmc.terraform.sign.api.block;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@SuppressWarnings("unused")
public class TerraformSignBlockHelper {
	private static final Map<Identifier, WoodType> WOOD_TYPE_CACHE = new ConcurrentHashMap<>();

	@SuppressWarnings("UnnecessaryReturnStatement")
	private TerraformSignBlockHelper() {
		return;
	}

	/**
	 * Registration helper for vanilla sign types (WallSignBlock, HangingSignBlock, etc.).  The sign block will be
	 * registered to the block registry, and also as a valid block for the appropriate vanilla sign block entity.
	 * <p/>
	 * This method requires the block properties applied to the block already had the registry key applied.
	 *
	 * @param key The resource key of the sign block to be registered
	 * @param block The sign block to be registered
	 * @return The registered sign block
	 * @param <T> A descendant of {@linkplain SignBlock}
	 */
	public static <T extends SignBlock> T registerSignBlock(ResourceKey<Block> key, T block) {
		if (block instanceof StandingSignBlock || block instanceof WallSignBlock) {
			BlockEntityType.SIGN.addSupportedBlock(block);
		} else if (block instanceof CeilingHangingSignBlock || block instanceof WallHangingSignBlock) {
			BlockEntityType.HANGING_SIGN.addSupportedBlock(block);
		} else {
			throw new IllegalArgumentException("This method only accepts vanilla sign blocks and descendants!");
		}

		return Registry.register(BuiltInRegistries.BLOCK, key, block);
	}

	/**
	 * Registration helper for vanilla sign types (WallSignBlock, HangingSignBlock, etc.).  The sign block will be
	 * registered to the block registry, and also as a valid block for the appropriate vanilla sign block entity.
	 * <p/>
	 * This method creates the resource key and applies it to the block properties for you.
	 *
	 * @param id The identifier of the sign block to be registered
	 * @param factory A factory which creates the block to be registered using the provided block properties
	 * @return The registered sign block
	 * @param <T> A descendant of {@linkplain SignBlock}
	 */
	public static <T extends SignBlock> T registerSignBlock(Identifier id, Function<BlockBehaviour.Properties, T> factory, BlockBehaviour.Properties properties) {
		ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, id);

		return registerSignBlock(key, factory.apply(properties.setId(key)));
	}

	/**
	 * Creates and registers a {@linkplain WoodType} and associated {@linkplain BlockSetType} with the provided
	 * identifier and configured identically to the Oak wood type.  This can be used as shorthand when no custom
	 * configuration is desired.  The identifier's path should be identical to the name of related blocks
	 * (f.e. "fir" for "traverse:fir_planks" etc.).
	 *
	 * @see WoodTypeBuilder
	 * @see BlockSetTypeBuilder
	 *
	 * @param typeId An identifier with the base name of the wood type
	 * @return A registered WoodType ready to be used when creating a sign block
	 */
	public static WoodType registerDefaultWoodType(Identifier typeId) {
		return WOOD_TYPE_CACHE.computeIfAbsent(typeId, id -> WoodTypeBuilder.copyOf(WoodType.OAK).register(id, BlockSetTypeBuilder.copyOf(BlockSetType.OAK).register(id)));
	}
}
