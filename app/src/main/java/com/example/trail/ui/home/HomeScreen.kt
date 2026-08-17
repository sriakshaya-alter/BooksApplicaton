package com.example.trail.ui.home

import com.example.trail.ui.components.BookCard
import com.example.trail.ui.components.SelectionItem
import com.example.trail.ui.theme.SecondaryText
import com.example.trail.ui.theme.AppBackground

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trail.data.BookFilter
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState



@Composable
fun HomeScreen(modifier: Modifier = Modifier.Companion,
               navController: NavController,
               bookViewModel: BooksViewModel)  {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(AppBackground)
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
                    tint = SecondaryText
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
                    .padding(top = 6.dp)
                .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.Center
            )
            {
               BookFilter.entries.forEach { item ->
                    val isSelected = item == bookViewModel.selectedFilter.value
                    SelectionItem(
                        text = item.displayName,
                        isSelected = isSelected,
                        modifier = Modifier.padding(3.dp),
                        onClick = { bookViewModel.onFilterChange(item) }
                    )
                }

            }
        }

        val filteredBooks = bookViewModel.bookList.filter { book ->
            bookViewModel.selectedFilter.value.matches(book) &&
                    (bookViewModel.bookSearch.value.isEmpty() ||
                            book.bookName.contains(bookViewModel.bookSearch.value, ignoreCase = true) ||
                            book.authorName.contains(bookViewModel.bookSearch.value, ignoreCase = true))
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
                    val isBookmarked = bookViewModel.userBookStates.any {
                        it.isbn == book.isbn && (it.isFavourite || it.readStatus != "none")
                    }
                    BookCard(
                        book = book,
                        onClick = { navController.navigate("detail/${book.bookName}") },
                        isBookMarked = isBookmarked
                    )
                }

            }
        }

    }
}

