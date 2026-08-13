package com.example.trail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
@Composable
fun HomeScreen(modifier:Modifier = Modifier)  {
    var bookViewModel: BooksViewModel = viewModel()
    //var BookSearch = remember{mutableStateOf("")}
//    var selectedFilter = remember { mutableStateOf("All") }
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
                value=bookViewModel.BookSearch.value,
                onValueChange = {bookViewModel.BookSearch.value = it},
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

        Box{
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp),
                horizontalArrangement = Arrangement.Center
            ){
                listOf("All","Hardcover","Paperback","eBook").forEach { items ->
                    val isSelected = items == bookViewModel.selectedFilter.value
                    OutlinedButton(
                        onClick = { bookViewModel.selectedFilter.value = items},
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isSelected) Color(0xFFC2542F) else Color.Transparent
                        ),
                        border = BorderStroke(1.dp, Color(0xFFE8DCCC))
                    ) {
                        Text(text = items, color = Color(0xFF2A211B), fontSize = 8.sp)
                    }

                }
            }
        }

        val filteredBooks = when (bookViewModel.selectedFilter.value) {
            "Hardcover" -> {
                if(bookViewModel.BookSearch.value.isEmpty()) {
                    bookViewModel.bookList.filter { it.format == "Hardcover" }
                }else {
                    var result = bookViewModel.bookList.filter {
                        it.author_name.contains(bookViewModel.BookSearch.value, ignoreCase = true) &&
                                it.format == "Hardcover"
                    }
                    if(result.isEmpty()){
                        emptyList()
                    }else{
                        result.toList()
                    }
                }

            }
            "Paperback" -> {
                if(bookViewModel.BookSearch.value.isEmpty()) {
                    bookViewModel.bookList.filter { it.format == "Paperback" }
                }else {
                    var result = bookViewModel.bookList.filter {
                        it.author_name.contains(bookViewModel.BookSearch.value, ignoreCase = true) &&
                                it.format == "Paperback"
                    }
                    if(result.isEmpty()){
                        emptyList()
                    }else{
                        result.toList()
                    }
                }

            }
            "eBook" -> {
                if(bookViewModel.BookSearch.value.isEmpty()) {
                    bookViewModel.bookList.filter { it.format == "eBook" }
                }else {
                    var result = bookViewModel.bookList.filter {
                        it.author_name.contains(bookViewModel.BookSearch.value, ignoreCase = true) &&
                                it.format == "eBook"
                    }
                    if(result.isEmpty()){
                        emptyList()
                    }else{
                        result.toList()
                    }
                }

            }
            else -> {
                if(bookViewModel.BookSearch.value.isEmpty()) {
                    bookViewModel.bookList.toList()
                }else {
                    var result = bookViewModel.bookList.filter {
                        it.author_name.contains(bookViewModel.BookSearch.value, ignoreCase = true)
                    }
                    if(result.isEmpty()){
                        emptyList()
                    }else{
                        result.toList()
                    }
                }

            }
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
                    BookCover(book)
                }
            }
        }


    }
}

//@Composable
//fun filteringIcon(){
//    Button(onClick = {allformat()}){
//       Text("All Fromats")
//    }
//    Button()
//}


// ← BookCover goes here, below HomeScreen
@Composable
fun BookCover(book: BookModel) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xF4E9DA))
        .padding(12.dp)
        .clip(RoundedCornerShape(8.dp))   // clip first
        .border(1.dp, Color(0xFFE8DCCC), RoundedCornerShape(8.dp)) // then border
        .background(Color(0xFFFBF6EF))
        .padding(12.dp)){
        Row {
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(book.coverColor)),
                contentAlignment = Alignment.Center,

            ) {
                Text(
                    text = book.book_name,
                    color = Color.White,
                    fontSize = 8.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column{
                Text(
                    text = book.book_name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = book.author_name,
                    fontSize = 8.sp,
                )
                Row{
                    Text(text = book.format,
                        fontSize = 8.sp, )
                    Text(".")
                    Text(book.book_pages.toString(),
                        fontSize = 8.sp,)
                    Text(".",
                        fontSize = 8.sp,)
                    Text(text=book.year,
                        fontSize = 8.sp,)
                }

            }
        }
}
}
//@Composable
//fun allformat(){
//    filteredList =
//}