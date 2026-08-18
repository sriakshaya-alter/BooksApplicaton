package com.example.trail


import com.example.trail.ui.theme.Surface
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
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val bookViewModel: BooksViewModel = viewModel()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    Scaffold(
        bottomBar = {

            if (currentRoute != "detail/{bookName}") {
                NavigationBar(
                    containerColor = Surface
                ) {
                    NavigationBarItem(
                        selected = currentRoute == "discover",
                        onClick = { navController.navigate("discover") },
                        icon = { Icon(Icons.Default.Search, contentDescription = "Discover") },
                        label = { Text("Discover") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray,
                            indicatorColor = Color(0xFFF4E9DA)
                        )
                    )
                    NavigationBarItem(
                        selected = currentRoute == "mybooks",
                        onClick = { navController.navigate("mybooks") },
                        icon = { Icon(Icons.Default.Book, contentDescription = "My Books") },
                        label = { Text("My Books") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFFA8452A),
                            selectedTextColor = Color(0xFFA8452A),
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray,
                            indicatorColor = Color(0xFFF4E9DA)
                        )
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
            composable("mybooks") { MyBooksScreen( bookViewModel = bookViewModel,navController=navController) }
            composable("detail/{bookId}",) { backStackEntry ->
                val bookId = backStackEntry.arguments?.getString("bookId") ?.toLongOrNull() ?: 0L
                val book = bookViewModel.bookList.find { it.id == bookId }
                book?.let { BookDetailScreen(book = it, navController = navController, bookViewModel = bookViewModel ) }
            }
        }
    }
}
