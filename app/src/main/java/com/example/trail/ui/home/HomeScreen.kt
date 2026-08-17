package com.example.trail.ui.home
import com.example.trail.ui.components.BookCard
import com.example.trail.ui.components.SelectionItem


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

@Composable
fun HomeScreen(modifier: Modifier = Modifier.Companion,
               navController: NavController,
               bookViewModel: BooksViewModel)  {

    //var BookSearch = remember{mutableStateOf("")}
//    var selectedFilter = remember { mutableStateOf("All") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(Color(0xFBF6EF))
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
//        Box {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 12.dp),
//                horizontalArrangement = Arrangement.spacedBy(1.dp)
//            ) {
//
//                Button(
//                    onClick = { bookViewModel.selectedFilter.value = "All" },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xFFC2542F)
//                    ),
//                    shape = RoundedCornerShape(20.dp)
//                ) {
//                    Text("All", color = Color.White, fontSize = 10.sp)
//                }
//
//                // Unselected buttons — outlined style
//                OutlinedButton(
//                    onClick = { bookViewModel.selectedFilter.value = "Hardcover" },
//                    shape = RoundedCornerShape(20.dp),
//                    border = BorderStroke(1.dp, Color(0xFFE8DCCC))
//                ) {
//                    Text("Hardcover", color = Color(0xFF2A211B), fontSize = 10.sp)
//                }
//
//                OutlinedButton(
//                    onClick = { bookViewModel.selectedFilter.value = "Paperback" },
//                    shape = RoundedCornerShape(20.dp),
//                    border = BorderStroke(1.dp, Color(0xFFE8DCCC))
//                ) {
//                    Text("Paperback", color = Color(0xFF2A211B), fontSize = 10.sp)
//                }
//                OutlinedButton(
//                    onClick = { bookViewModel.selectedFilter.value = "eBook" },
//                    shape = RoundedCornerShape(20.dp),
//                    border = BorderStroke(1.dp, Color(0xFFE8DCCC))
//                ) {
//                    Text(text = "eBook", color = Color(0xFF2A211B), fontSize = 10.sp)
//                }
//            }
//        }

        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.Center
            )
            {
                listOf("All", "Hardcover", "Paperback", "eBook").forEach { item ->
                    val isSelected = item == bookViewModel.selectedFilter.value
                    SelectionItem(
                        text = item,
                        isSelected = isSelected,
                        modifier = Modifier.weight(2f).padding(3.dp),
                        onClick = { bookViewModel.onFilterChange(item) }
                    )
                }


            }
        }


//        val filteredBooks = when (bookViewModel.selectedFilter.value) {
//            "Hardcover" -> {
//                if(bookViewModel.BookSearch.value.isEmpty()) {
//                    bookViewModel.bookList.filter { it.format == "Hardcover" }
//                }else {
//                    var result = bookViewModel.bookList.filter {
//                        it.author_name.contains(bookViewModel.BookSearch.value, ignoreCase = true) &&
//                                it.format == "Hardcover"
//                    }
//                    if(result.isEmpty()){
//                        emptyList()
//                    }else{
//                        result.toList()
//                    }
//                }
//
//            }
//            "Paperback" -> {
//                if(bookViewModel.BookSearch.value.isEmpty()) {
//                    bookViewModel.bookList.filter { it.format == "Paperback" }
//                }else {
//                    var result = bookViewModel.bookList.filter {
//                        it.author_name.contains(bookViewModel.BookSearch.value, ignoreCase = true) &&
//                                it.format == "Paperback"
//                    }
//                    if(result.isEmpty()){
//                        emptyList()
//                    }else{
//                        result.toList()
//                    }
//                }
//
//            }
//            "eBook" -> {
//                if(bookViewModel.BookSearch.value.isEmpty()) {
//                    bookViewModel.bookList.filter { it.format == "eBook" }
//                }else {
//                    var result = bookViewModel.bookList.filter {
//                        it.author_name.contains(bookViewModel.BookSearch.value, ignoreCase = true) &&
//                                it.format == "eBook"
//                    }
//                    if(result.isEmpty()){
//                        emptyList()
//                    }else{
//                        result.toList()
//                    }
//                }
//
//            }
//            else -> {
//                if(bookViewModel.BookSearch.value.isEmpty()) {
//                    bookViewModel.bookList.toList()
//                }else {
//                    var result = bookViewModel.bookList.filter {
//                        it.author_name.contains(bookViewModel.BookSearch.value, ignoreCase = true)
//                    }
//                    if(result.isEmpty()){
//                        emptyList()
//                    }else{
//                        result.toList()
//                    }
//                }
//
//            }
//        }
        val filteredBooks = bookViewModel.bookList.filter { book ->
            val matchesFormat = bookViewModel.selectedFilter.value == "All" ||
                    book.format == bookViewModel.selectedFilter.value
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


// ← BookCover goes here, below HomeScreen
