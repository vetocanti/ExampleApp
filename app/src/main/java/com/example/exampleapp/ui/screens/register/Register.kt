package com.example.exampleapp.ui.screens.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.exampleapp.ui.components.PasswordTextField

@Composable
fun RegisterScreen(
    // 1. Renombrado para mayor claridad: esta acción se ejecuta cuando hay que ir a Login.
    onGo: () -> Unit,
    registerViewModel: RegisterViewModel = viewModel()
) {
    // 2. Simplificamos el estado de los campos a String. Es más directo.
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") } // Es crucial para un buen registro.

    // 3. Observamos el estado del proceso de registro desde el ViewModel.
    //    `collectAsState` hace que la UI se recomponga automáticamente cuando este estado cambia.
    val registrationState by registerViewModel.registrationState.collectAsState()

    // 4. Usamos LaunchedEffect para manejar la navegación como un "efecto secundario".
    //    Esto se ejecuta solo cuando `registrationState` cambia a `Success`.
    //    Evita la navegación en cada recomposición.
    LaunchedEffect(registrationState) {
        if (registrationState is RegistrationState.Success) {
            onGo()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crear nueva cuenta", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        // No es necesaria la Card exterior, podemos aplicar padding directamente.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nombre de usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            PasswordTextField(
                value = password,
                onValueChange = { password = it },
                label = "Contraseña"
            )

            Spacer(Modifier.height(16.dp))

            PasswordTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirmar contraseña"
            )
        }

        Spacer(Modifier.height(24.dp))

        // 5. La UI reacciona al estado del ViewModel.
        when (val state = registrationState) {
            is RegistrationState.Loading -> {
                // Muestra un indicador de carga mientras se procesa el registro.
                CircularProgressIndicator()
            }
            is RegistrationState.Error -> {
                // Muestra el mensaje de error si algo falla.
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(8.dp))
                // Volvemos a mostrar el botón para que el usuario pueda intentarlo de nuevo.
                RegisterButton(
                    enabled = username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword,
                    onClick = { registerViewModel.registerUser(email, password, username) }
                )
            }
            else -> {
                // Por defecto (estado Idle o Success), muestra el botón.
                RegisterButton(
                    enabled = username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword,
                    onClick = { registerViewModel.registerUser(email, password, username) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onGo) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}

// 6. (Opcional pero recomendado) Extraer el botón a su propio Composable para no repetir código.
@Composable
private fun RegisterButton(enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text("Registrarse")
    }
}
