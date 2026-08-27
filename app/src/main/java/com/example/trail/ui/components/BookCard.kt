package com.example.trail.ui.components

import com.example.trail.shared.ui.theme.TrailColors.OutLine
import com.example.trail.shared.ui.theme.TrailColors.SecondaryText
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.trail.shared.BookModel
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.draw.shadow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Icon
import com.example.trail.shared.ui.theme.TrailColors.Background


@Composable
fun BookCard(book: BookModel,onClick:() ->Unit,isBookMarked:Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
            .clickable { onClick() }
            .padding(12.dp),

        ) {
        Row {
            BookImage(book)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = book.bookName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = book.authorName,
                    fontSize = 12.sp,
                    color = SecondaryText
                )
//                Row {
//                    Text(".",
//                        fontSize = 8.sp,
//                        )
//                    Text(
//                        book.bookPages.toString(),
//                        fontSize = 8.sp,
//                    )
//                    Text(
//                        ".",
//                        fontSize = 8.sp,
//                    )
//                    Text(
//                        text = book.year,
//                        fontSize = 8.sp,
//                    )
//                }

            }
                Icon(
                    imageVector =  Icons.Default.Bookmark,
                    contentDescription = "BookMark",
                    tint = if(isBookMarked) Color(0xFFA8452A) else OutLine
                )
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 12.dp),
            color = OutLine,
            thickness = 1.dp
        )
    }
}

@Composable
fun BookImage(
    book: BookModel,
    width: Dp = 60.dp,
    height: Dp = 80.dp
) {
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(Color(book.coverColor))
    ) {
        Box(
            modifier = Modifier
                .width(6.dp)
                .height(height)
                .align(Alignment.CenterStart)
                .background(Color(0x33300000).copy(alpha=0.1f))
        )

        Text(
            text = book.bookName,
            color = Color.White,
            fontSize = 8.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(6.dp)
        )
    }
}
