package com.example.trail.data.DataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.edit

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name="settings")

class SettingsDataStore(private val context:Context){
    companion object{
        val API_KEY = stringPreferencesKey("api_key")
    }
    val apiKeyFlow : Flow<String> = context.dataStore.data.map {preferences ->
        preferences[API_KEY] ?: ""
    }
    suspend fun saveAPIKey(apiKey:String){
        context.dataStore.edit { preferences ->
            preferences[API_KEY] = apiKey
        }
    }
}