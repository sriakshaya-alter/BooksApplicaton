package com.example.trail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults



@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    var BookSearch = remember{mutableStateOf("")}

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(Color.White)
            .padding(20.dp)

    ) {
            Text(
            text = "Discover",
            fontSize = 20.sp,
            //modifier = Modifier.padding(16.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value=BookSearch.value,
                onValueChange = {BookSearch.value = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                ),
            placeholder = { Text("Search", color = Color(0xFF8A7A6B)) },
            leadingIcon = {
                Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color(0xFF8A7A6B)
                )
        },



    }
}



// ← BookCover goes here, below HomeScreen
@Composable
fun BookCover(book: BookModel) {
    Box(
        modifier = Modifier
            .width(60.dp)
            .height(80.dp)
            .background(Color(book.coverColor)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = book.book_name,
            color = Color.White,
            fontSize = 8.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(4.dp)
        )
    }
}
