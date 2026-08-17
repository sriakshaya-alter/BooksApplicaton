package com.example.trail.ui.myBook


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trail.data.BookModel
import com.example.trail.ui.components.SelectionItem
import com.example.trail.ui.home.BooksViewModel
import androidx.compose.foundation.layout.height
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Arrangement


@Composable
fun MyBooksScreen(bookViewModel: BooksViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "MyBooks",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row( horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            ) {
            listOf("All","Want to Read","Read","Favourites").forEach{item ->
                val isSelected = item == bookViewModel.myBooksFilter.value
                SelectionItem(text = item,
                    isSelected = isSelected,
                    modifier = Modifier.weight(0.2f).padding(2.dp),
                    onClick = { bookViewModel.onMyBooksFilterChange(item)})
            }
        }

        val myBooks = bookViewModel.userBookStates
            .filter { state ->
                val matchesFilter = bookViewModel.myBooksFilter.value == "All" ||
                        state.readStatus == bookViewModel.myBooksFilter.value ||
                        (bookViewModel.myBooksFilter.value == "Favourites" && state.isFavourite)
                matchesFilter
            }
            .mapNotNull { state ->
                bookViewModel.bookList.find { it.isbn == state.isbn }
            }

        if (myBooks.isEmpty()) {
            Text(
                text = "No books yet. Go to Discover and add some!",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(myBooks) { book ->
                    MyBookCard(book = book,bookViewModel= bookViewModel)
                }
            }
        }
    }
}

@Composable
fun MyBookCard(book: BookModel, bookViewModel: BooksViewModel) {
    val userState = bookViewModel.userBookStates.find { it.isbn == book.isbn }
    val isFavourite = userState?.isFavourite ?: false
    val readStatus = userState?.readStatus ?: "none"

    Column(modifier = Modifier.padding(8.dp)) {
        Box {
            // book cover
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(book.coverColor))
            ) {
                DisplayText(book.bookName,    modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(5.dp)
                )
                if (readStatus != "none") {

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFF2A211B))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (readStatus == "read") "READ" else "WANT TO READ",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // heart icon — top right
            if (isFavourite) {

                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favourite",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF2A211B))
                        .size(10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = book.bookName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(text = book.authorName, fontSize = 12.sp, color = Color(0xFF8A7A6B))
    }
}
@Composable
fun DisplayText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 10.sp,
        modifier = modifier
    )
}