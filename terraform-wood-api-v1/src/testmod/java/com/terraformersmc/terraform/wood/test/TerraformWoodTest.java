package com.terraformersmc.terraform.wood.test;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import com.terraformersmc.terraform.sign.block.TerraformHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.HangingSignItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.SignItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TerraformWoodTest implements ModInitializer {
	private static final String MOD_ID = "terraform";

	private static final Identifier CUSTOM_PLANKS_ID = new Identifier(MOD_ID, "custom_planks");

	protected static final Identifier CUSTOM_BOAT_ID = new Identifier(MOD_ID, "custom_boat");
	private static final Identifier CUSTOM_CHEST_BOAT_ID = new Identifier(MOD_ID, "custom_chest_boat");
	protected static final Identifier CUSTOM_RAFT_ID = new Identifier(MOD_ID, "custom_raft");
	private static final Identifier CUSTOM_CHEST_RAFT_ID = new Identifier(MOD_ID, "custom_chest_raft");

	protected static final Identifier SIGN_TEXTURE_ID = new Identifier(MOD_ID, "entity/sign/custom");
	private static final Identifier CUSTOM_SIGN_ID = new Identifier(MOD_ID, "custom_sign");
	private static final Identifier CUSTOM_WALL_SIGN_ID = new Identifier(MOD_ID, "custom_wall_sign");
	private static final Identifier CUSTOM_HANGING_SIGN_ID = new Identifier(MOD_ID, "custom_hanging_sign");
	private static final Identifier CUSTOM_WALL_HANGING_SIGN_ID = new Identifier(MOD_ID, "custom_wall_hanging_sign");

	private static TerraformBoatType boat;
	private static TerraformBoatType raft;

	@Override
	public void onInitialize() {
		Item planks = new Item(new Item.Settings());

		// Boats
		Item boatItem = TerraformBoatItemHelper.registerBoatItem(CUSTOM_BOAT_ID, () -> boat, false);
		Item chestBoatItem = TerraformBoatItemHelper.registerBoatItem(CUSTOM_CHEST_BOAT_ID, () -> boat, true);

		Item raftItem = TerraformBoatItemHelper.registerBoatItem(CUSTOM_RAFT_ID, () -> raft, false);
		Item chestRaftItem = TerraformBoatItemHelper.registerBoatItem(CUSTOM_CHEST_RAFT_ID, () -> raft, true);

		boat = new TerraformBoatType.Builder()
			.item(boatItem)
			.chestItem(chestBoatItem)
			.planks(planks)
			.build();

		raft = new TerraformBoatType.Builder()
			.raft()
			.item(raftItem)
			.chestItem(chestRaftItem)
			.planks(planks)
			.build();

		// Signs
		Block sign = new TerraformSignBlock(SIGN_TEXTURE_ID, FabricBlockSettings.copyOf(Blocks.OAK_SIGN));
		Block wallSign = new TerraformWallSignBlock(SIGN_TEXTURE_ID, FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN));

		Block hangingSign = new TerraformHangingSignBlock(SIGN_TEXTURE_ID, FabricBlockSettings.copyOf(Blocks.OAK_HANGING_SIGN));
		Block wallHangingSign = new TerraformWallHangingSignBlock(SIGN_TEXTURE_ID, FabricBlockSettings.copyOf(Blocks.OAK_WALL_HANGING_SIGN));

		Item signItem = new SignItem(new Item.Settings().maxCount(16), sign, wallSign);
		Item hangingSignItem = new HangingSignItem(hangingSign, wallHangingSign, new Item.Settings().maxCount(16));

		// Register
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, CUSTOM_BOAT_ID, boat);
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, CUSTOM_RAFT_ID, raft);

		Registry.register(Registry.BLOCK, CUSTOM_SIGN_ID, sign);
		Registry.register(Registry.BLOCK, CUSTOM_WALL_SIGN_ID, wallSign);
		Registry.register(Registry.BLOCK, CUSTOM_HANGING_SIGN_ID, hangingSign);
		Registry.register(Registry.BLOCK, CUSTOM_WALL_HANGING_SIGN_ID, wallHangingSign);

		Registry.register(Registry.ITEM, CUSTOM_PLANKS_ID, planks);
		Registry.register(Registry.ITEM, CUSTOM_SIGN_ID, signItem);
		Registry.register(Registry.ITEM, CUSTOM_HANGING_SIGN_ID, hangingSignItem);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
			entries.addAfter(Items.MANGROVE_PLANKS, planks);
			entries.addAfter(Items.MANGROVE_CHEST_BOAT, boatItem, chestBoatItem, raftItem, chestRaftItem);
			entries.addAfter(Items.MANGROVE_HANGING_SIGN, signItem, hangingSignItem);
		});
	}
}
