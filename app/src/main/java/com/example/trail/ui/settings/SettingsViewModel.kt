package com.example.trail.ui.settings

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.trail.BookApplication
import com.example.trail.data.RetrofitInstance
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
    val isValidating = mutableStateOf(false)
    val isError = mutableStateOf(false)
    val errorMessage = mutableStateOf("")

    fun onSaveApiChange() {
        viewModelScope.launch {
            isValidating.value = true
            isError.value = false
            try {
                val response = RetrofitInstance.api.searchBooks(
                    query = "test",
                    apiKey = apiKey.value,
                    number = 1
                )
                settingsDataStore.saveAPIKey(apiKey.value)
                isSaved.value = true
                isError.value = false
            } catch (e: Exception) {
                isError.value = true
                errorMessage.value = when {
                    e.message?.contains("401") == true -> "Invalid API key. Please check and try again."
                    e.message?.contains("402") == true -> "Your API Quota's limit reached.Try differnce API key"
                    e.message?.contains("timeout") == true -> "Connection timed out. Try again."
                    else -> "Could not validate API key. Please try again."
                }
            } finally {
                isValidating.value = false
            }
        }
    }

}