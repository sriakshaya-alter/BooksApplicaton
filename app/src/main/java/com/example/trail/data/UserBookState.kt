package com.example.trail.data


data class UserBookState(
    val isbn: String,
    var readStatus: ReadStatus = ReadStatus.NONE,
    var isFavourite: Boolean = false
)
