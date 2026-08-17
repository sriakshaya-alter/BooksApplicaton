package com.example.trail.data

enum class BookFilter(val displayName: String) {
    ALL("All"),
    TOP_RATED("Top Rated"),
    HIGHLY_RATED("Highly Rated"),
    GOOD("Good");
    fun matches(book: BookModel): Boolean = when (this) {
        ALL          -> true
        TOP_RATED    -> book.rating > 0.89
        HIGHLY_RATED -> book.rating in 0.86..0.88
        GOOD         -> book.rating < 0.86
    }
}
