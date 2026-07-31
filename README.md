# Stonecutter multiloader template

One shared source tree builds four targets:

- Minecraft 1.20.1 on Forge and Fabric
- Minecraft 1.21.1 on NeoForge and Fabric

Run `./gradlew projects` to list nodes, `./gradlew tasks --all` to list switch tasks, and `./gradlew build` to build every target. Edit `stonecutter.properties.toml` to rename the mod or update loader versions.

Source under `src/` reflects the active node. Use `./gradlew "Set active project to <node>"` before developing against another target, then `./gradlew "Reset active project"` before committing.
