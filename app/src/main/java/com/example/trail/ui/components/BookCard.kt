package com.example.trail.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trail.data.BookModel

@Composable
fun BookCard(book: BookModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xF4E9DA))
            .padding(12.dp),

        ) {
        Row {
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(80.dp)
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                    .background(Color(book.coverColor)),
                contentAlignment = Alignment.Center,

                ) {
                Text(
                    text = book.bookName,
                    color = Color.White,
                    fontSize = 8.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = book.bookName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = book.authorName,
                    fontSize = 8.sp,
                )
                Row {
                    Text(
                        text = book.format,
                        fontSize = 8.sp,
                    )
                    Text(".",
                        fontSize = 8.sp,
                        )
                    Text(
                        book.bookPages.toString(),
                        fontSize = 8.sp,
                    )
                    Text(
                        ".",
                        fontSize = 8.sp,
                    )
                    Text(
                        text = book.year,
                        fontSize = 8.sp,
                    )
                }

            }
        }
        HorizontalDivider(   // ← HR line at bottom of each card
            modifier = Modifier.padding(top = 12.dp),
            color = Color(0xFFE8DCCC),
            thickness = 1.dp
        )

    }
}