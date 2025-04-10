package com.example.carthub.Seller

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController

fun navigateIfBuyer(context: Context, navController: NavController, destination: String) {
    val role = context.getSharedPreferences("CarHubPrefs", 0).getString("userRole", "buyer")
    if (role == "buyer") {
        navController.navigate(destination)
    } else {
        Toast.makeText(context, "Sellers cannot access this page.", Toast.LENGTH_SHORT).show()
    }
}

fun isUserBuyer(context: Context): Boolean {
    val role = context.getSharedPreferences("CarHubPrefs", 0).getString("userRole", "buyer")
    return role == "buyer"
}
