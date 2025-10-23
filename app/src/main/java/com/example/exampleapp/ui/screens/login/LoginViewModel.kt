package com.example.exampleapp.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// Estado para el proceso de inicio de sesión (muy similar al de registro)
sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                // 1. Llamar a la función de Firebase para iniciar sesión
                auth.signInWithEmailAndPassword(email, password).await()

                // 2. Si la llamada es exitosa (no lanza excepción), el inicio de sesión fue correcto
                _loginState.value = LoginState.Success

            } catch (e: Exception) {
                // 3. Si algo falla, capturamos la excepción y emitimos un estado de error
                val errorMessage = e.message ?: "Ocurrió un error desconocido."
                _loginState.value = LoginState.Error(errorMessage)
            }
        }
    }
}
