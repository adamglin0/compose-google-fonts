package com.adamglin.async.font.google.fonts

import kotlin.time.Instant

data class GoogleFontFamilyMetadata(
    val name: String,
    val version: Int,
    val subsets: Set<String>,
    val lastModified: Instant,
    val category: Category,
    val menu: String,
    val commonMetadata: CommonMetadata,
    val variableFontMetadata: VariableFontMetadata?,
) {
    companion object {}
    data class VariableFontMetadata(
        val variants: Set<String>,
        val files: Map<String, String>,
        val axes: Set<Axis>,
    )

    data class CommonMetadata(
        val variants: Set<String>,
        val files: Map<String, String>,
    )
}
