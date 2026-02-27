package com.adamglin.async.font.google.fonts

import kotlin.jvm.JvmInline

data class Axis(
    val tag: AxisTag,
    val start: Int,
    val end: Int,
)

@JvmInline
value class AxisTag(val value: String) {
    companion object {
        val Wght = AxisTag("wght")
        val Wdth = AxisTag("wdth")
        val Opsz = AxisTag("opsz")
        val Slnt = AxisTag("slnt")
        val ARRR = AxisTag("ARRR")
        val BLED = AxisTag("BLED")
        val BNCE = AxisTag("BNCE")
        val CASL = AxisTag("CASL")
        val CRSV = AxisTag("CRSV")
        val CTRS = AxisTag("CTRS")
        val EDPT = AxisTag("EDPT")
        val EHLT = AxisTag("EHLT")
        val ELGR = AxisTag("ELGR")
        val ELSH = AxisTag("ELSH")
        val ELXP = AxisTag("ELXP")
        val FILL = AxisTag("FILL")
        val FLAR = AxisTag("FLAR")
        val GRAD = AxisTag("GRAD")
        val HEXP = AxisTag("HEXP")
        val INFM = AxisTag("INFM")
        val MONO = AxisTag("MONO")
        val MORF = AxisTag("MORF")
        val ROND = AxisTag("ROND")
        val SCAN = AxisTag("SCAN")
        val SHLN = AxisTag("SHLN")
        val SHRP = AxisTag("SHRP")
        val SOFT = AxisTag("SOFT")
        val SPAC = AxisTag("SPAC")
        val SZP1 = AxisTag("SZP1")
        val SZP2 = AxisTag("SZP2")
        val VOLM = AxisTag("VOLM")
        val WONK = AxisTag("WONK")
        val XELA = AxisTag("XELA")
        val XOPQ = AxisTag("XOPQ")
        val XPN1 = AxisTag("XPN1")
        val XPN2 = AxisTag("XPN2")
        val XROT = AxisTag("XROT")
        val XTRA = AxisTag("XTRA")
        val YEAR = AxisTag("YEAR")
        val YELA = AxisTag("YELA")
        val YOPQ = AxisTag("YOPQ")
        val YPN1 = AxisTag("YPN1")
        val YPN2 = AxisTag("YPN2")
        val YROT = AxisTag("YROT")
        val YTAS = AxisTag("YTAS")
        val YTDE = AxisTag("YTDE")
        val YTFI = AxisTag("YTFI")
        val YTLC = AxisTag("YTLC")
        val YTUC = AxisTag("YTUC")

        val knownTags: Set<AxisTag> = setOf(
            ARRR,
            BLED,
            BNCE,
            CASL,
            CRSV,
            CTRS,
            EDPT,
            EHLT,
            ELGR,
            ELSH,
            ELXP,
            FILL,
            FLAR,
            GRAD,
            HEXP,
            INFM,
            MONO,
            MORF,
            ROND,
            SCAN,
            SHLN,
            SHRP,
            SOFT,
            SPAC,
            SZP1,
            SZP2,
            VOLM,
            WONK,
            XELA,
            XOPQ,
            XPN1,
            XPN2,
            XROT,
            XTRA,
            YEAR,
            YELA,
            YOPQ,
            YPN1,
            YPN2,
            YROT,
            YTAS,
            YTDE,
            YTFI,
            YTLC,
            YTUC,
            Opsz,
            Slnt,
            Wdth,
            Wght,
        )
    }

    override fun toString(): String = value
}
