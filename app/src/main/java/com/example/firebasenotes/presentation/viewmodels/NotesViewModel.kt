package com.example.firebasenotes.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenotes.data.models.NoteStates
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class NotesViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore: FirebaseFirestore = Firebase.firestore

    private val _notesData = MutableStateFlow<List<NoteStates>>(emptyList())
    val notesData: StateFlow<List<NoteStates>> = _notesData

    fun fetchNotes() {
        val email = auth.currentUser?.email

        firestore.collection("Notes").whereEqualTo("emailUser", email)
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val documents = mutableListOf<NoteStates>()
                querySnapshot?.forEach { document ->
                    val document = document.toObject(NoteStates::class.java)
                        .copy(idDoc = document.id) //Se saca todo el documento
                    documents.add(document)
                }
                _notesData.value = documents
            }
    }

    fun saveNewNote(title: String, note: String, onSuccess: () -> Unit) {
        val email = auth.currentUser?.email

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newNote = hashMapOf(
                    "title" to title,
                    "note" to note,
                    "date" to formatDate(),
                    "emailUser" to email.toString()
                )
                firestore.collection("Notes").add(newNote).addOnSuccessListener {
                    onSuccess()
                }

            } catch (e: Exception) {
                Log.d("ERROR SAVE", "Error al guardar ${e.localizedMessage}")
            }
        }

    }

    private fun formatDate(): String {
        val currentDate: Date = Calendar.getInstance().time
        val res = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return res.format(currentDate)
    }

    fun logOut() {
        auth.signOut() // Cerrar sesion en fb
    }

}