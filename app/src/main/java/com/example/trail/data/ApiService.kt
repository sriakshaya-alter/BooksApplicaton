package com.example.trail.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

interface BigBookApiService{
    @GET("search-books")
    suspend fun searchBooks(
        @Query("query") query:String,
        @Query("api-key") apikey: String = "6ba06e89965e4ac89b3224a4e68dbcb7",
       @Query("number") number : Int = 10
    ):BookSearchResponse

    @GET("{book-id}")
    suspend fun getBookDetails(
        @Query("query") bookId : Long,
        @Query("api-key") apiKey: String = "6ba06e89965e4ac89b3224a4e68dbcb7"
    ): BookDetailApiModel
}

object RetrofitInstance {
    private const val BASE_URL = "https://api.bigbookapi.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)   // ← increase timeout
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val api: BigBookApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)              // ← add client
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BigBookApiService::class.java)
    }
}