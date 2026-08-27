package com.example.trail.ui.components

import com.example.trail.shared.ui.theme.TrailColors.OnBackgroundText
import com.example.trail.shared.ui.theme.TrailColors.OutLine
import com.example.trail.shared.ui.theme.TrailColors.Surface

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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import com.example.trail.shared.ui.theme.TrailColors.Chipfill

@Composable
fun SelectionItem(
    modifier: Modifier= Modifier,
    backgroundColor: Color = Chipfill,
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {} ,
) {
    Box(
        modifier
            .clip(RoundedCornerShape(50.dp))
            .background(if (isSelected) MaterialTheme.colorScheme.primary else backgroundColor)
            .border(1.dp, OutLine, RoundedCornerShape(50.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (text.startsWith("All")) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = if (isSelected) Surface else OnBackgroundText,
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else OnBackgroundText
            )
        }
    }
}