package com.terraformersmc.terraform.leaves.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import com.terraformersmc.terraform.leaves.impl.data.TerraformExtendedDistanceFix;
import com.terraformersmc.terraform.leaves.impl.data.TerraformLeavesDfu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiFunction;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

@Mixin(DataFixers.class)
public class MixinDataFixers {
	/*
	 * The following provides a mechanism for mods to register their leaves IDs early enough for the DFU.
	 * This is required in order for Terraform extended leaves to be upgraded from <1.21.5 to >=1.21.5.
	 * Extended leaves not registered in this manner may decay after upgrading.
	 */
	@Inject(method = "createFixerUpper", at = @At("HEAD"))
	private static void create(CallbackInfoReturnable<DataFixerBuilder.Result> cir) {
		TerraformLeavesDfu.init();
	}

	/*
	 * This fix updates Terraform API ExtendedLeaves from a globally expanded DISTANCE property to the standard
	 * DISTANCE property with an auxiliary EXTENDED_DISTANCE property.
	 */
	@WrapOperation(method = "addFixers",
			slice = @Slice(
					from = @At(value = "NEW", target = "net/minecraft/util/datafix/fixes/EntityFallDistanceFloatToDoubleFix")
			),
			at = @At(value = "INVOKE", target = "Lcom/mojang/datafixers/DataFixerBuilder;addSchema(ILjava/util/function/BiFunction;)Lcom/mojang/datafixers/schemas/Schema;", ordinal = 0)
	)
	@SuppressWarnings("unused")
	private static Schema terraform$injectExtendedDistanceFix(DataFixerBuilder builder, int version, BiFunction<Integer, Schema, Schema> factory, Operation<Schema> original) {
		Schema extendedDistanceSchema = builder.addSchema(4304, NamespacedSchema::new);
		builder.addFixer(new TerraformExtendedDistanceFix(extendedDistanceSchema, false));

		return original.call(builder, version, factory);
	}
}
