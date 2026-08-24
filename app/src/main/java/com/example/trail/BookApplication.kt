package com.example.trail

import android.app.Application
import com.example.trail.shared.AppDatabase
import com.example.trail.data.Repository.BookRepository
import com.example.trail.data.DataStore.SettingsDataStore
import com.example.trail.shared.BookApiService
import com.example.trail.shared.createHttpClient

class BookApplication : Application() {

    val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy { BookRepository(database) }

    val settingsDataStore by lazy { SettingsDataStore(this) }

    val bookApiService by lazy {
        BookApiService(createHttpClient())
    }
}
