## Compose Google Fonts

![Maven Central Version](https://img.shields.io/maven-central/v/com.adamglin/compose-google-fonts)

> This library is under development.

## Install

**libs.versions.toml**

``` file="libs.versions.toml"
[versions]
composeGoogleFonts = "latest-version"
[libraries]
composeGoogleFonts-core = { module = "com.adamglin:compose-google-fonts:core", version.ref = "compose-google-fonts" }
composeGoogleFonts-fetch-ktor = { module = "com.adamglin:compose-google-fonts:fetch-ktor", version.ref = "compose-google-fonts" }
```

**build.gradle.kts**

``` kts
implementation(libs.composeGoogleFonts.core)
implementation(libs.composeGoogleFonts.fetch.ktor)
```
