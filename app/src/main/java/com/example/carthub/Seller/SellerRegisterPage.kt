package com.example.carthub.Seller

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
fun SellerRegistrationPage(navController: NavController) {
    val context = LocalContext.current

    var sellerId by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
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
                "Seller Registration",
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
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.White) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White
                )
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name", color = Color.White) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White
                )
            )


            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Checkbox(
                    checked = showPassword,
                    onCheckedChange = { showPassword = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.White,
                        uncheckedColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Show Password", color = Color.White)
            }

            errorMessage?.let {
                Text(it, color = Color.Red, modifier = Modifier.padding(4.dp))
            }

            Button(
                onClick = {
                    if (sellerId.length < 4) {
                        errorMessage = "Seller ID must be at least 4 characters"
                    } else if (password.length < 6) {
                        errorMessage = "Password must be at least 6 characters"
                    } else {
                        val firebaseEmail = "${sellerId.trim()}@carhub.com"
                        FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(firebaseEmail, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Registered successfully!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.navigate("sellerLogin") {
                                        popUpTo("sellerRegister") { inclusive = true }
                                    }
                                } else {
                                    errorMessage = "Error: ${task.exception?.localizedMessage}"
                                }
                            }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }
        }
    }
}
