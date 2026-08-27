package com.example.trail.ui.components

import com.example.trail.shared.ui.theme.TrailColors.SecondaryText
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.unit.sp
import com.example.trail.shared.ui.theme.TrailColors.OnBackgroundText

@Composable
fun DetailRow(label: String, value: String?) {
    if (value.isNullOrBlank()) {
        return
    } else {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = label, fontSize = 14.sp, color = SecondaryText)
                Text(
                    text = value, fontSize = 14.sp,
                    color = OnBackgroundText,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            HorizontalDivider(color = Color(0xFFE8DCCC), thickness = 1.dp)
        }
    }
}
