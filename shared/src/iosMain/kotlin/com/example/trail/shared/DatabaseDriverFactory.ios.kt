package com.example.trail.shared

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = NSHomeDirectory() + "/books_database"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath
    )
}
