package com.example.trail.ui.settings


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.OutlinedTextFieldDefaults
import com.example.trail.ui.theme.ChipFillColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    val settingsViewModel: SettingsViewModel = viewModel()
    Scaffold(topBar = {
        TopAppBar(
            title = {Text("Settings")},
            navigationIcon = {
                IconButton(onClick = {navController.popBackStack()}){
                    Icon(Icons.Default.ArrowBack,
                        contentDescription=("Back Arrow"))
                } })
    }) {innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(top=20.dp)
        ){
            Text(text = "API Key",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text( "Enter your BigBook API key from bigbookapi.com",
            fontSize = 12.sp,
            color = Color(0xFF8A7A6B))
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = settingsViewModel.apiKey.value,
            onValueChange = {settingsViewModel.onApiKeyChange(it)},
            modifier = Modifier.fillMaxWidth(),
            placeholder = {Text("Enter your API Key")},
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFC2542F)
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {settingsViewModel.onSaveApiChange()},
            modifier = Modifier.background(ChipFillColor)) {
            Text("Save API Key",color=Color.White)
            }
        if (settingsViewModel.isSaved.value) {
        Spacer(modifier = Modifier.height(8.dp))
        Text("✓ API key saved successfully", color = Color(0xFF2E8B57))
    }

    }

}