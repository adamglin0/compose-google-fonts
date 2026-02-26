//package com.adamglin.compose.google.fonts.example
//
//import androidx.compose.foundation.text.BasicText
//import androidx.compose.runtime.*
//import androidx.compose.ui.platform.LocalFontFamilyResolver
//import androidx.compose.ui.text.ExperimentalTextApi
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.createFontFamilyResolver
//import com.adamglin.compose.google.fonts.fetch.ktor.FetchKtor
//import kotlinx.coroutines.CoroutineExceptionHandler
//import kotlinx.coroutines.Dispatchers
//
//@OptIn(ExperimentalTextApi::class)
//@Composable
//fun ExampleApp1() {
//    CompositionLocalProvider(
//        LocalFontFamilyResolver provides createFontFamilyResolver(Dispatchers.Main + CoroutineExceptionHandler { _, e -> e.printStackTrace() })
//    ){
//        val fontFamily = FontFamily(
//            Font("zcoolxiaowei-v2", loadData = {
//                println("load font")
//                val url =
//                    "https://fonts.gstatic.com/s/zcoolxiaowei/v15/i7dMIFFrTRywPpUVX9_RJyM1YFKQHwyVd3U.ttf"
//                FetchKtor().downloadBytes(url)?.also { println("load done")} ?: error("download error")
//            })
//        )
//
//        BasicText(
//            text = "Hello, World! 你好",
//            style = TextStyle(fontFamily = fontFamily)
//        )
//    }
//
//
//}