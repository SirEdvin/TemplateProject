# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Changed
- Stonecutter multiversion layout (1.20.1 Fabric/Forge, 1.21.1 Fabric/NeoForge) becomes the default branch.
- Upgraded to Stonecutter 0.10 on Gradle 9.9.1; Gradle now requests JDK 25 through `gradle/gradle-daemon-jvm.properties`.
- `loomx.loom_version` moved to `gradle.properties` (Stonecutter 0.10 no longer exposes structured values to plugins).
- README documents the layout, switching the active node, and adding or removing nodes.
