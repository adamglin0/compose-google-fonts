package com.adamglin.compose.async.fonts

import android.content.Context
import android.graphics.Typeface
import androidx.compose.ui.text.font.AndroidFont
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontLoadingStrategy
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import java.io.File

//internal class AsyncAndroidFontImpl(
//    override val identity: String,
//    override val weight: FontWeight,
//    override val loader: AsyncFont.TypefaceByteArrayLoader,
//    override val style: FontStyle, loadingStrategy: FontLoadingStrategy,
//    variationSettings: FontVariation.Settings
//) : AsyncFont, AndroidFont(
//    loadingStrategy,
//    TypefaceByteArrayLoader(identity, loader),
//    variationSettings
//)
//


actual abstract class AsyncFont actual constructor(
    actual val identity: String,
    loadingStrategy: FontLoadingStrategy,
    actual val typefaceByteArrayLoader: TypefaceByteArrayLoader,
    actual override val weight: FontWeight,
    actual override val style: FontStyle,
    variationSettings: FontVariation.Settings
) : Font, AndroidFont(
    loadingStrategy = loadingStrategy,
    typefaceLoader = AndroidFontTypefaceByteArrayLoader(typefaceByteArrayLoader),
    variationSettings = variationSettings
)

private class AndroidFontTypefaceByteArrayLoader(
    val loader: TypefaceByteArrayLoader
) : AndroidFont.TypefaceLoader {
    override fun loadBlocking(
        context: Context,
        font: AndroidFont
    ): Typeface? {
        val byteArray = loader.loadBlocking(font as AsyncFont) ?: return null
        return makeTypeface(byteArray)
    }

    override suspend fun awaitLoad(
        context: Context,
        font: AndroidFont
    ): Typeface? {
        val byteArray = loader.awaitLoad(font as AsyncFont) ?: return null
        return makeTypeface(byteArray)
    }


    private fun makeTypeface(byteArray: ByteArray): Typeface {
        val tempFile = File.createTempFile("font", null)
        return try {
            tempFile.writeBytes(byteArray)
            Typeface.createFromFile(tempFile)
        } finally {
            tempFile.delete()
        }
    }
}