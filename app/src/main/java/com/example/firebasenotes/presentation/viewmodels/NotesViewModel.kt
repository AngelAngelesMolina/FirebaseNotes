package com.example.firebasenotes.presentation.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var state by mutableStateOf(NoteStates())
        private set

    fun onValue(value: String, text: String) { //actualizar nuestra data
        when (text) {
            "title" -> state = state.copy(title = value)
            "note" -> state = state.copy(note = value)
        }
    }

    fun getNoteById(idDoc: String) {
        firestore.collection("Notes").document(idDoc).addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (value != null && value.exists()) {

                val note = value.toObject(NoteStates::class.java)
                state = state.copy(
                    title = note?.title ?: "",
                    note = note?.note ?: "",
                )
            }
        }
    }

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
                        .copy(idDoc = document.id) //Se saca todo el documento incluyendo el id
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

    fun editNote(idDoc: String, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val editNote = hashMapOf(
                    "title" to state.title,
                    "note" to state.note,
                )
                firestore.collection("Notes").document(idDoc).update(editNote as Map<String, Any>)
                    .addOnSuccessListener {
                        onSuccess()
                    }

            } catch (e: Exception) {
                Log.d("ERROR EN EDIT", "Error al editar ${e.localizedMessage}")
            }
        }

    }
    fun deleteNote(idDoc: String, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                firestore.collection("Notes").document(idDoc).delete()
                    .addOnSuccessListener {
                        onSuccess()
                    }

            } catch (e: Exception) {
                Log.d("ERROR EN DELETE", "Error al eliminar ${e.localizedMessage}")
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