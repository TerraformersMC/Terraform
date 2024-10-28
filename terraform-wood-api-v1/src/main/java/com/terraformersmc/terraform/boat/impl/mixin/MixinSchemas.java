package com.terraformersmc.terraform.boat.impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import com.terraformersmc.terraform.boat.impl.data.TerraformAddBoatsSchema;
import com.terraformersmc.terraform.boat.impl.data.TerraformBoatDfu;
import com.terraformersmc.terraform.boat.impl.data.TerraformBoatSplitFix;
import com.terraformersmc.terraform.boat.impl.data.TerraformBoatSplitSchema;
import net.minecraft.datafixer.Schemas;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.fix.ChoiceTypesFix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiFunction;

@Mixin(Schemas.class)
public class MixinSchemas {
	/*
	 * The following provides a mechanism for mods to register their boat IDs early enough for the DFU.
	 * This is required in order for Terraform boats to be upgraded from <1.21.2 to after >=1.21.2.
	 * Boats not registered in this manner will be converted to oak wood type at upgrade.
	 */
	@Inject(method = "create", at = @At("HEAD"))
	private static void create(CallbackInfoReturnable<DataFixerBuilder.Result> cir) {
		TerraformBoatDfu.init();
	}

	/*
	 * This fix adds the legacy Terraform API place-holder boat entities to the DFU as of just before 1.20.
	 * This means worlds with Terraform-based boats must be upgraded to 1.20 or 1.21 before 1.21.2+.
	 */
	@WrapOperation(method = "build",
			slice = @Slice(
					from = @At(value = "NEW", target = "net/minecraft/datafixer/fix/ChunkDeleteLightFix")
			),
			at = @At(value = "INVOKE", target = "Lcom/mojang/datafixers/DataFixerBuilder;addSchema(ILjava/util/function/BiFunction;)Lcom/mojang/datafixers/schemas/Schema;", ordinal = 0)
	)
	@SuppressWarnings("unused")
	private static Schema terraform$injectAddBoatsFix(DataFixerBuilder builder, int version, BiFunction<Integer, Schema, Schema> factory, Operation<Schema> original) {
		Schema addBoatsSchema = builder.addSchema(3454, TerraformAddBoatsSchema::new);
		builder.addFixer(new ChoiceTypesFix(addBoatsSchema, "Add Terraform Boats", TypeReferences.ENTITY));

		return original.call(builder, version, factory);
	}

	/*
	 * This fix updates Terraform API place-holder boat entities, converting them to the new split entities,
	 * and simultaneously removing the old Terraform API system of using fake boat entities.
	 */
	@WrapOperation(method = "build",
			slice = @Slice(
					from = @At(value = "NEW", target = "net/minecraft/datafixer/fix/FireResistantToDamageResistantComponentFix")
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
