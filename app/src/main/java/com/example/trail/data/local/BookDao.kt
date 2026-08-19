package com.example.trail.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BookDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBooks(books : List<BookEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrReplaceBook(book : BookEntity)

    @Update
    suspend fun updateBook(book: BookEntity)

    @Query("SELECT * FROM books")
    suspend fun getAllBooks():List<BookEntity>

    @Query("SELECT * FROM books where bookId = :bookId")
    suspend fun getBookById(bookId:Long)

    @Query("SELECT COUNT(*) FROM books")
    suspend fun getBookCount(): Int

    @Query("DELETE FROM books")
    suspend fun clearAllBooks()
}
