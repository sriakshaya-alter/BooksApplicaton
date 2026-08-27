package com.example.trail.shared

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val bookId:Long,
    val bookName : String,
    val authorName : String,
    val rating:Double,
    val coverColor:Long,
    val bookPages:Int =0,
    val year: String="",
    val description:String="",
    val isbn:String="",
    val isDetailsFetched:Boolean=false
)