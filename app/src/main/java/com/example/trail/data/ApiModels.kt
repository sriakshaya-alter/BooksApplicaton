package com.example.trail.data


data class BookSearchResponse(
    val books: List<List<BookApiModel>>,
    val available: Int,
    val number: Int,
    val offset: Int
)
data class BookApiModel(
    val id: Long,
    val title: String,
    val image: String? = null,
    val authors: List<AuthorApiModel>? = null,
    val rating: RatingApiModel? = null
)
data class BookDetailApiModel(
    val id: Long,
    val title: String,
    val image: String? = null,
    val authors: List<AuthorApiModel>? = null,
    val publish_date: Double? = null,
    val number_of_pages: Double? = null,
    val description: String? = null,
    val rating: RatingApiModel? = null,
    val identifiers: IdentifiersApiModel? = null
)
data class AuthorApiModel(
    val id: Long,
    val name: String
)
data class RatingApiModel(
    val average: Double
)
data class IdentifiersApiModel(
    val isbn_13: String? = null,
    val isbn_10: String? = null
)
