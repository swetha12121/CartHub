package com.example.carthub.Seller

import androidx.compose.ui.Alignment
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.carthub.R
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerLoginPage(navController: NavController) {
    val context = LocalContext.current

    var sellerId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.slogin), // Add your image to drawable
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp, vertical = 140.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Seller Login",
                fontSize = 26.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = sellerId,
                onValueChange = { sellerId = it },
                label = { Text("Seller ID", color = Color.White) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = outlinedTextFieldColors()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = outlinedTextFieldColors()
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = showPassword, onCheckedChange = { showPassword = it })
                Text("Show Password", color = Color.White)
            }

            errorMessage?.let {
                Text(it, color = Color.Red, modifier = Modifier.padding(4.dp))
            }

            Button(
                onClick = {
                    if (sellerId.isBlank() || password.isBlank()) {
                        errorMessage = "Please fill all fields"
                    } else {
                        val firebaseEmail = "${sellerId.trim()}@carhub.com"
                        FirebaseAuth.getInstance()
                            .signInWithEmailAndPassword(firebaseEmail, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    context.getSharedPreferences("CarHubPrefs", 0)
                                        .edit()
                                        .putBoolean("isLoggedIn", true)
                                        .putString("loggedInSellerId", sellerId)
                                        .apply()

                                    navController.navigate("home/seller") {
                                        popUpTo("sellerLogin") { inclusive = true }
                                    }
                                } else {
                                    errorMessage =
                                        "Login failed: ${task.exception?.localizedMessage}"
                                }
                            }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Don't have an account? Register",
                color = Color.White,
                modifier = Modifier.clickable {
                    navController.navigate("sellerRegister")
                }
            )
        }
    }
}
