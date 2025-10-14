package com.example.exampleapp.ui.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
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
fun RegisterScreen(onGo: () -> Unit ) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    // El estado para la contraseña (ya lo tenías)
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text("Regístrate")
        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.padding(horizontal = 16.dp))
        {
            Column(
                modifier = Modifier.padding(16.dp))
            {
                TextField(
                    value = name,
                    onValueChange = { newTextFieldValue ->
                        name = newTextFieldValue
                    },
                    label = { Text("Nombre") } // Cambié "Label" por "Usuario"
                )
                TextField(
                    value = username,
                    onValueChange = {
                        newTextFieldValue -> username = newTextFieldValue
                    },
                    label = {Text("Nombre de usuario")}
                )

                Spacer(Modifier.height(16.dp))

                PasswordTextField(
                    value = password,
                    onValueChange = { password = it },
                )

                Spacer(Modifier.height(24.dp)) // Un espacio mayor antes del botón

                // --- 2. AQUÍ AÑADIMOS EL BOTÓN ---
                Button(
                    onClick = {
                        // Cuando se hace clic, llamamos a la función `onGo` que recibimos como parámetro.
                        // Le pasamos el nombre de usuario como ejemplo.
                        onGo()
                    },
                    // Opcional: El botón solo se activa si ambos campos tienen texto.
                    enabled = name.text.isNotEmpty() && password.isNotEmpty() && username.text.isNotEmpty()
                ) {
                    Text("Registrar")
                }
            }
        }

    }

}