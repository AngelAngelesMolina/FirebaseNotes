package com.example.firebasenotes.presentation.views.note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.firebasenotes.presentation.viewmodels.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, notesViewModel: NotesViewModel, pad: PaddingValues) {

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Mis notas")
            },
                navigationIcon = {
                    IconButton(onClick = {
                        notesViewModel.logOut()
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = ""
                        )
                    }
                }

            )
        }

    ) { pad ->
        Column(
            modifier = Modifier
//                .fillMaxSize()
                .padding(pad), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "HomeView")
        }
    }
}