package com.example.firebasenotes.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebasenotes.presentation.viewmodels.LoginViewModel
import com.example.firebasenotes.presentation.viewmodels.NotesViewModel
import com.example.firebasenotes.presentation.views.login.TabsView
import com.example.firebasenotes.presentation.views.note.HomeView

@Composable
fun NavManager(loginVm: LoginViewModel, notesVm: NotesViewModel, pad: PaddingValues) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "Login") {
        composable("Login") {
            TabsView(loginVm, navController, pad)
        }
        composable("Home") {
            HomeView()
        }
    }
}