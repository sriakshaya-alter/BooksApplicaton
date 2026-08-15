package com.example.trail.data


data class UserBookState(
    val isbn: String,
    var readStatus: String = "none",    // "none", "wantToRead", "read"
    var isFavourite: Boolean = false
)
