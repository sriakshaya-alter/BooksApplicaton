package com.example.trail.shared

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class BookApiService(private val client: HttpClient) {

    private val baseUrl = "https://api.bigbookapi.com"

    suspend fun searchBooks(
        query: String,
        apiKey: String,
        number: Int = 20,
        minRating: Float? = null
    ): BookSearchResponse {
        return client.get("$baseUrl/search-books") {
            parameter("query", query)
            parameter("api-key", apiKey)
            parameter("number", number)
            minRating?.let { parameter("min-rating", it) }
        }.body()
    }

    suspend fun getBookDetails(bookId: Long, apiKey: String): BookDetailApiModel {
        return client.get("$baseUrl/$bookId") {
            parameter("api-key", apiKey)
        }.body()
    }
}
