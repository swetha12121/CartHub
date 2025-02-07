package com.example.carthub

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController, userViewModel: UserViewModel) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginPage(navController, userViewModel) }
        composable("register") { RegistrationPage(navController, userViewModel) }
        composable("home/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            HomePage(navController, userViewModel, userId)
        }
        composable("logout") { LogoutPage(navController, userViewModel) }
    }
}
