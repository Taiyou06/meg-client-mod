plugins {
    id("dev.kikugie.stonecutter")
}

stonecutter active "26.x-fabric"

tasks.register("buildAll") {
    group = "stonecutter"
    description = "Builds the mod for every configured Minecraft version and loader."
    dependsOn(stonecutter.tasks.named("build"))
}

stonecutter parameters {
    val (version, loader) = current.project.split('-', limit = 2)
    properties { tags(version, loader) }
    constants { match(loader, "fabric", "neoforge") }
    swaps["mod_id"] = "\"${properties.get<String>("mod.id")}\";"

    replacements {
        // Mojang renamed ResourceLocation -> Identifier at 1.21.11.
        string(current.parsed >= "1.21.11") {
            replace("ResourceLocation", "Identifier")
        }
    }
}
