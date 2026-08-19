package com.example.trail.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.example.trail.data.ReadStatus

@Dao
interface UserBookStateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookStatus(bookStatus: List<UserBookStateEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceBookStatus(bookStatus: UserBookStateEntity)

    @Query("SELECT * FROM userBookStateEntity")
    suspend fun getAllBookStatus(): List<UserBookStateEntity>

    @Query("SELECT COUNT(*) FROM userBookStateEntity WHERE readStatus = 'READ'")
    suspend fun getReadBooksCount(): Int

    @Query("SELECT COUNT(*) FROM userBookStateEntity WHERE readStatus = 'WANT_TO_READ'")
    suspend fun getWantToReadBooksCount(): Int

    @Query("SELECT COUNT(*) FROM userBookStateEntity WHERE isFavourite = 1")
    suspend fun getFavouriteCount(): Int

    @Query("SELECT * FROM userBookStateEntity WHERE bookId = :bookId")
    suspend fun getBookStateById(bookId: Long): UserBookStateEntity?

    @Query("DELETE FROM userBookStateEntity")
    suspend fun clearAll()
}
