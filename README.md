# MEG Client Mod

A client-side Minecraft mod that optimizes [Model Engine](https://mythiccraft.io/index.php?resources/model-engine%E2%80%94ultimate-entity-model-manager-1-19-4-1-21-11.1213/) animation packets by replacing per-entity metadata updates with a single bulk payload using half-precision encoding, significantly reducing bandwidth usage for complex models.

## Supported Platforms

- Fabric
- NeoForge

**Minecraft:** 1.21.x and 26.x

> Minecraft 26.1 is the first unobfuscated release and requires Java 25; the 1.21.x
> builds remain on Java 21. A single source tree targets every version via
> [Stonecutter](https://stonecutter.kikugie.dev/) + `loom-back-compat`, which selects the
> right Loom variant (remapped for 1.21.x, plain for 26.x) per version.

## How It Works

When installed, the mod registers the `modelengine:bulk_data` plugin channel. The Model Engine server plugin detects this (per-player) and sends optimized bulk animation packets instead of individual entity-data updates. Transformation data (translation, rotation, scale) is encoded as half-precision floats, cutting payload size roughly in half compared to vanilla packets. The client decodes the bulk payload and re-applies each bone as vanilla `Display` entity data.

Players without the mod receive standard packets as usual, no compatibility issues.

## Installation

Drop the mod JAR into your `mods/` folder (Fabric or NeoForge). No configuration needed. The server must be running Model Engine 4.

## Building

Requires JDK 21 **and** JDK 25 installed (26.x targets need Java 25). The Gradle daemon
runs on Java 25; Gradle provisions/uses JDK 21 for the 1.21.x targets via toolchains.

The build ships **one jar per era per loader** (four jars): `1.21.x` and `26.x`, for Fabric and
NeoForge. One jar covers a whole line because within a mapping era the runtime names are stable
(Fabric remaps to intermediary; Mojang official names are aligned) and generic-only changes erase
at the bytecode level. A single jar cannot span the 1.21 to 26.x boundary (obfuscated vs
unobfuscated are different mapping namespaces), so each era is built separately. The NeoForge
1.21.x build targets 1.21.11 (the latest 1.x); older 1.21 patches used different official names
and are covered by Fabric only.

Build a single node:

```bash
./gradlew ":26.x-fabric:build"
./gradlew ":1.21.x-neoforge:build"
```

Build every version + loader at once:

```bash
./gradlew buildAll
```

> The first `buildAll` decompiles Minecraft for each NeoForge target and is memory-heavy;
> if it fails under parallel execution, run `./gradlew buildAll --no-parallel` (or build
> per-node). Subsequent runs are cached.

Output jars land in `versions/<node>/build/libs/`. The active dev version is set in
[stonecutter.gradle.kts](stonecutter.gradle.kts) (`stonecutter active "..."`); the version
matrix is declared in [settings.gradle.kts](settings.gradle.kts) and per-version
dependencies in [stonecutter.properties.toml](stonecutter.properties.toml).

## Important Notice

The custom payload protocol (`modelengine:bulk_data`) is designed exclusively for use with [Model Engine](https://mythiccraft.io/index.php?resources/model-engine%E2%80%94ultimate-entity-model-manager-1-19-4-1-21-11.1213/). Third-party model plugins that send packets over this channel without proper implementation may cause visual glitches, desyncs, or client crashes. If you experience issues while using a non-Model Engine plugin, disable this mod and report the problem to that plugin's author, not here.

## License

[AGPL-3.0](LICENSE.txt) - Copyright (c) 2026 Taiyouh
