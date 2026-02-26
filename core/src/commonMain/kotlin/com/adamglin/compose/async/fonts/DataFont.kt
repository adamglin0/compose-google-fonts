package com.adamglin.compose.async.fonts

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontLoadingStrategy
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight

/**
 * 这是一个简单的实现，通过传入一个简单的挂起方法，返回 ByteArray 来生成 AsyncFont。
 */
@Stable
fun AsyncFont(
    identity: String,
    loadData: suspend () -> ByteArray,
    weight: FontWeight = FontWeight.Normal,
    style: FontStyle = FontStyle.Normal,
    loadingStrategy: FontLoadingStrategy = FontLoadingStrategy.Async,
    variationSettings: FontVariation.Settings = FontVariation.Settings(weight, style)
): Font = DataAsyncFontImpl(identity, loadData, weight, style, loadingStrategy, variationSettings)

/**
 * 这是一个简单的实现，通过传入一个简单的挂起方法，返回 ByteArray 来生成 AsyncFont。
 */
private class DataAsyncFontImpl(
    identity: String,
    loadData: suspend () -> ByteArray,
    weight: FontWeight,
    style: FontStyle,
    loadingStrategy: FontLoadingStrategy,
    variationSettings: FontVariation.Settings,
) : AsyncFont(
    identity = identity,
    loadingStrategy = loadingStrategy,
    typefaceByteArrayLoader = ByteArrayTypefaceByteArrayLoader(loadData),
    weight = weight,
    style = style,
    variationSettings = variationSettings,
)

private class ByteArrayTypefaceByteArrayLoader(
    private val loadData: suspend () -> ByteArray
) : TypefaceByteArrayLoader {

    override fun loadBlocking(font: AsyncFont): ByteArray? = null

    override suspend fun awaitLoad(font: AsyncFont): ByteArray {
        return loadData()
    }
}