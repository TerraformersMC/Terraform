package com.terraformersmc.terraform.wood.test;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TerraformWoodTest implements ModInitializer {
	private static final String MOD_ID = "terraform";

	private static final Identifier CUSTOM_PLANKS_ID = new Identifier(MOD_ID, "custom_planks");

	private static final Identifier CUSTOM_BOAT_ID = new Identifier(MOD_ID, "custom_boat");
	private static final Identifier CUSTOM_CHEST_BOAT_ID = new Identifier(MOD_ID, "custom_chest_boat");
	static final Identifier CUSTOM_ID = new Identifier(MOD_ID, "custom");

	static final Identifier SIGN_TEXTURE_ID = new Identifier(MOD_ID, "entity/sign/custom");
	private static final Identifier CUSTOM_SIGN_ID = new Identifier(MOD_ID, "custom_sign");
	private static final Identifier CUSTOM_WALL_SIGN_ID = new Identifier(MOD_ID, "custom_wall_sign");

	private static TerraformBoatType boat;

	@Override
	public void onInitialize() {
		Item planks = new Item(new Item.Settings().group(ItemGroup.BUILDING_BLOCKS));

		// Boats
		Item item = TerraformBoatItemHelper.registerBoatItem(CUSTOM_BOAT_ID, () -> boat, false);
		Item chestItem = TerraformBoatItemHelper.registerBoatItem(CUSTOM_CHEST_BOAT_ID, () -> boat, true);

		boat = new TerraformBoatType.Builder()
			.item(item)
			.chestItem(chestItem)
			.planks(planks)
			.build();

		// Signs
		Block sign = new TerraformSignBlock(SIGN_TEXTURE_ID, FabricBlockSettings.copyOf(Blocks.OAK_SIGN));
		Block wallSign = new TerraformWallSignBlock(SIGN_TEXTURE_ID, FabricBlockSettings.copyOf(Blocks.OAK_SIGN));

		Item signItem = new SignItem(new Item.Settings().maxCount(16).group(ItemGroup.DECORATIONS), sign, wallSign);

		// Register
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, CUSTOM_ID, boat);

		Registry.register(Registry.BLOCK, CUSTOM_SIGN_ID, sign);
		Registry.register(Registry.BLOCK, CUSTOM_WALL_SIGN_ID, wallSign);

		Registry.register(Registry.ITEM, CUSTOM_PLANKS_ID, planks);
		Registry.register(Registry.ITEM, CUSTOM_SIGN_ID, signItem);
	}
}
