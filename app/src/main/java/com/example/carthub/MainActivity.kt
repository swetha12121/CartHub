package com.example.carthub

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

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "user_database"
        )
            .addMigrations(MIGRATION_2_3) // ✅ Apply migration to avoid data loss
            .fallbackToDestructiveMigration() // ✅ Auto-delete old database if migration fails
            .build()

        val userDao = database.userDao()
        val userViewModel = UserViewModel(userDao)

        setContent {
            val navController = rememberNavController()
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                NavGraph(navController, userViewModel)
            }
        }
    }
}
