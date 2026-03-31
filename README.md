# MEG Client Mod

A client-side Minecraft mod that optimizes [Model Engine](https://mythiccraft.io/index.php?resources/model-engine%E2%80%94ultimate-entity-model-manager-1-19-4-1-21-11.1213/) animation packets by replacing per-entity metadata updates with a single bulk payload using half-precision encoding, significantly reducing bandwidth usage for complex models.

## Supported Platforms

- Fabric
- Forge
- NeoForge

**Minecraft:** 1.19.4 - 1.21.x

## How It Works

When installed, the mod registers the `modelengine:bulk_data` plugin channel. The Model Engine server plugin detects this and sends optimized bulk animation packets instead of individual entity data updates. Transformation data (position, rotation, scale) is encoded as half-precision floats, cutting payload size roughly in half compared to vanilla packets.

Players without the mod receive standard packets as usual, no compatibility issues.

## Installation

Drop the mod JAR into your `mods/` (Fabric/NeoForge) or `mods/` (Forge) folder. No configuration needed. The server must be running Model Engine 4.

## Important Notice

The custom payload protocol (`modelengine:bulk_data`) is designed exclusively for use with [Model Engine](https://mythiccraft.io/index.php?resources/model-engine%E2%80%94ultimate-entity-model-manager-1-19-4-1-21-11.1213/). Third-party model plugins that send packets over this channel without proper implementation may cause visual glitches, desyncs, or client crashes. If you experience issues while using a non-Model Engine plugin, disable this mod and report the problem to that plugin's author, not here.

## License

[AGPL-3.0](LICENSE.txt) - Copyright (c) 2026 Taiyouh
