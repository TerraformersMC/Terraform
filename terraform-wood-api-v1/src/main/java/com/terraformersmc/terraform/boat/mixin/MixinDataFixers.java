package com.terraformersmc.terraform.boat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import com.terraformersmc.terraform.boat.impl.data.TerraformAddBoatsSchema;
import com.terraformersmc.terraform.boat.impl.data.TerraformBoatDfu;
import com.terraformersmc.terraform.boat.impl.data.TerraformBoatSplitFix;
import com.terraformersmc.terraform.boat.impl.data.TerraformBoatSplitSchema;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiFunction;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.AddNewChoices;
import net.minecraft.util.datafix.fixes.References;

@Mixin(DataFixers.class)
public class MixinDataFixers {
	/*
	 * The following provides a mechanism for mods to register their boat IDs early enough for the DFU.
	 * This is required in order for Terraform boats to be upgraded from <1.21.2 to >=1.21.2.
	 * Boats not registered in this manner will be converted to oak wood type at upgrade.
	 */
	@Inject(method = "addFixers", at = @At("TAIL"))
	private static void create(CallbackInfo ci) {
		TerraformBoatDfu.init();
	}

	/*
	 * This fix adds the legacy Terraform API place-holder boat entities to the DFU as of just before 1.20.
	 * This means saves with Terraform-based boats must be upgraded to 1.20 or 1.21 before 1.21.2+.
	 */
	@WrapOperation(method = "addFixers",
			slice = @Slice(
					from = @At(value = "NEW", target = "net/minecraft/util/datafix/fixes/ChunkDeleteLightFix")
			),
			at = @At(value = "INVOKE", target = "Lcom/mojang/datafixers/DataFixerBuilder;addSchema(ILjava/util/function/BiFunction;)Lcom/mojang/datafixers/schemas/Schema;", ordinal = 0)
	)
	@SuppressWarnings("unused")
	private static Schema terraform$injectAddBoatsFix(DataFixerBuilder builder, int version, BiFunction<Integer, Schema, Schema> factory, Operation<Schema> original) {
		Schema addBoatsSchema = builder.addSchema(3454, TerraformAddBoatsSchema::new);
		builder.addFixer(new AddNewChoices(addBoatsSchema, "Add Terraform Boats", References.ENTITY));

		return original.call(builder, version, factory);
	}

	/*
	 * This fix updates Terraform API place-holder boat entities, converting them to the new split entities,
	 * and simultaneously removing the old Terraform API system of using fake boat entities.
	 */
	@WrapOperation(method = "addFixers",
			slice = @Slice(
					from = @At(value = "NEW", target = "net/minecraft/util/datafix/fixes/FireResistantToDamageResistantComponentFix")
			),
			at = @At(value = "INVOKE", target = "Lcom/mojang/datafixers/DataFixerBuilder;addSchema(ILjava/util/function/BiFunction;)Lcom/mojang/datafixers/schemas/Schema;", ordinal = 0)
	)
	@SuppressWarnings("unused")
	private static Schema terraform$injectBoatSplitFix(DataFixerBuilder builder, int version, BiFunction<Integer, Schema, Schema> factory, Operation<Schema> original) {
		Schema boatSplitSchema = builder.addSchema(4066, TerraformBoatSplitSchema::new);
		builder.addFixer(new TerraformBoatSplitFix(boatSplitSchema, true));

		return original.call(builder, version, factory);
	}
}
