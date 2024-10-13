package com.terraformersmc.terraform.wood.test;

import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import com.terraformersmc.terraform.sign.api.block.TerraformHangingSignBlock;
import com.terraformersmc.terraform.sign.api.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.api.block.TerraformWallHangingSignBlock;
import com.terraformersmc.terraform.sign.api.block.TerraformWallSignBlock;

import com.terraformersmc.terraform.wood.api.block.PillarLogHelper;
import com.terraformersmc.terraform.wood.test.command.SpawnBoatsCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class TerraformWoodTest implements ModInitializer {
	private static final String MOD_ID = "terraform";

	private static final Identifier CUSTOM_LOG_ID = Identifier.of(MOD_ID, "custom_log");
	private static final Identifier CUSTOM_PLANKS_ID = Identifier.of(MOD_ID, "custom_planks");

	public static final Identifier CUSTOM_BOATS_ID = Identifier.of(MOD_ID, "custom");

	protected static final Identifier SIGN_TEXTURE_ID = Identifier.of(MOD_ID, "entity/signs/custom");
	protected static final Identifier HANGING_SIGN_TEXTURE_ID = Identifier.of(MOD_ID, "entity/signs/hanging/custom");
	protected static final Identifier HANGING_SIGN_GUI_TEXTURE_ID = Identifier.of(MOD_ID, "textures/gui/hanging_signs/custom");
	private static final Identifier CUSTOM_SIGN_ID = Identifier.of(MOD_ID, "custom_sign");
	private static final Identifier CUSTOM_WALL_SIGN_ID = Identifier.of(MOD_ID, "custom_wall_sign");
	private static final Identifier CUSTOM_HANGING_SIGN_ID = Identifier.of(MOD_ID, "custom_hanging_sign");
	private static final Identifier CUSTOM_WALL_HANGING_SIGN_ID = Identifier.of(MOD_ID, "custom_wall_hanging_sign");

	public static BoatItem customBoatItem;
	public static BoatItem customChestBoatItem;
	public static BoatItem customRaftItem;
	public static BoatItem customChestRaftItem;

	@Override
	public void onInitialize() {
		Block customLog = new PillarBlock(PillarLogHelper.settings(MapColor.RED, MapColor.BLUE).registryKey(RegistryKey.of(RegistryKeys.BLOCK, CUSTOM_LOG_ID)));
		Block customPlanks = new Block(AbstractBlock.Settings.create().mapColor(MapColor.RED).registryKey(RegistryKey.of(RegistryKeys.BLOCK, CUSTOM_PLANKS_ID)));

		BlockItem customLogItem = new BlockItem(customLog, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, CUSTOM_LOG_ID)));
		BlockItem customPlanksItem = new BlockItem(customPlanks, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, CUSTOM_PLANKS_ID)));

		// Boats
		customBoatItem = TerraformBoatItemHelper.registerBoatItem(CUSTOM_BOATS_ID, false);
		customChestBoatItem = TerraformBoatItemHelper.registerBoatItem(CUSTOM_BOATS_ID, true);

		customRaftItem = TerraformBoatItemHelper.registerBoatItem(CUSTOM_BOATS_ID, false, true);
		customChestRaftItem = TerraformBoatItemHelper.registerBoatItem(CUSTOM_BOATS_ID, true, true);

		// Signs
		Block sign = new TerraformSignBlock(SIGN_TEXTURE_ID, AbstractBlock.Settings.copy(Blocks.OAK_SIGN).sounds(BlockSoundGroup.ANVIL).registryKey(RegistryKey.of(RegistryKeys.BLOCK, CUSTOM_SIGN_ID)));
		Registry.register(Registries.BLOCK, CUSTOM_SIGN_ID, sign);

		Block wallSign = new TerraformWallSignBlock(SIGN_TEXTURE_ID, AbstractBlock.Settings.copy(Blocks.OAK_WALL_SIGN).sounds(BlockSoundGroup.SAND).lootTable(sign.getLootTableKey()).registryKey(RegistryKey.of(RegistryKeys.BLOCK, CUSTOM_WALL_SIGN_ID)));
		Registry.register(Registries.BLOCK, CUSTOM_WALL_SIGN_ID, wallSign);

		Block hangingSign = new TerraformHangingSignBlock(HANGING_SIGN_TEXTURE_ID, HANGING_SIGN_GUI_TEXTURE_ID, AbstractBlock.Settings.copy(Blocks.OAK_HANGING_SIGN).sounds(BlockSoundGroup.WOOL).registryKey(RegistryKey.of(RegistryKeys.BLOCK, CUSTOM_HANGING_SIGN_ID)));
		Registry.register(Registries.BLOCK, CUSTOM_HANGING_SIGN_ID, hangingSign);

		Block wallHangingSign = new TerraformWallHangingSignBlock(HANGING_SIGN_TEXTURE_ID, HANGING_SIGN_GUI_TEXTURE_ID, AbstractBlock.Settings.copy(Blocks.OAK_WALL_HANGING_SIGN).sounds(BlockSoundGroup.SCULK_SENSOR).lootTable(hangingSign.getLootTableKey()).registryKey(RegistryKey.of(RegistryKeys.BLOCK, CUSTOM_WALL_HANGING_SIGN_ID)));
		Registry.register(Registries.BLOCK, CUSTOM_WALL_HANGING_SIGN_ID, wallHangingSign);

		SignItem signItem = new SignItem(sign, wallSign, new Item.Settings().maxCount(16).registryKey(RegistryKey.of(RegistryKeys.ITEM, CUSTOM_SIGN_ID)));
		HangingSignItem hangingSignItem = new HangingSignItem(hangingSign, wallHangingSign, new Item.Settings().maxCount(16).registryKey(RegistryKey.of(RegistryKeys.ITEM, CUSTOM_HANGING_SIGN_ID)));

		// Register
		customLogItem.appendBlocks(Item.BLOCK_ITEMS, customLogItem);
		customPlanksItem.appendBlocks(Item.BLOCK_ITEMS, customPlanksItem);
		signItem.appendBlocks(Item.BLOCK_ITEMS, signItem);
		hangingSignItem.appendBlocks(Item.BLOCK_ITEMS, hangingSignItem);

		Registry.register(Registries.BLOCK, CUSTOM_LOG_ID, customLog);
		Registry.register(Registries.BLOCK, CUSTOM_PLANKS_ID, customPlanks);

		Registry.register(Registries.ITEM, CUSTOM_LOG_ID, customLogItem);
		Registry.register(Registries.ITEM, CUSTOM_PLANKS_ID, customPlanksItem);
		Registry.register(Registries.ITEM, CUSTOM_SIGN_ID, signItem);
		Registry.register(Registries.ITEM, CUSTOM_HANGING_SIGN_ID, hangingSignItem);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
			entries.addAfter(Items.CHERRY_BUTTON, customLogItem, customPlanksItem);
		});

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
			entries.addAfter(Items.CHERRY_HANGING_SIGN, signItem, hangingSignItem);
		});

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
			entries.addAfter(Items.CHERRY_CHEST_BOAT, customBoatItem, customChestBoatItem, customRaftItem, customChestRaftItem);
		});

		// Utility commands
		CommandRegistrationCallback.EVENT.register(
				(dispatcher, registryAccess, environment) -> SpawnBoatsCommand.register(dispatcher)
		);
	}
}
