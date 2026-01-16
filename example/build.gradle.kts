import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvmToolchain(17)
    jvm {}

    sourceSets {
        commonMain.dependencies {
            // compose
            implementation(compose.desktop.currentOs) {
                exclude(group = "org.jetbrains.compose.material")
                exclude(group = "org.jetbrains.compose.material3")
            }
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)

            implementation(projects.core)
            implementation(projects.fetchKtor)
        }
    }
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}
