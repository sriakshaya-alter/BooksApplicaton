package com.example.trail.shared


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserBookStateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookStatus(bookStatus: List<UserBookStateEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceBookStatus(bookStatus: UserBookStateEntity)

    @Query("SELECT * FROM userBookStateEntity")
    suspend fun getAllBookStatus(): List<UserBookStateEntity>

    @Query("SELECT * FROM userBookStateEntity WHERE bookId = :bookId")
    suspend fun getBookStateById(bookId: Long): UserBookStateEntity?

    @Query("DELETE FROM userBookStateEntity")
    suspend fun clearAll()
}