package com.example.exampleapp.ui.screens.login // Asegúrate de que el paquete sea el correcto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button // <-- 1. Importa el componente Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.exampleapp.ui.components.PasswordTextField

@Composable
fun LoginScreen(onGo: (String) -> Unit, onGoToRegister: () -> Unit) {
    // El estado para el campo de usuario (ya lo tenías)
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    // El estado para la contraseña (ya lo tenías)
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Iniciar sesión")

        Spacer(Modifier.height(16.dp))

        // Tarjeta para agrupar los campos de texto
        Card(
            // Añadimos un poco de padding dentro de la tarjeta para que no esté tan pegado
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Column(
                // Un Column dentro de la Card para organizar los elementos verticalmente
                // con un padding interno
                modifier = Modifier.padding(16.dp)
            ) {
                TextField(
                    value = textState,
                    onValueChange = { newTextFieldValue ->
                        textState = newTextFieldValue
                    },
                    label = { Text("Usuario") } // Cambié "Label" por "Usuario"
                )

                Spacer(Modifier.height(16.dp))

                PasswordTextField(
                    value = password,
                    onValueChange = { password = it }
                )
            }
        }

        Spacer(Modifier.height(24.dp)) // Un espacio mayor antes del botón

        // --- 2. AQUÍ AÑADIMOS EL BOTÓN ---
        Button(
            onClick = {
                // Cuando se hace clic, llamamos a la función `onGo` que recibimos como parámetro.
                // Le pasamos el nombre de usuario como ejemplo.
                onGo(textState.text)
            },
            // Opcional: El botón solo se activa si ambos campos tienen texto.
            enabled = textState.text.isNotEmpty() && password.isNotEmpty()
        ) {
            Text("Ir a Home")
        }
        TextButton(onClick = {
            onGoToRegister()
        /* Aquí necesitamos un callback para ir a registro */ }) {

            Text("¿No tienes cuenta? Regístrate")
        }
    }
}
