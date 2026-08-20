package com.example.trail.ui.home

import com.example.trail.ui.components.BookCard
import com.example.trail.ui.components.SelectionItem
import com.example.trail.ui.theme.SecondaryText
import com.example.trail.ui.theme.Background

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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import com.example.trail.data.ReadStatus

@Composable
fun HomeScreen(modifier: Modifier = Modifier.Companion,
               navController: NavController,
               bookViewModel: BooksViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(Background)
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Discover", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            IconButton(onClick = { navController.navigate("settings") }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color(0xFF2A211B)
                )
            }
        }

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
        if (bookViewModel.needsApiKey.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Please enter your BigBook API key to continue")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.navigate("settings") }) {
                        Text("Go to Settings")
                    }
                }
            }
        }
        else if (bookViewModel.isLoading.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFFA8452A))
            }
        } else if (bookViewModel.error.value != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = bookViewModel.error.value ?: "Error")
            }
        } else {
            Box {
                if (bookViewModel.bookSearch.value.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(start = 10.dp, top = 8.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    )
                    {
                        BookFilter.entries.forEach { item ->
                            val isSelected = item == bookViewModel.selectedFilter.value
                            SelectionItem(
                                text = item.displayName,
                                isSelected = isSelected,
                                modifier = Modifier.padding(0.dp),
                                onClick = { bookViewModel.onFilterChange(item) }
                            )
                        }

                    }
                }
            }

            val filteredBooks = bookViewModel.bookList.filter { book ->
                bookViewModel.selectedFilter.value.matches(book)
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
                            it.bookId == book.id && (it.isFavourite || it.readStatus != ReadStatus.NONE)
                        }
                        BookCard(
                            book = book,
                            onClick = { navController.navigate("detail/${book.id}") },
                            isBookMarked = isBookmarked
                        )
                    }

                }
            }

        }
    }
}
