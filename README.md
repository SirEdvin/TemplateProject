# Stonecutter multiloader template

One shared source tree builds four targets:

- Minecraft 1.20.1 on Forge and Fabric
- Minecraft 1.21.1 on NeoForge and Fabric

Run `./gradlew projects` to list nodes, `./gradlew tasks --all` to list switch tasks, and `./gradlew build` to build every target. Edit `stonecutter.properties.toml` to rename the mod or update loader versions.

Source under `src/` reflects the active node. Use `./gradlew "Set active project to <node>"` before developing against another target, then `./gradlew "Reset active project"` before committing.

The stable Stonecutter pin is 0.9.8. Keep 0.10 prereleases opt-in: the inspected 0.10-beta.1 requires Java 25 for Gradle and Gradle 9.7+, changes property APIs and preprocessing semantics, and is not a patch upgrade. Minecraft compilation targets remain Java 17/21 independently of the Gradle launcher.

Use `--no-daemon` and redirect verbose Gradle output to a log. Verify `build` with both `1.21.1-fabric` and `1.20.1-forge` active, reset to the VCS node, and confirm no source churn. Both Forge-family scripts order `createMinecraftArtifacts` after `stonecutterGenerate`; retain this when adding access transformers. Full-file version overrides also need active-source precedence checks before use.
