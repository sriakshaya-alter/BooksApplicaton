package com.example.trail

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.trail.ui.BookDetails.BookDetailScreen
import com.example.trail.ui.home.BooksViewModel
import com.example.trail.ui.home.HomeScreen
import com.example.trail.ui.myBook.MyBooksScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val bookViewModel: BooksViewModel = viewModel()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    Scaffold(
        bottomBar = {

            if (currentRoute != "detail/{bookName}") {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == "discover",
                        onClick = { navController.navigate("discover") },
                        icon = { Icon(Icons.Default.Search, contentDescription = "Discover") },
                        label = { Text("Discover") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "mybooks",
                        onClick = { navController.navigate("mybooks") },
                        icon = { Icon(Icons.Default.Book, contentDescription = "My Books") },
                        label = { Text("My Books") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "discover",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("discover") {
                HomeScreen(navController = navController, bookViewModel = bookViewModel)
            }
            composable("mybooks") { MyBooksScreen( bookViewModel = bookViewModel) }
            composable("detail/{bookName}",) { backStackEntry ->
                val bookName = backStackEntry.arguments?.getString("bookName") ?: ""
                val book = bookViewModel.bookList.find { it.bookName == bookName }
                book?.let { BookDetailScreen(book = it, navController = navController, bookViewModel = bookViewModel ) }
            }
        }
    }
}
