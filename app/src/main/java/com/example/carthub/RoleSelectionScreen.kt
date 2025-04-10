package com.example.carthub

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RoleSelectionScreen(
    context: Context,
    onRoleSelected: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to CartHub",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Are you a buyer or a seller?",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(36.dp))
            Button(
                onClick = { onRoleSelected("buyer") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("I'm a Buyer")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onRoleSelected("seller") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("I'm a Seller")
            }
        }
    }
}
