// En ui/screens/login/LoginScreen.kt
package com.example.exampleapp.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel // No olvides importar
import com.example.exampleapp.ui.components.PasswordTextField

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit, // Callback para navegar a Home
    onGoToRegister: () -> Unit,  // Callback para navegar a Registro
    loginViewModel: LoginViewModel = viewModel() // 1. Inyectar el LoginViewModel
) {
    // Estados para los campos de texto
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // 2. Observar el estado de inicio de sesión desde el ViewModel
    val loginState by loginViewModel.loginState.collectAsState()

    // 3. Efecto para navegar cuando el inicio de sesión sea exitoso
    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(Modifier.height(16.dp))
            PasswordTextField(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña"
            )
        }

        Spacer(Modifier.height(24.dp))

        // 4. La UI reacciona al estado del ViewModel
        when (val state = loginState) {
            is LoginState.Loading -> {
                CircularProgressIndicator()
            }
            is LoginState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(8.dp))
                LoginButton(
                    enabled = email.isNotEmpty() && password.isNotEmpty(),
                    onClick = { loginViewModel.loginUser(email, password) }
                )
            }
            else -> { // Idle o Success
                LoginButton(
                    enabled = email.isNotEmpty() && password.isNotEmpty(),
                    onClick = { loginViewModel.loginUser(email, password) }
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        TextButton(onClick = onGoToRegister) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}

// Botón extraído para evitar repetición
@Composable
private fun LoginButton(enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text("Iniciar Sesión")
    }
}
