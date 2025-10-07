package com.example.exampleapp.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exampleapp.ui.screens.detail.DetailScreen
import com.example.exampleapp.ui.screens.home.HomeScreen

@Composable
fun AppNav() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "home") {
        composable("home") {
            HomeScreen(onGo = { id -> nav.navigate("detalle/$id") })
        }
        composable("detalle/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: "0"
            DetailScreen(id)
        }
    }
}
