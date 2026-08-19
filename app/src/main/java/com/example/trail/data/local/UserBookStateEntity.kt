package com.example.trail.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trail.data.ReadStatus

@Entity(tableName = "userBookStateEntity")
data class UserBookStateEntity(
    @PrimaryKey val bookId: Int,
    var readStatus: String = ReadStatus.NONE.name,
    var isFavourite: Boolean = false
)
