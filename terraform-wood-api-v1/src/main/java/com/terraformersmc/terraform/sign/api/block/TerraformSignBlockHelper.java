package com.terraformersmc.terraform.sign.api.block;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@SuppressWarnings("unused")
public class TerraformSignBlockHelper {
	private static final Map<Identifier, WoodType> WOOD_TYPE_CACHE = new ConcurrentHashMap<>();

	private TerraformSignBlockHelper() {
		return;
	}

	/**
	 * Registration helper for vanilla sign types (WallSignBlock, HangingSignBlock, etc.).  The sign block will be
	 * registered to the block registry, and also as a valid block for the appropriate vanilla sign block entity.
	 *
	 * This method requires the block settings applied to the block already had the registry key applied.
	 *
	 * @param key The registery key of the sign block to be registered
	 * @param block The sign block to be registered
	 * @return The registered sign block
	 * @param <T> A descendent of {@linkplain AbstractSignBlock}
	 */
	public static <T extends AbstractSignBlock> T registerSignBlock(RegistryKey<Block> key, T block) {
		if (block instanceof SignBlock || block instanceof WallSignBlock) {
			BlockEntityType.SIGN.addSupportedBlock(block);
		} else if (block instanceof HangingSignBlock || block instanceof WallHangingSignBlock) {
			BlockEntityType.HANGING_SIGN.addSupportedBlock(block);
		} else {
			throw new IllegalArgumentException("This method only accepts vanilla sign blocks!");
		}

		return Registry.register(Registries.BLOCK, key, block);
	}

	/**
	 * Registration helper for vanilla sign types (WallSignBlock, HangingSignBlock, etc.).  The sign block will be
	 * registered to the block registry, and also as a valid block for the appropriate vanilla sign block entity.
	 *
	 * This method creates the registry key and applies it to the block settings for you.
	 *
	 * @param id The identifier of the sign block to be registered
	 * @param factory A factory which creates the block to be registered using the provided block settings
	 * @return The registered sign block
	 * @param <T> A descendent of {@linkplain AbstractSignBlock}
	 */
	public static <T extends AbstractSignBlock> T registerSignBlock(Identifier id, Function<AbstractBlock.Settings, T> factory, AbstractBlock.Settings settings) {
		RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);

		return registerSignBlock(key, factory.apply(settings.registryKey(key)));
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
