# Terraform

Terraform is a library mod that is bundled with other Terraformers mods. It provides world generation, math, and other utilities for creating our mods!

For further information about each individual module see the README.md file in in each module root. 

### Usage

To add a Terraform module as a dependency simply add the terraformers' maven as a repository for dependencies. This can be done as follows:

```java
repositories {
    maven {
      name = 'TerraformersMC'
      url = 'https://maven.terraformersmc.com/'
    }
}
```

Then add the corresponding Terraform module as a dependency. For example, the following line adds the wood api as a dependency.

```java
dependencies {
    modImplementation "com.terraformersmc.terraform-api:terraform-wood-api-v1:1.0.1"
}
```

For version information and specific module names use [the maven navigator](https://maven.terraformersmc.com/releases/).

### Versioning Information

When developing Terraform modules we try to keep a very strict versioning system to make using the api easy for mod developers. Like all Fabric/Quilt mods and libraries, Terraform utilizes the [semver](https://semver.org/) system of versioning.

Whenever a new version of the game is released we update each Terraform module and indicate that it is for a new version of the game by bumping the major version value (`1.x.x` to `2.x.x`, etc.). When developers update game versions they should check this repository to see which new major version corresponds to the new game version.

Whenever new features are added to one of our modules in a way that expands the visible surface of the api we indicate support for those new features by bumping the minor version value (`x.1.x` to `x.2.x`, etc.). It should always be safe for developers to update a Terraform module to the newest minor version value because changes in the minor version should only ever expand the visible surface of the api.

If implementations change, or features are added that don't change the visible surface of the api, those changes are indicated by bumping the patch version value (`x.x.1` to `x.x.2`, etc.). It should always be safe for developers to update a Terraform module to the newest patch version value because changes in the patch version should never modify the visible surface of the api.

##### Breaking Changes

Occasionally, we need to release changes to a module that break the existing visible surface of the api. Examples of this include the removal of a deprecated api, the removal of parameters in a method, etc. Basically, anything that causes a developer to have to do work in order to update their usage of a Terraform module. We try to make these changes as infrequent as possible by preferring deprecation annotations and the addition of new apis over the flat out removal of existing apis, but there always comes a time when it becomes burdensome to continue to move forward a deprecated api and it eventually has to be removed. Whenever this happens, and there are breaking changes to a module, either the major version of the module is bumped (`1.x.x` to `2.x.x`, etc.), or a new module is created bumping the version in the module name (`terraform-wood-api-v1` to `terraform-wood-api-v2`, etc.). Either way, we try to do everything in our power to make sure developers know about breaking changes before they happen, and that developers have a long time to prepare for breaking changes.

##### Experimental Modules

When developing a module, occasionally we need to release an experimental version of an api in order to get feedback from the community about its usage, or to use it in bleeding-edge updates to one of our mods. When this occurs, modules will be labeled as version zero (`terraform-wood-api-v0`) and the api will have deprecation annotations along with documentation explaining that the module is still experimental and could change drastically between versions. Developers use these modules at their own risk, and we make no guarantees about the continuity of the visible api surface between versions, nor the continued maintenance of the module.
