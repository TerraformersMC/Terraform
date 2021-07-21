# Terraform Dirt API v1

The Terraform Dirt API allows the easy definition of new dirt types, including blocks for dirt, grass blocks, grass paths, podzol, and farmland. It's what Terrestria uses to ensure that Andisol behaves as players would expect.

## Usage

1. Bring `terraform-dirt-api-v1` into your workspace by adding the Terraformers Maven to your project, and add the appropriate dependency lines in Gradle.
2. Create a new `DirtBlocks` object containing all of the associated block types of a dirt type.
	* Note that you should probably take this as an opportunity to add custom grass, podzol, and farmland: passing `null`, vanilla, or shared blocks to the DirtBlocks constructor is currently not fully supported.
3. Add your dirt, grass blocks, and podzol to the `terraform:soil` block tag as appropriate.
4. Add your grass blocks to the `terraform:grass_blocks` block tag.
5. Add your farmland blocks to the `terraform:farmland` block tag.

## Functionality

Here's what the Terraform Dirt API does for you:

* Ensures that your dirt / grass block / grass path can be tilled into the appropriate farmland block
* Ensures that mega spruce trees replace your soil blocks with the appropriate podzol blocks
* Allows animals to spawn on custom grass blocks
* Allows crops to grow properly on custom farmland
* Allows sheep to eat your custom grass and turn it into the appropriate dirt block
* Ensures that custom farmland reverts to the appropriate dirt block
* Allows trees and other worldgen features to grow on custom soil
* Allows standard plant blocks like flowers and saplings to grow on custom soil
* Allows pumpkin stems, crops, and similar to be planted on top of your custom farmland
* Allows rabbits to eat carrots that are growing on custom farmland
* Allows custom grass spread
* Allows sugar cane to be planted on top of your soil blocks
* Finally, ensures that trees growing on custom soil revert the soil block to the appropriate dirt block
