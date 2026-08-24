package com.example.trail

import android.app.Application
import com.example.trail.shared.appContext
import com.example.trail.shared.getDatabaseBuilder
import com.example.trail.data.Repository.BookRepository
import com.example.trail.data.DataStore.SettingsDataStore
import com.example.trail.shared.BookApiService
import com.example.trail.shared.createHttpClient

class BookApplication : Application() {

    val database by lazy { getDatabaseBuilder().build() }
    val repository by lazy { BookRepository(database) }

    val settingsDataStore by lazy { SettingsDataStore(this) }

    val bookApiService by lazy {
        BookApiService(createHttpClient())
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this  // set Android context for Room to use
    }
}
