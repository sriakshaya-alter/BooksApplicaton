package com.example.trail.ui.BookDetails

import com.example.trail.ui.components.ChipItem
import com.example.trail.ui.components.SelectionItem
import com.example.trail.ui.theme.ChipFillColor
import com.example.trail.ui.theme.OnBackgroundText
import com.example.trail.ui.components.BookImage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.trail.data.BookModel
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material.icons.filled.FavoriteBorder
import com.example.trail.ui.components.SelectionItem


@Composable
fun BookDetailScreen(book: BookModel, navController: NavController) {
    val userState = bookViewModel.userBookStates.find { it.bookName == book.bookName }
    val readStatus = userState?.readStatus ?: "none"
    val isFavourite = userState?.isFavourite ?: false
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ){
        Row{
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
       }
        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            BookImage(book,width = 160.dp, height = 220.dp)
            Text(text = book.bookName,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(top=10.dp))
            Text(book.authorName,
                modifier = Modifier
                    .padding(top=7.dp),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.secondary
                )
            Row(
                horizontalArrangement = Arrangement.Center,        // ← center all chips
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                listOf(book.format,"${book.bookPages} pp",book.year).forEach{item ->
                    SelectionItem(backgroundColor = ChipFillColor,
                        text = item)
                    Spacer(modifier = Modifier.width(8.dp))

                }
//                SelectionItem( backgroundColor = ChipFillColor,
//                    text = book.format)
//                Spacer(modifier = Modifier.width(8.dp))            // ← space between chips
//                SelectionItem( backgroundColor = ChipFillColor,
//                    text = "${book.bookPages} pp")
//                Spacer(modifier = Modifier.width(8.dp))
//                SelectionItem( backgroundColor = ChipFillColor,
//                    text = book.year)
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                listOf("Want to read", "Read").forEach { item ->
                    SelectionItem(
                        text = item,
                        isSelected = item == "Want to read",
                        onClick = {bookViewModel.setReadStatus(book.isbn,item)},
                        modifier = Modifier.weight(1f)
                    )
                }

                IconButton(onClick = { bookViewModel.toggleFavourite(book.isbn) }) {
                    Icon(
                        imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        tint = if (isFavourite) Color(0xFFC2542F) else Color(0xFF2A211B)
                    )
                }
            }

        }

    }
}


