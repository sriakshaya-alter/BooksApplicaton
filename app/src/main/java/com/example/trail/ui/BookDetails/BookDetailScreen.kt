package com.example.trail.ui.BookDetails

import com.example.trail.ui.components.DetailRow
import com.example.trail.ui.components.SectionLabel
import com.example.trail.ui.home.BooksViewModel
import com.example.trail.ui.components.SelectionItem
import com.example.trail.ui.theme.ChipFillColor
import com.example.trail.ui.components.BookImage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.Box



@Composable
fun BookDetailScreen(book: BookModel, navController: NavController,bookViewModel: BooksViewModel ) {
    val userState = bookViewModel.userBookStates.find { it.isbn == book.isbn }
    val readStatus = userState?.readStatus ?: "none"
    val isFavourite = userState?.isFavourite ?: false
    LaunchedEffect(book.id) {
        bookViewModel.fetchBookDetails(book.id)
    }
    val currentBook = bookViewModel.bookList.find { it.id == book.id } ?: book
    if (bookViewModel.isDetailLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFFA8452A))
        }
    } else {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BookImage(book, width = 160.dp, height = 220.dp)
                Text(
                    text = book.bookName,
                    modifier = Modifier
                        .padding(top = 10.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,

                )
                Text(
                    book.authorName,
                    modifier = Modifier
                        .padding(top = 7.dp),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.secondary
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    listOf(
                        String.format("%.1f ★", currentBook.rating),
                        "${currentBook.bookPages} pp",
                        currentBook.year
                    ).forEach { item ->
                        SelectionItem(
                            backgroundColor = ChipFillColor,
                            text = item.toString()
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    listOf("Want to Read", "Read").forEach { item ->
                        SelectionItem(
                            text = item,
                            isSelected = item == readStatus,
                            modifier = Modifier.weight(2f),
                            onClick = { bookViewModel.setReadStatus(book.isbn, item) }
                        )
                    }
                    IconButton(onClick = { bookViewModel.toggleFavourite(book.isbn) }) {
                        Icon(
                            imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favourite",
                            tint = if (isFavourite) Color(0xFFC2542F) else Color(0xFF2A211B)
                        )
                    }
                }
            }
            Column {

                SectionLabel("ABOUT THIS BOOK")

                Text(
                    text = currentBook.description,
                    fontSize = 12.sp,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
//            Text(
//                text = "More",
//                color = Color(0xFFC2542F),
//                fontWeight = FontWeight.Bold
//            )

//        Column(modifier = Modifier.fillMaxWidth()){
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top=16.dp)
//                    .padding(horizontal = 8.dp)
//                    .border(1.dp, Color(0xFFE8DCCC), RoundedCornerShape(12.dp))
//                    .clip(RoundedCornerShape(12.dp))
//                    .background(Color.White)
//                    .padding(16.dp)
//            )
//           }
            }
            Column(modifier = Modifier.padding(top = 8.dp)) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    SectionLabel("DETAILS")
                    DetailRow("Pages", "${currentBook.bookPages} pp")
                    DetailRow("Published", currentBook.year)
                    DetailRow("ISBN", currentBook.isbn)
                    DetailRow("Rating", String.format("%.1f", currentBook.rating))
                }

            }
        }
    }
}
