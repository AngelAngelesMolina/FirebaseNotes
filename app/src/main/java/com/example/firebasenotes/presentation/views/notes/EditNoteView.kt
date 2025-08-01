package com.example.firebasenotes.presentation.views.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firebasenotes.R
import com.example.firebasenotes.presentation.viewmodels.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteView(
    navController: NavController,
    notesVm: NotesViewModel,
    idDoc: String,
    pad: PaddingValues
) {
    LaunchedEffect(Unit) {
        notesVm.getNoteById(idDoc)
    }
    val state = notesVm.state
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar nota") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(painter = painterResource(R.drawable.ic_save), contentDescription = "")
                    }
                }
            )
        }

    ) { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = state.title,
                onValueChange = { notesVm.onValue(it, "title") }, label = {
                    Text(text = "Titulo")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            )
            OutlinedTextField(
                value = state.note,
                onValueChange = { notesVm.onValue(it, "note") }, label = {
                    Text(text = "Nota")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
            )

        }

    }

}
