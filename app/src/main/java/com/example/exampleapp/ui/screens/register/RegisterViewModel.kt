package com.example.exampleapp.ui.screens.register


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// Representa el estado del proceso de registro
sealed class RegistrationState {
    object Idle : RegistrationState()
    object Loading : RegistrationState()
    object Success : RegistrationState()
    data class Error(val message: String) : RegistrationState()
}

class RegisterViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    val registrationState = _registrationState.asStateFlow()

    fun registerUser(email: String, password: String, username: String) {
        viewModelScope.launch {
            _registrationState.value = RegistrationState.Loading
            try {
                // 1. Autenticar y crear el usuario en Firebase Authentication
                val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                val firebaseUser = authResult.user

                if (firebaseUser != null) {
                    // 2. Crear el documento del usuario en Firestore
                    val userMap = hashMapOf(
                        "username" to username,
                        "email" to email
                    )
                    firestore.collection("users").document(firebaseUser.uid).set(userMap).await()

                    _registrationState.value = RegistrationState.Success
                } else {
                    _registrationState.value = RegistrationState.Error("No se pudo crear el usuario.")
                }
            } catch (e: Exception) {
                // Manejar errores comunes
                val errorMessage = e.message ?: "Ocurrió un error desconocido."
                _registrationState.value = RegistrationState.Error(errorMessage)
            }
        }
    }
}
