package com.example.trail.shared

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "userBookStateEntity")
data class UserBookStateEntity(
    @PrimaryKey val bookId: Long,
    var readStatus: String = ReadStatus.NONE.name,
    var isFavourite: Boolean = false
)