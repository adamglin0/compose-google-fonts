package com.adamglin.compose.async.fonts

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontLoadingStrategy
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight

/**
 * 这是异步加载的字体的抽象。实际是对 AndroidFont 和 SkikoFont 的顶层抽象。
 * @property identity 作为字体的名字，同时也参与缓存
 * @property variationSettings 可变字体的配置
 */
expect abstract class AsyncFont(
    identity: String,
    loadingStrategy: FontLoadingStrategy,
    typefaceByteArrayLoader: TypefaceByteArrayLoader,
    weight: FontWeight,
    style: FontStyle,
    variationSettings: FontVariation.Settings = FontVariation.Settings()
) : Font {
    val identity: String
    final override val loadingStrategy: FontLoadingStrategy
    val typefaceByteArrayLoader: TypefaceByteArrayLoader
    override val weight: FontWeight
    override val style: FontStyle
    val variationSettings: FontVariation.Settings
}

interface TypefaceByteArrayLoader {
    fun loadBlocking(font: AsyncFont): ByteArray?
    suspend fun awaitLoad(font: AsyncFont): ByteArray?
}