# Stonecutter multiloader template

One shared source tree builds four targets with [Stonecutter](https://stonecutter.kikugie.dev/) 0.10:

| Node | Minecraft | Loader | Node Java |
| --- | --- | --- | --- |
| `1.20.1-fabric` | 1.20.1 | Fabric | 17 |
| `1.20.1-forge` | 1.20.1 | Forge | 17 |
| `1.21.1-fabric` | 1.21.1 | Fabric | 21 |
| `1.21.1-neoforge` | 1.21.1 | NeoForge | 21 |

This is the default branch. The old per-version branches (`1.20`, `1.21`) are history and are not covered by these instructions.

## Layout

- `settings.gradle.kts` registers the nodes and `vcsVersion` (the canonical committed source state, `1.21.1-fabric`). Every node is a flat `{minecraft}-{loader}` project using its loader's build script.
- `stonecutter.gradle.kts` is the controller: the **active node** (`stonecutter active "..."`), tags and loader constants.
- `build.fabric.gradle.kts`, `build.forge.gradle.kts`, `build.neoforge.gradle.kts` are the node build templates.
- `stonecutter.properties.toml` holds per-loader and per-version values (mod metadata, loader versions, compatibility ranges).
- `src/` is the shared source. It always shows the **active node**; differences are Stonecutter comments (`//? if fabric {`, `//? if <1.21 {`).
- Sources generated for the other nodes live under `versions/<node>/build/generated/stonecutter/` and are disposable. Never edit them.

`./gradlew projects` lists nodes; `./gradlew tasks --all` lists the switch tasks.

## Requirements

- Gradle 9.9.1 (wrapper). Stonecutter 0.10 needs Gradle 9.7+ running on **Java 25**.
- `gradle/gradle-daemon-jvm.properties` requests JDK 25 and the Foojay resolver downloads it when missing, so `./gradlew` works from any installed JDK. Point your IDE's Gradle JVM at JDK 25.
- Nodes still compile with their own Java 17/21 toolchains, provisioned the same way.
- The template defines no unit tests, GameTests or server smoke run: `./gradlew build` compiles and packages every node and `test` is `NO-SOURCE`.

Use `--no-daemon` and redirect verbose output: `./gradlew build --no-daemon > build.log 2>&1`.

## Everyday commands

```sh
./gradlew build --no-daemon                                  # every node
./gradlew ":1.20.1-forge:build" --no-daemon                  # one node
./gradlew "Set active project to 1.20.1-forge" --no-daemon   # switch the active node
./gradlew "Reset active project" --no-daemon                 # back to vcsVersion; run before committing
```

Switch and build in separate Gradle invocations; combining them fails task validation. Switching rewrites tracked files in `src/` and the `active` literal in `stonecutter.gradle.kts`, so start from a clean `git status`. Before committing, run `Reset active project` and check that `git diff` holds only intended changes.

## Add a node

1. Register it in `settings.gradle.kts` (for example `match("1.21.4", "fabric", "neoforge")`) and add its values to `stonecutter.properties.toml` (`mod.mc_compat`, loader and API versions) and its Java level to the build script.
2. Confirm `projects` and `tasks --all` show the node and its switch task.
3. Switch to it, adapt source conditionals, build, then build every node with each node active and finish with `Reset active project`.

## Remove a node

1. While it is still registered, switch to a node you keep. If it is the `vcsVersion` node, point `vcsVersion` at a retained node and run `Reset active project`; commit that churn alone.
2. Delete its `match(...)` registration, its tables in `stonecutter.properties.toml`, any `versions/<node>/` override and any node-only build logic.
3. Delete source conditionals that are now constant for every remaining node.
4. Check `projects` lists exactly the remaining nodes, build all, run a switch round trip and `Reset active project`: `git status` must be clean.

## Customize

Edit `stonecutter.properties.toml` to rename the mod or update loader versions. `loomx.loom_version` lives in `gradle.properties` because the Fabric loom compatibility plugin reads it as a plain Gradle property. Forge-family scripts order `createMinecraftArtifacts` after `stonecutterGenerate`; keep that when adding access transformers. A full-file override in `versions/<node>/src/` collides with the shared file while that node is active, so exclude the shared path in the build script before using one.

SirEdvin libraries and `modding-buildenv` are not used here. If you add them, resolve them from `https://repo.repsy.io/siredvin/default`, the only repository that hosts `site.siredvin` artifacts.
