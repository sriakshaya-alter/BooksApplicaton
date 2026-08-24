package com.example.trail.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import com.example.trail.shared.BookDetailApiModel
import com.example.trail.shared.BookSearchResponse

interface BigBookApiService{

    @GET("search-books")
    suspend fun searchBooks(
        @Query("query") query: String,
        @Query("api-key") apiKey: String, //= BuildConfig.API_KEY,
        @Query("number") number: Int = 20,
        @Query("sort") sort: String = "rating",
        @Query("min-rating") minRating: Float? = null
    ): BookSearchResponse

    @GET("{bookId}")
    suspend fun getBookDetails(
        @Path("bookId") bookId : Long,
        @Query("api-key") apiKey: String, //= BuildConfig.API_KEY,
    ): BookDetailApiModel
}

object RetrofitInstance {
    private const val BASE_URL = "https://api.bigbookapi.com/"
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val api: BigBookApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BigBookApiService::class.java)
    }
}