package com.example.trail.data

data class BookModel(
    var bookName: String,
    var authorName: String,
    var bookPages: Int,
    var year: String,           // onsale date
    var coverColor: Long,       // custom — not in API
    var format: String = "Hardcover",
    var price: String = "\$9.99",
    var description: String = "",
    var authorBio: String = "",
    var imprint: String = "",
    var division: String = "",
    var isbn: String = "",
    var category: String = ""
)
