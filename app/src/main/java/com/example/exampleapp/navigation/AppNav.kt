package com.example.exampleapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exampleapp.ui.screens.detail.DetailScreen
import com.example.exampleapp.ui.screens.home.HomeScreen
import com.example.exampleapp.ui.screens.login.LoginScreen
import com.example.exampleapp.ui.screens.register.RegisterScreen

@Composable
fun AppNav() {
    val nav = rememberNavController()
    // La pantalla de inicio es "login", lo cual es correcto.
    NavHost(navController = nav, startDestination = "login") {

        // --- Definición de la ruta "login" ---
        // Aquí es donde el usuario comienza.
        composable(route = "login") {
            // 2. LLAMAR a LoginScreen, no a HomeScreen.
            //    La lambda `onGo` se activa cuando el botón es presionado.
            LoginScreen(
                onGo = { username -> // El 'username' viene desde LoginScreen
                    // 3. Al hacer clic, navega a "home".
                    nav.navigate("home")
                },
                onGoToRegister = { nav.navigate("register") }
            )
        }

        // --- Definición de la ruta "home" ---
        // El usuario llega aquí después de un login exitoso.
        composable(route = "home") {
            // Llamamos a HomeScreen y le pasamos la acción para ir al detalle.
            HomeScreen(
                onGo = { id -> // 'id' es el identificador del item en HomeScreen
                    nav.navigate("detalle/$id")
                }
            )
        }

        // --- Definición de la ruta "detalle/{id}" ---
        // El usuario llega aquí desde HomeScreen.
        composable(route = "detalle/{id}") { backStackEntry ->
            // Se obtiene el 'id' de los argumentos de la ruta.
            val id = backStackEntry.arguments?.getString("id")
            // Es buena práctica manejar el caso en que el id sea nulo.
            requireNotNull(id) { "El ID no puede ser nulo" }
            DetailScreen(id = id)
        }

        composable(route = "register") {
            RegisterScreen(onGo= { nav.navigate("login") {
                popUpTo("login") { inclusive = true}
            } })
        }
    }
}
