package com.example.trail.shared

data class UserBookState(
    val bookId: Long,
    var readStatus: ReadStatus = ReadStatus.NONE,
    var isFavourite: Boolean = false
)