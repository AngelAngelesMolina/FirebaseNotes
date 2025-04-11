package com.example.firebasenotes.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class NotesViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    fun logOut() {
        auth.signOut() // Cerrar sesion en fb
    }

}