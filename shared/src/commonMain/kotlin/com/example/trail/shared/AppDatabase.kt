package com.example.trail.shared

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BookEntity::class, UserBookStateEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun userBookStateDao(): UserBookStateDao
}
