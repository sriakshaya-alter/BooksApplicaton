package com.example.trail.ui.BookDetails

import com.example.trail.ui.theme.ChipFillColor
import com.example.trail.ui.theme.OnBackgroundText
import com.example.trail.ui.components.BookImage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.trail.data.BookModel
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material.icons.filled.FavoriteBorder






@Composable
fun BookDetailScreen(book: BookModel, navController: NavController) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ){
        Row{
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
       }
        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            BookImage(book,width = 160.dp, height = 220.dp)
            Text(text = book.bookName,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(top=10.dp))
            Text(book.authorName,
                modifier = Modifier
                    .padding(top=7.dp),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.secondary
                )
            Row(
                horizontalArrangement = Arrangement.Center,        // ← center all chips
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                ChipItem(text = book.format)
                Spacer(modifier = Modifier.width(8.dp))            // ← space between chips
                ChipItem(text = "${book.bookPages} pp")
                Spacer(modifier = Modifier.width(8.dp))
                ChipItem(text = book.year)
            }
            BookActionButtons()
        }

    }
}

@Composable
fun ChipItem(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(ChipFillColor)
            .border(1.dp, Color(0xFFE8DCCC), RoundedCornerShape(50.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = OnBackgroundText
        )
    }
}

@Composable
fun BookActionButtons() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC2542F)),
            modifier = Modifier.weight(1f)
        ) { Text("Want to read", color = Color.White, fontSize = 12.sp) }

        OutlinedButton(onClick = { }, modifier = Modifier.weight(1f)) {
            Text("Read", fontSize = 12.sp)
        }

        IconButton(onClick = { }) {
            Icon(Icons.Default.FavoriteBorder, contentDescription = "Favourite", tint = Color(0xFF2A211B))
        }
    }
}

