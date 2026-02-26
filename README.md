## Compose Async Fonts

![Maven Central Version](https://img.shields.io/maven-central/v/com.adamglin/compose-google-fonts)

> This library is under development.

## Install

**libs.versions.toml**

``` file="libs.versions.toml"
[versions]
composeAsyncFonts = "latest-version"
[libraries]
composeAsyncFonts-core = { module = "com.adamglin:compose-async-fonts:core", version.ref = "composeAsyncFonts" }
composeAsyncFonts-fetch-ktor = { module = "com.adamglin:compose-async-fonts:fetch-ktor", version.ref = "composeAsyncFonts" }
composeAsyncFonts-googleFonts = { module = "com.adamglin:compose-async-fonts:google-fonts", version.ref = "composeAsyncFonts" }
```

**build.gradle.kts**

``` kts
implementation(libs.composeAsyncFonts.core)
implementation(libs.composeAsyncFonts.fetch.ktor)
implementation(libs.composeAsyncFonts.googleFonts)
```

## Features

- [ ] Persistent font cache