pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/") { name = "FabricMC" }
        maven("https://maven.neoforged.net/releases/") { name = "NeoForged" }
        maven("https://maven.kikugie.dev/releases") { name = "KikuGie Releases" }
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9.6"
    // Applies the right Loom variant per version so obfuscated 1.21.x and unobfuscated 26.x share one build.
    id("dev.kikugie.loom-back-compat") version "0.3"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

stonecutter {
    create(rootProject) {
        fun fabric(node: String, mc: String) = version("$node-fabric", mc).buildscript("build.fabric.gradle.kts")
        fun neoforge(node: String, mc: String) = version("$node-neoforge", mc).buildscript("build.neoforge.gradle.kts")

        // One jar per era per loader, built against the latest patch of each line.
        fabric("1.21.x", "1.21.11")
        fabric("26.x", "26.1.2")
        neoforge("1.21.x", "1.21.11")
        neoforge("26.x", "26.1.2")

        vcsVersion = "26.x-fabric"
    }
}

rootProject.name = "meg-client-mod"
