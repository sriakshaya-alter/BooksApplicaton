package com.example.trail.ui.BookDetails


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.trail.data.BookModel

@Composable
fun BookDetailScreen(book: BookModel, navController: NavController) {
    Text(book.bookName)  // placeholder for now
}
