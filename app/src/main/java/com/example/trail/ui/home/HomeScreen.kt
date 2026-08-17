package com.example.trail.ui.home
import com.example.trail.ui.components.BookCard


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trail.data.BookFilter
import com.example.trail.data.BookModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier.Companion)  {
    val bookViewModel: BooksViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(Color(0xF4E9DA))
            .padding(20.dp)

    ) {
        Text(
            text = "Discover",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = bookViewModel.bookSearch.value,
            onValueChange = { bookViewModel.onSearchChange(it) },
            modifier = Modifier
                .fillMaxWidth(),

            placeholder = { Text("Search", color = Color.Black) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color(0xFF8A7A6B)
                )
            },
            shape = RoundedCornerShape(32.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF4E9DA),
                focusedContainerColor = Color(0xFFF4E9DA),
                unfocusedBorderColor = Color(0xFFE8DCCC),
                focusedBorderColor = Color(0xFFC2542F)
            ),
            singleLine = true,
        )


        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.Center
            )
            {
                BookFilter.entries.forEach { item ->
                    val isSelected = item == bookViewModel.selectedFilter.value
                    OutlinedButton(
                        onClick = { bookViewModel.onFilterChange(item) },
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isSelected) Color(0xFFC2542F) else Color.Transparent
                        ),
                        border = BorderStroke(1.dp, Color(0xFFE8DCCC)),
                        modifier = Modifier
                            .padding(5.dp)
                    ) {
                        Text(text = item.displayName, color = Color(0xFF2A211B), fontSize = 8.sp)
                    }
                }
            }
        }


        val filteredBooks = bookViewModel.bookList.filter { book ->
            val matchesFormat = bookViewModel.selectedFilter.value == BookFilter.ALL ||
                    book.format == bookViewModel.selectedFilter.value.displayName
            val matchesSearch = bookViewModel.bookSearch.value.isEmpty() ||
                    book.bookName.contains(bookViewModel.bookSearch.value, ignoreCase = true) ||
                    book.authorName.contains(bookViewModel.bookSearch.value, ignoreCase = true)
            matchesFormat && matchesSearch
        }

        LazyColumn {
            if (filteredBooks.isEmpty()) {
                item {
                    Text(
                        text = "No books found",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(filteredBooks) { book ->
                    BookCard(book)
                }
            }
        }


    }
}


