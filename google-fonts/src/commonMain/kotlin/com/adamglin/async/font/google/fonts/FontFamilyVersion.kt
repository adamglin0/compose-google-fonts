package com.adamglin.async.font.google.fonts

import kotlin.jvm.JvmInline

@JvmInline
value class FontFamilyVersion(val value: Int) {

    context(metadata: GoogleFontFamilyMetadata)
    inline val isLatest: Boolean get() = value == -1 || value == metadata.version

    context(metadata: GoogleFontFamilyMetadata)
    inline val versionString: String
        get() = if (isLatest) "v${metadata.version}" else "v$value"

    context(metadata: GoogleFontFamilyMetadata)
    inline val effectiveVersion: Int
        get() = if (value == -1) metadata.version else value

    companion object {
        val Latest: FontFamilyVersion = FontFamilyVersion(-1)
    }
}
