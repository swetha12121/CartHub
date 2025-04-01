package com.example.carthub

import NavGraph
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("CarHubPrefs", Context.MODE_PRIVATE) // ✅ FIXED
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)
        val userId = sharedPref.getInt("userId", -1)

        val startDestination = if (isLoggedIn && userId != -1) "home/$userId" else "login"

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

        setContent {
            val navController = rememberNavController()
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                NavGraph(
                    navController = navController,
                    userViewModel = userViewModel,
                    startDestination = startDestination // ✅ PASS IT HERE
                )
            }
        }
    }
}
