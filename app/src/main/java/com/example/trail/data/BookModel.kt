package com.example.trail.data


data class BookModel(
    var id: Long = 0,               // API book id
    var bookName: String,
    var authorName: String,
    var bookPages: Int,
    var year: String,
    var coverColor: Long,           // our custom colored box
    var description: String = "",
    var isbn: String = ""
)

