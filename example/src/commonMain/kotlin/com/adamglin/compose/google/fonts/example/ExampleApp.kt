package com.adamglin.compose.google.fonts.example

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.text.font.toFontFamily
import com.adamglin.compose.async.fonts.AsyncFont
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalTextApi::class)
@Composable
fun ExampleApp() {
    val downloader = remember { Downloader() }
    val fontUrl =
        "https://fonts.gstatic.com/s/betaniapatmos/v2/9oRXNYMTrDYnkuhOrHhyQracaunDNbEH8qpU.ttf"
    val fontFamily = remember {
        AsyncFont(
            identity = "BetaniaPatmos",
            loadData = {
                downloader.downloadBytes(url = fontUrl).also {
                    println("download done")
                } ?: error("download error")
            },
        ).toFontFamily()
    }

    BasicText(
        "Compose Multiplatform is a declarative framework for sharing UI code across multiple platforms with Kotlin. It is based on Jetpack Compose and developed by JetBrains and open-source contributors.",
        style = TextStyle(fontFamily = fontFamily)
    )
}
