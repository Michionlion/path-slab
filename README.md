# Path Slab

Build smooth, runnable dirt paths with slabs that match vanilla dirt paths and respect resource packs.

## Features

- Dirt Path Slab (7/16 high) with a 15/16 double slab
- Vanilla dirt path texture references for full resource-pack compatibility
- Waterloggable slab with clean loot + recipe support

## Blocks

- `pathslab:dirt_path_slab`

## Crafting

**Dirt Path Slab**

```text
CCC
```

- C = Coarse Dirt
- Output: 6 Path Slabs

## Compatibility

Path Slab integrates automatically with popular "path speed" mods:

- **Block Runner**: Adds Dirt Path Slab to `blockrunner:quick_blocks` (same speed tier as dirt path).
- **AdditionZ**: Adds Dirt Path Slab to `additionz:path_blocks` so the path speed boost applies.

No hard dependency is required; the tags are simply picked up if those mods are installed.

## Technical Notes

- Mod ID: `pathslab`
- Minecraft 1.21.11, Fabric Loader 0.18.4, Fabric API 0.141.2+1.21.11
- Mojang mappings, Java 21

## License

MIT; please do not re-use or include in a modpack without attribution
