package com.example.trail

import android.app.Application
import com.example.trail.data.local.AppDatabase
import com.example.trail.data.Repository.BookRepository

class BookApplication : Application() {

    val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy { BookRepository(database) }
}
