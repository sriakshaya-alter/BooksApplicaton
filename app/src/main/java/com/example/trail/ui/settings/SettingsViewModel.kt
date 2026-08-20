package com.example.trail.ui.settings

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.trail.BookApplication
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class SettingsViewModel (application: Application) : AndroidViewModel(application){

    private val settingsDataStore = (application as BookApplication).settingsDataStore

    val apiKey = mutableStateOf("")
    val isSaved = mutableStateOf(false)

    init{
        viewModelScope.launch{
            apiKey.value = settingsDataStore.apiKeyFlow.first()
        }
    }
    fun onApiKeyChange(key:String){
        apiKey.value = key
        isSaved.value = false
    }
    fun onSaveApiChange(){
        viewModelScope.launch{
            settingsDataStore.saveAPIKey(apiKey.value)
            isSaved.value = true
        }
    }
}