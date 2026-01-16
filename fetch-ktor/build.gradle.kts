@file:OptIn(ExperimentalWasmDsl::class)

import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.multiplatform.android.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

group = "com.adamglin.compose.google.fonts.fetch-ktor"

version = "1.0.0"

kotlin {
    jvmToolchain(17)

    @Suppress("UnstableApiUsage")
    androidLibrary {
        namespace = "com.adamglin.zithian.compose"
        compileSdk = 36
        minSdk = 29
    }

    jvm {}

    iosArm64()
    iosSimulatorArm64()

    js(compiler = IR) {
        browser()
    }

    wasmJs {
        browser()
    }

    sourceSets {
        val skikoMain = create("skikoMain")
        val webMain = create("webMain")
        val androidAndJvmMain = create("androidAndJvmMain")
        skikoMain.dependsOn(commonMain.get())
        androidAndJvmMain.dependsOn(commonMain.get())
        webMain.dependsOn(skikoMain)
        jsMain.configure { dependsOn(webMain) }
        wasmJsMain.configure { dependsOn(webMain) }
        jvmMain.configure {
            dependsOn(androidAndJvmMain)
            dependsOn(skikoMain)
        }
        nativeMain.configure {
            dependsOn(skikoMain)
        }
        androidMain.configure {
            dependsOn(androidAndJvmMain)
        }
        appleMain.configure {
            dependsOn(nativeMain.get())
        }
        iosMain.configure {
            dependsOn(appleMain.get())
        }
        iosArm64Main.configure { dependsOn(iosMain.get()) }
        iosSimulatorArm64Main.configure { dependsOn(iosMain.get()) }

        commonMain.dependencies {
            // ktor
            implementation(projects.core)
            implementation(libs.ktor.client.core)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        jvmMain.dependencies {
            implementation(libs.ktor.client.cio)
        }

        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        all {
            languageSettings {
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }
    }
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}