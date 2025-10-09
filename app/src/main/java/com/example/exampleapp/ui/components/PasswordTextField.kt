package com.example.exampleapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation


/**
 * Un campo de texto de estilo contraseña con un ícono para mostrar/ocultar el contenido.
 *
 * @param value El valor actual del campo de texto.
 * @param onValueChange Callback que se ejecuta cuando el valor cambia.
 * @param modifier El modificador a aplicar al campo de texto.
 * @param label El texto que se muestra como etiqueta del campo.
 */
@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Contraseña"
) {
    // 1. Estado para controlar si la contraseña es visible o no.
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text(label) },
        singleLine = true,
        // 2. Transforma visualmente el texto a puntos o lo muestra normalmente.
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None // Muestra el texto
        } else {
            PasswordVisualTransformation() // Oculta el texto con '●'
        },
        // 3. Define el tipo de teclado.
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        // 4. Ícono al final del campo para cambiar la visibilidad.
        trailingIcon = {
            val image = if (isPasswordVisible) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            val description = if (isPasswordVisible) {
                "Ocultar contraseña"
            } else {
                "Mostrar contraseña"
            }

            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(imageVector = image, contentDescription = description)
            }
        }
    )
}
