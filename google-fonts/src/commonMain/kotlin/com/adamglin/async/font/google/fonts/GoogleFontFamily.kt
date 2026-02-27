package com.adamglin.async.font.google.fonts

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.adamglin.compose.async.fonts.AsyncFont

@Stable
class GoogleFontFamily private constructor(
    val metadata: GoogleFontFamilyMetadata,
    val version: FontFamilyVersion,
    val variants: Set<String>
) {
    fun toFontFamily(loader: suspend (url: String) -> ByteArray?): FontFamily {
        val asyncFonts = variants.map { variant ->
            val url = metadata.commonMetadata.files[variant]
                ?: error("No file URL for variant: $variant")

            val (weight, style) = parseVariant(variant)

            AsyncFont(
                identity = "${metadata.name}-$variant",
                loadData = {
                    loader(url) ?: error("Failed to load font: $url")
                },
                weight = weight,
                style = style
            )
        }

        return FontFamily(asyncFonts)
    }

    private fun parseVariant(variant: String): Pair<FontWeight, FontStyle> {
        return when (variant) {
            "regular" -> FontWeight.Normal to FontStyle.Normal
            "italic" -> FontWeight.Normal to FontStyle.Italic
            else -> {
                val weightValue = variant.filter { it.isDigit() }.toIntOrNull() ?: 400
                val isItalic = variant.contains("italic", ignoreCase = true)
                FontWeight(weightValue) to if (isItalic) FontStyle.Italic else FontStyle.Normal
            }
        }
    }

    companion object {
        fun allVariant(
            metadata: GoogleFontFamilyMetadata,
            version: FontFamilyVersion = FontFamilyVersion.Latest
        ): GoogleFontFamily {
            return GoogleFontFamily(
                metadata = metadata,
                version = version,
                variants = metadata.commonMetadata.variants
            )
        }

        fun ofVariants(
            metadata: GoogleFontFamilyMetadata,
            variants: Set<String>,
            version: FontFamilyVersion = FontFamilyVersion.Latest
        ): GoogleFontFamily {
            val validVariants = metadata.commonMetadata.variants
            val invalidVariants = variants - validVariants
            if (invalidVariants.isNotEmpty()) {
                error("Invalid variants for ${metadata.name}: $invalidVariants. Valid variants: $validVariants")
            }
            return GoogleFontFamily(
                metadata = metadata,
                version = version,
                variants = variants
            )
        }
    }
}
