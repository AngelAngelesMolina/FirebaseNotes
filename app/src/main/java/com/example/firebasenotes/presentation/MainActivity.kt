package com.example.firebasenotes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.firebasenotes.presentation.navigation.NavManager
import com.example.firebasenotes.presentation.theme.FirebaseNotesTheme
import com.example.firebasenotes.presentation.viewmodels.LoginViewModel
import com.example.firebasenotes.presentation.viewmodels.NotesViewModel
import com.example.firebasenotes.presentation.views.login.TabsView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginVm : LoginViewModel by viewModels()
        val notesVm : NotesViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            FirebaseNotesTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    NavManager(loginVm, notesVm, it)
//                    TabsView(innerPadding)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FirebaseNotesTheme {
        Greeting("Android")
    }
}