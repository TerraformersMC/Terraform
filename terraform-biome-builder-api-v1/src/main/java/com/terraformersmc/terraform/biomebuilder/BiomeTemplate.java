package com.terraformersmc.terraform.biomebuilder;

public final class BiomeTemplate {
	private final TerraformBiomeBuilder builder;

	public BiomeTemplate(TerraformBiomeBuilder builder) {
		this.builder = builder;
		builder.markTemplate();
	}

	public TerraformBiomeBuilder builder() {
		return new TerraformBiomeBuilder(this.builder);
	}
}
