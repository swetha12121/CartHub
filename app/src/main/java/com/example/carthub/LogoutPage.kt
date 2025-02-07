package com.example.carthub

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogoutPage(navController: NavController, userViewModel: UserViewModel) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Logout") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Are you sure you want to logout?", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    userViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true } // ✅ Clears home from stack to prevent back navigation
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = {
                    navController.popBackStack("home/1", inclusive = false) // ✅ Fixes crashing issue
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel")
            }
        }
    }
}
