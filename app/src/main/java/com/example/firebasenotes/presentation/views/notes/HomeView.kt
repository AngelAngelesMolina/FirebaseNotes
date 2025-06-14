package com.example.firebasenotes.presentation.views.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.firebasenotes.presentation.components.CardNote
import com.example.firebasenotes.presentation.viewmodels.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, notesViewModel: NotesViewModel, pad: PaddingValues) {
    LaunchedEffect(Unit) {
        notesViewModel.fetchNotes()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
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
                },
                actions = {
                    IconButton(onClick = { navController.navigate("AddNoteView") }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "")
                    }
                }

            )
        }

    ) { pad ->
        Column(
            modifier = Modifier
                .padding(pad), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val notesData by notesViewModel.notesData.collectAsState()
            LazyColumn {
                items(notesData) { note ->
                    CardNote(title = note.title, note = note.note, date = note.date) {
                        navController.navigate("EditNoteView/${note.idDoc}")
                    }
                }
            }
        }
    }
}