package com.terraformersmc.terraform.feature;

import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.TreeDecorator;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;

import java.util.List;

public class FallenLogFeatureConfig extends TreeFeatureConfig {
	public final int lengthRandom;

	protected FallenLogFeatureConfig(BlockStateProvider trunk, BlockStateProvider leaves, List<TreeDecorator> decorators, int baseLength, int lengthRandom) {
		super(trunk, leaves, decorators, baseLength);

		this.lengthRandom = lengthRandom;
	}

	@Override
	public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
		ImmutableMap.Builder<T, T> builder = ImmutableMap.builder();

		builder
				.put(ops.createString("trunk_provider"), this.trunkProvider.serialize(ops))
				.put(ops.createString("leaves_provider"), this.leavesProvider.serialize(ops))
				.put(ops.createString("decorators"), ops.createList(this.decorators.stream().map(treeDecorator -> treeDecorator.serialize(ops))))
				.put(ops.createString("base_length"), ops.createInt(this.baseHeight))
				.put(ops.createString("length_random"), ops.createInt(this.lengthRandom));

		return new Dynamic<>(ops, ops.createMap(builder.build()));
	}

	public static <T> FallenLogFeatureConfig deserialize(Dynamic<T> dynamic) {
		BlockStateProviderType<?> trunk = Registry.BLOCK_STATE_PROVIDER_TYPE.get(new Identifier(dynamic.get("trunk_provider").get("type").asString().orElseThrow(RuntimeException::new)));
		BlockStateProviderType<?> leaves = Registry.BLOCK_STATE_PROVIDER_TYPE.get(new Identifier(dynamic.get("leaves_provider").get("type").asString().orElseThrow(RuntimeException::new)));

		return new FallenLogFeatureConfig(
				trunk.deserialize(dynamic.get("trunk_provider").orElseEmptyMap()),
				leaves.deserialize(dynamic.get("leaves_provider").orElseEmptyMap()),
				dynamic.get("decorators").asList(
						dynamicx -> Registry.TREE_DECORATOR_TYPE.get(
								new Identifier(dynamicx.get("type").asString().orElseThrow(RuntimeException::new))
						).method_23472(dynamicx)
				),
				dynamic.get("base_length").asInt(0),
				dynamic.get("length_random").asInt(0)
		);
	}

	public static class Builder extends TreeFeatureConfig.Builder {
		private List<TreeDecorator> decorators = Lists.newArrayList();
		private int baseLength;
		private int lengthRandom;

		public Builder(BlockStateProvider trunk, BlockStateProvider leaves) {
			super(trunk, leaves);
		}

		public Builder baseLength(int length) {
			this.baseLength = length;
			return this;
		}

		public Builder lengthRandom(int variance) {
			this.lengthRandom = variance;
			return this;
		}

		@Override
		public FallenLogFeatureConfig build() {
			return new FallenLogFeatureConfig(this.trunkProvider, this.leavesProvider, this.decorators, this.baseLength, this.lengthRandom);
		}
	}
}
