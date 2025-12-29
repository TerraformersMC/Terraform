package com.terraformersmc.terraform.wood.test;

import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import com.terraformersmc.terraform.sign.api.block.TerraformSignBlockHelper;
import com.terraformersmc.terraform.wood.api.block.PillarLogHelper;
import com.terraformersmc.terraform.wood.test.command.SpawnBoatsCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;

public class TerraformWoodTest implements ModInitializer {
	private static final String MOD_ID = "terraform";

	private static final Identifier CUSTOM_LOG_ID = Identifier.fromNamespaceAndPath(MOD_ID, "custom_log");
	private static final Identifier CUSTOM_PLANKS_ID = Identifier.fromNamespaceAndPath(MOD_ID, "custom_planks");

	public static final Identifier CUSTOM_BOATS_ID = Identifier.fromNamespaceAndPath(MOD_ID, "custom");

	private static final Identifier CUSTOM_SIGN_ID = Identifier.fromNamespaceAndPath(MOD_ID, "custom_sign");
	private static final Identifier CUSTOM_WALL_SIGN_ID = Identifier.fromNamespaceAndPath(MOD_ID, "custom_wall_sign");
	private static final Identifier CUSTOM_HANGING_SIGN_ID = Identifier.fromNamespaceAndPath(MOD_ID, "custom_hanging_sign");
	private static final Identifier CUSTOM_WALL_HANGING_SIGN_ID = Identifier.fromNamespaceAndPath(MOD_ID, "custom_wall_hanging_sign");

	public static BoatItem customBoatItem;
	public static BoatItem customChestBoatItem;
	public static BoatItem customRaftItem;
	public static BoatItem customChestRaftItem;

	@Override
	public void onInitialize() {
		Block customLog = new RotatedPillarBlock(PillarLogHelper.createProperties(MapColor.COLOR_RED, MapColor.COLOR_BLUE).setId(ResourceKey.create(Registries.BLOCK, CUSTOM_LOG_ID)));
		Block customPlanks = new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).setId(ResourceKey.create(Registries.BLOCK, CUSTOM_PLANKS_ID)));

		BlockItem customLogItem = new BlockItem(customLog, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, CUSTOM_LOG_ID)).useBlockDescriptionPrefix());
		BlockItem customPlanksItem = new BlockItem(customPlanks, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, CUSTOM_PLANKS_ID)).useBlockDescriptionPrefix());

		// Boats
		customBoatItem = TerraformBoatItemHelper.registerBoatItem(CUSTOM_BOATS_ID, false);
		customChestBoatItem = TerraformBoatItemHelper.registerBoatItem(CUSTOM_BOATS_ID, true);

		customRaftItem = TerraformBoatItemHelper.registerBoatItem(CUSTOM_BOATS_ID, false, true);
		customChestRaftItem = TerraformBoatItemHelper.registerBoatItem(CUSTOM_BOATS_ID, true, true);

		// Signs
		WoodType customSignWoodType = TerraformSignBlockHelper.registerDefaultWoodType(Identifier.fromNamespaceAndPath(MOD_ID, "custom"));

		StandingSignBlock sign = TerraformSignBlockHelper.registerSignBlock(CUSTOM_SIGN_ID, properties -> new StandingSignBlock(customSignWoodType, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).sound(SoundType.ANVIL));
		WallSignBlock wallSign = TerraformSignBlockHelper.registerSignBlock(CUSTOM_WALL_SIGN_ID, properties -> new WallSignBlock(customSignWoodType, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).sound(SoundType.SAND).overrideLootTable(sign.getLootTable()));
		CeilingHangingSignBlock hangingSign = TerraformSignBlockHelper.registerSignBlock(CUSTOM_HANGING_SIGN_ID, properties -> new CeilingHangingSignBlock(customSignWoodType, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).sound(SoundType.WOOL));
		WallHangingSignBlock wallHangingSign = TerraformSignBlockHelper.registerSignBlock(CUSTOM_WALL_HANGING_SIGN_ID, properties -> new WallHangingSignBlock(customSignWoodType, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).sound(SoundType.SCULK_SENSOR).overrideLootTable(hangingSign.getLootTable()));

		SignItem signItem = new SignItem(sign, wallSign, new Item.Properties().stacksTo(16).setId(ResourceKey.create(Registries.ITEM, CUSTOM_SIGN_ID)).useBlockDescriptionPrefix());
		HangingSignItem hangingSignItem = new HangingSignItem(hangingSign, wallHangingSign, new Item.Properties().stacksTo(16).setId(ResourceKey.create(Registries.ITEM, CUSTOM_HANGING_SIGN_ID)).useBlockDescriptionPrefix());

		// Register
		customLogItem.registerBlocks(Item.BY_BLOCK, customLogItem);
		customPlanksItem.registerBlocks(Item.BY_BLOCK, customPlanksItem);
		signItem.registerBlocks(Item.BY_BLOCK, signItem);
		hangingSignItem.registerBlocks(Item.BY_BLOCK, hangingSignItem);

		Registry.register(BuiltInRegistries.BLOCK, CUSTOM_LOG_ID, customLog);
		Registry.register(BuiltInRegistries.BLOCK, CUSTOM_PLANKS_ID, customPlanks);

		Registry.register(BuiltInRegistries.ITEM, CUSTOM_LOG_ID, customLogItem);
		Registry.register(BuiltInRegistries.ITEM, CUSTOM_PLANKS_ID, customPlanksItem);
		Registry.register(BuiltInRegistries.ITEM, CUSTOM_SIGN_ID, signItem);
		Registry.register(BuiltInRegistries.ITEM, CUSTOM_HANGING_SIGN_ID, hangingSignItem);

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
			entries.addAfter(Items.CHERRY_BUTTON, customLogItem, customPlanksItem);
		});

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> {
			entries.addAfter(Items.CHERRY_HANGING_SIGN, signItem, hangingSignItem);
		});

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
			entries.addAfter(Items.CHERRY_CHEST_BOAT, customBoatItem, customChestBoatItem, customRaftItem, customChestRaftItem);
		});

		// Utility commands
		CommandRegistrationCallback.EVENT.register(
				(dispatcher, registryAccess, environment) -> SpawnBoatsCommand.register(dispatcher)
		);
	}
}
