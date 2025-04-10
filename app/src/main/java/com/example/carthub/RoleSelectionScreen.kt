package com.example.carthub

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RoleSelectionScreen(context: Context, onRoleSelected: (String) -> Unit) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF805616), Color(0xFF565E2B), Color(0xFF5E1320))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "Role Icon",
                    modifier = Modifier.size(200.dp)
                )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Welcome to CartHub " +
                        "\n        who are you?",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(40.dp))

            RoleOptionCard("Buyer", R.drawable.ic_buyer, onClick = {
                onRoleSelected("buyer")
            })

            Spacer(modifier = Modifier.height(40.dp))

            RoleOptionCard("Seller", R.drawable.ic_seller, onClick = {
                onRoleSelected("seller")
            })
        }
    }
}

@Composable
fun RoleOptionCard(role: String, iconRes: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = "$role icon",
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = role,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.White
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Select $role",
                tint = Color.White
            )
        }
    }
}
