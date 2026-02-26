package com.adamglin.compose.async.fonts

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontLoadingStrategy
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.SkikoFont
import androidx.compose.ui.text.font.SkikoFont.TypefaceLoader
import org.jetbrains.skia.Data
import org.jetbrains.skia.FontMgr
import org.jetbrains.skia.Typeface

actual abstract class AsyncFont actual constructor(
    actual override val identity: String,
    loadingStrategy: FontLoadingStrategy,
    actual val typefaceByteArrayLoader: TypefaceByteArrayLoader,
    actual override val weight: FontWeight,
    actual override val style: FontStyle,
    variationSettings: FontVariation.Settings
) : Font, SkikoFont(
    loadingStrategy = loadingStrategy,
    typefaceLoader = SkikoTypefaceLoader(typefaceByteArrayLoader),
    variationSettings = variationSettings
)

private class SkikoTypefaceLoader(private val typefaceLoader: TypefaceByteArrayLoader) :
    TypefaceLoader {
    override fun loadBlocking(font: SkikoFont): Typeface? {
        val byteArray = typefaceLoader.loadBlocking(font as AsyncFont) ?: return null
        return FontMgr.default.makeFromData(Data.makeFromBytes(byteArray))
    }

    override suspend fun awaitLoad(font: SkikoFont): Typeface? {
        val byteArray = typefaceLoader.awaitLoad(font as AsyncFont) ?: return null
        return FontMgr.default.makeFromData(Data.makeFromBytes(byteArray))
    }

}