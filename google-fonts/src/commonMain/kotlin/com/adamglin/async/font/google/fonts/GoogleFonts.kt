package com.adamglin.async.font.google.fonts

import kotlin.time.Instant

val GoogleFontFamilyMetadata.Companion.ABeeZee
    get() = GoogleFontFamilyMetadata(
        name = "ABeeZee",
        version = 23,
        subsets = setOf("latin", "latin-ext"),
        lastModified = Instant.parse("2025-09-08T00:00:00Z"),
        category = Category.SansSerif,
        menu = "https://fonts.gstatic.com/s/abeezee/v23/esDR31xSG-6AGleN2tOklQ.ttf",
        commonMetadata = GoogleFontFamilyMetadata.CommonMetadata(
            variants = setOf("regular", "italic"),
            files = mapOf(
                "regular" to "https://fonts.gstatic.com/s/abeezee/v23/esDR31xSG-6AGleN6tKukbcHCpE.ttf",
                "italic" to "https://fonts.gstatic.com/s/abeezee/v23/esDT31xSG-6AGleN2tCklZUCGpG-GQ.ttf"
            )
        ),
        variableFontMetadata = null
    )