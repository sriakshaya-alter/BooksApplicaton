package com.example.trail.ui.myBook

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
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
import com.example.trail.shared.BookModel
import com.example.trail.ui.components.SelectionItem
import com.example.trail.ui.home.BooksViewModel
import androidx.compose.foundation.layout.height
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.foundation.layout.Arrangement
import androidx.navigation.NavController
import com.example.trail.shared.ReadStatus
import com.example.trail.shared.MyBooksFilter
import com.example.trail.shared.ui.theme.TrailColors.OnBackgroundText

@Composable
fun MyBooksScreen(bookViewModel: BooksViewModel,navController: NavController) {
    val totalCount = bookViewModel.userBookStates.size
    val readCount = bookViewModel.userBookStates.count { it.readStatus == ReadStatus.READ }
    val wantToReadCount = bookViewModel.userBookStates.count { it.readStatus == ReadStatus.WANT_TO_READ }
    val favouriteCount = bookViewModel.userBookStates.count { it.isFavourite }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "MyBooks",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
        Spacer(modifier = Modifier.height(16.dp))
        Row( modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            ) {MyBooksFilter.entries.forEach { filter ->
            val count = when (filter) {
                MyBooksFilter.ALL          -> totalCount
                MyBooksFilter.READ         -> readCount
                MyBooksFilter.WANT_TO_READ -> wantToReadCount
                MyBooksFilter.FAVOURITES   -> favouriteCount
            }
            val isSelected = filter == bookViewModel.myBooksFilter.value
            SelectionItem(
                text = "${filter.displayName} $count",
                isSelected = isSelected,
                modifier = Modifier.padding(2.dp),
                onClick = { bookViewModel.onMyBooksFilterChange(filter) }
            )
        }

        }

        val myBooks = bookViewModel.userBookStates
            .filter { state ->
                val hasAction = state.readStatus != ReadStatus.NONE || state.isFavourite

                val matchesFilter = bookViewModel.myBooksFilter.value == MyBooksFilter.ALL ||
                        (bookViewModel.myBooksFilter.value == MyBooksFilter.READ && state.readStatus == ReadStatus.READ) ||
                        (bookViewModel.myBooksFilter.value == MyBooksFilter.WANT_TO_READ && state.readStatus == ReadStatus.WANT_TO_READ) ||
                        (bookViewModel.myBooksFilter.value == MyBooksFilter.FAVOURITES && state.isFavourite)

                matchesFilter && hasAction

            }
            .mapNotNull { state ->
                bookViewModel.bookList.find { it.id == state.bookId }
            }

        if (myBooks.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                Text(
                    text = "No books yet. Go to Discover and add some!",
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(myBooks) { book ->
                    MyBookCard(book = book,bookViewModel= bookViewModel,onClick = { navController.navigate("detail/${book.id}") })
                }
            }
        }
    }
}

@Composable
fun MyBookCard(book: BookModel, bookViewModel: BooksViewModel,onClick: () -> Unit) {
    val userState = bookViewModel.userBookStates.find { it.bookId == book.id }
    val isFavourite = userState?.isFavourite ?: false
    val readStatus = userState?.readStatus ?: "none"

    Column(modifier = Modifier.padding(8.dp).clickable { onClick() }) {
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
                if (readStatus != ReadStatus.NONE) {

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(OnBackgroundText.copy(alpha = 0.7f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (readStatus == ReadStatus.WANT_TO_READ) "WANT TO READ" else "READ",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }


            if (isFavourite) {

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF2A211B).copy(alpha = 0.7f))
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favourite",
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
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