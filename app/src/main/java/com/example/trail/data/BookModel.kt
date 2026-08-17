package com.example.trail.data


data class BookModel(
    var id: Long = 0,
    var bookName: String,
    var authorName: String,
    var bookPages: Int,
    var year: String,
    var coverColor: Long,
    var description: String = "",
    var isbn: String = "",
    var rating: Double = 0.0    // ← add rating
)

