package com.example.trail.ui.components

import com.example.trail.ui.theme.AppBackground

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import com.example.trail.ui.theme.ChipFillColor
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight

@Composable
fun SelectionItem(
    modifier: Modifier= Modifier,
    backgroundColor: Color = AppBackground,
    text: String,
    isSelected: Boolean = false,       // default false — no selection
    onClick: () -> Unit = {}           // default empty — no click
) {
    Box(
        modifier
            .clip(RoundedCornerShape(50.dp))
            .background(if (isSelected) Color(0xFFC2542F) else backgroundColor)
            .border(1.dp, Color(0xFFE8DCCC), RoundedCornerShape(50.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp),
             contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color.White else Color(0xFF2A211B)
        )
    }
}
