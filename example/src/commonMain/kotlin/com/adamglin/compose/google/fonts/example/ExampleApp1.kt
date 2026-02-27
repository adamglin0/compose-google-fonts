package com.adamglin.compose.google.fonts.example

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import com.adamglin.async.font.google.fonts.GoogleFontFamily

@OptIn(ExperimentalTextApi::class)
@Composable
fun GoogleFontFamilyDemo() {
    var selectedGoogleFontFamily = remember { mutableStateOf(GoogleFontFamily.allVariant(
        GoogleFonts.ABeeze)) }
    val textFieldState = rememberTextFieldState(
        """
        No one shall be subjected to arbitrary arrest, detention or exile.
        Everyone is entitled in full equality to a fair and public hearing by an independent and impartial tribunal, in the determination of his rights and obligations and of any criminal charge against him.
        No one shall be subjected to arbitrary interference with his privacy, family, home or correspondence, nor to attacks upon his honour and reputation. Everyone has the right to the protection of the law against such interference or attacks.
    """.trimIndent()
    )
    Column {
        BasicSecureTextField(
            state = textFieldState,
            modifier = Modifier.fillMaxWidth().height(300.dp)
                .border(1.dp, Color.Black)
        )
    }
}