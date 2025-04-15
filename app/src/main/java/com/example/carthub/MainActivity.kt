package com.example.carthub

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Access SharedPreferences to check login and role info
        val sharedPref = getSharedPreferences("CarHubPrefs", Context.MODE_PRIVATE)
        val userRole = sharedPref.getString("userRole", null)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)
        val userId = sharedPref.getInt("userId", -1)
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        val startDestination = when {
            userRole == null -> "roleSelection"  // First time
            !isLoggedIn && userRole == "seller" -> "sellerLogin"
            !isLoggedIn && userRole == "buyer" -> "login"
            else -> "home/$userRole"
        }



        // Setup Room DB
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "user_database"
        )
            .addMigrations(MIGRATION_2_3)
            .fallbackToDestructiveMigration()
            .build()

        val userDao = database.userDao()
        val userViewModel = UserViewModel(userDao)

        // Set up UI with navigation
        setContent {
            val navController = rememberNavController()
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                NavGraph(
                    navController = navController,
                    userViewModel = userViewModel,
                    startDestination = startDestination,
                    context = this // Pass context to use in RoleSelectionScreen
                )
            }
        }
    }
}

