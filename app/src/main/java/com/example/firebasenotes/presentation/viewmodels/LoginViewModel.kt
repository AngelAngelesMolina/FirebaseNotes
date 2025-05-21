package com.example.firebasenotes.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.data.models.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    var showAlert by mutableStateOf(false)
    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        Log.d("ERROR EN FIREBASE", "Usuario y password incorrecto")
                        showAlert = true
                    }
                }
            } catch (e: Exception) {
                Log.d("ERROR EN JETPACK", "ERROR: ${e.localizedMessage}")
            }
        }
    }

    fun createUser(email: String, password: String, username: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        saveUser(username)
                        onSuccess()
                    } else {
                        Log.d("ERROR EN FIREBASE", "Error al crear usr")
                        showAlert = true
                    }
                }
            } catch (e: Exception) {
                Log.d("ERROR EN JETPACK", "ERROR: ${e.localizedMessage}")
            }
        }
    }

    private fun saveUser(username: String) {
        val id = auth.currentUser?.uid
        val email = auth.currentUser?.email

        viewModelScope.launch(Dispatchers.IO) {
            val user = UserModel(userId = id.toString(), email = email.toString(), userName = username)
            FirebaseFirestore.getInstance().collection("Users")
                .add(user)
                .addOnSuccessListener {
                    Log.d("GUARDADO!", "SE GUARDO CORRECTAMENTE EN FIRESTORE")

                }
                .addOnFailureListener {
                    Log.d("ERROR AL GUARDAR", "ERROR AL GUARDAR EN FIRESTORE")
                }
        }
    }


    fun closeAlert() {
        showAlert = false
    }

}