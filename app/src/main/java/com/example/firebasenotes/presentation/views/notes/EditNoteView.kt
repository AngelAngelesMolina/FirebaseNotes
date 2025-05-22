package com.example.firebasenotes.presentation.views.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.firebasenotes.presentation.viewmodels.NotesViewModel

@Composable
fun EditNoteView(navController: NavController, notesVm: NotesViewModel, idDoc: String, pad: PaddingValues) {
    Column(modifier = Modifier.fillMaxSize().padding(pad)){
        Text(text = idDoc)
    }

}