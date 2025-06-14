package com.example.firebasenotes.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firebasenotes.presentation.viewmodels.LoginViewModel
import com.example.firebasenotes.presentation.viewmodels.NotesViewModel
import com.example.firebasenotes.presentation.views.login.BlankView
import com.example.firebasenotes.presentation.views.login.TabsView
import com.example.firebasenotes.presentation.views.notes.AddNoteView
import com.example.firebasenotes.presentation.views.notes.EditNoteView
import com.example.firebasenotes.presentation.views.notes.HomeView

@Composable
fun NavManager(loginVm: LoginViewModel, notesVm: NotesViewModel, pad: PaddingValues) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "Blank") {
        composable("Blank") {
            BlankView(navController)
        }
        composable("Login") {
            TabsView(loginVm, navController, pad)
        }
        composable("Home") {
            HomeView(navController, notesVm, pad)
        }
        composable("AddNoteView") {
            AddNoteView(navController, notesVm)
        }
        composable(
            "EditNoteView/{idDoc}", arguments = listOf(
            navArgument("idDoc") { type = NavType.StringType }
        )) {
            val idDoc = it.arguments?.getString("idDoc") ?: ""
            EditNoteView(navController, notesVm, idDoc = idDoc, pad)
        }
    }
}