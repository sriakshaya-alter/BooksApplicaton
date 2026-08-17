package com.example.trail.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.example.trail.ui.theme.SecondaryText

@Composable
fun SectionLabel(text: String) {
    Text(
        text = text,
        color = SecondaryText,
        fontSize = 12.sp
    )
}
