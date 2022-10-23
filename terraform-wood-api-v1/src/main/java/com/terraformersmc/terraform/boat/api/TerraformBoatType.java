package com.terraformersmc.terraform.boat.api;

import com.terraformersmc.terraform.boat.impl.TerraformBoatTypeImpl;
import com.terraformersmc.terraform.boat.impl.entity.TerraformBoatEntity;
import com.terraformersmc.terraform.boat.impl.entity.TerraformChestBoatEntity;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

/**
 * An interface representing a Terraform boat.
 */
public interface TerraformBoatType {
	/**
	 * {@return the {@linkplain net.minecraft.entity.vehicle.BoatEntity#getPickBlockStack() pick stack} and {@linkplain Item item} dropped when the {@linkplain TerraformBoatEntity boat entity} is broken}
	 */
	Item getItem();

	/**
	 * {@return the {@linkplain net.minecraft.entity.vehicle.BoatEntity#getPickBlockStack() pick stack} and {@linkplain Item item} dropped when the {@linkplain TerraformChestBoatEntity chest boat entity} is broken}
	 */
	Item getChestItem();

	/**
	 * {@return the planks {@linkplain Item item} dropped when the {@linkplain TerraformBoatEntity boat entity} or {@linkplain TerraformChestBoatEntity chest boat entity} is destroyed into planks and sticks}
	 */
	default Item getPlanks() {
		return Items.OAK_PLANKS;
	}

	/**
	 * A builder for {@linkplain TerraformBoatType Terraform boat types}.
	 * 
	 * <p>To build a Terraform boat type:
	 * 
	 * <pre>{@code
	 *     TerraformBoatType boat = new TerraformBoatType.Builder()
	 *         .item(ExampleModItems.MAHOGANY_BOAT)
	 *         .build();
	 * }</pre>
	 */
	public static class Builder {
		private Item item;
		private Item chestItem;
		private Item planks = Items.OAK_PLANKS;

		public TerraformBoatType build() {
			return new TerraformBoatTypeImpl(this.item, this.chestItem, this.planks);
		}

		/**
		 * @see TerraformBoatType#getItem
		 */
		public Builder item(Item item) {
			this.item = item;
			return this;
		}

		/**
		 * @see TerraformBoatType#getChestItem
		 */
		public Builder chestItem(Item chestItem) {
			this.chestItem = chestItem;
			return this;
		}

		/**
		 * @see TerraformBoatType#getPlanks
		 */
		public Builder planks(Item planks) {
			this.planks = planks;
			return this;
		}
	}
}
