package com.example.carthub

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    navController: NavController, cartItems: List<Car>, onPaymentSuccess: () -> Unit) {
    val totalAmount = cartItems.sumOf { it.price }
    var cardHolderName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var selectedPaymentMethod by remember { mutableStateOf("Credit Card") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payment") },
                navigationIcon = { BackButton(navController) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Payment Summary", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(cartItems) { car ->
                    Text(text = "${car.name} - \$${car.price}")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Total: \$${totalAmount}", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Select Payment Method", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                RadioButton(
                    selected = selectedPaymentMethod == "Credit Card",
                    onClick = { selectedPaymentMethod = "Credit Card" })
                Text("Credit Card", modifier = Modifier.padding(start = 8.dp))
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = selectedPaymentMethod == "PayPal",
                    onClick = { selectedPaymentMethod = "PayPal" })
                Text("PayPal", modifier = Modifier.padding(start = 8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = mobileNumber,
                onValueChange = { mobileNumber = it },
                label = { Text("Mobile Number") },
                modifier = Modifier.fillMaxWidth()
            )

            if (selectedPaymentMethod == "Credit Card") {
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = cardHolderName,
                    onValueChange = { cardHolderName = it },
                    label = { Text("Card Holder Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = cardNumber,
                    onValueChange = { cardNumber = it },
                    label = { Text("Card Number") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = expiryDate,
                    onValueChange = { expiryDate = it },
                    label = { Text("Expiry Date (MM/YY)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = cvv,
                    onValueChange = { cvv = it },
                    label = { Text("CVV") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onPaymentSuccess() // ✅ Clear cart before navigating
                    if (selectedPaymentMethod == "Credit Card") {
                        navController.navigate("payment_success/$mobileNumber")
                    } else {
                        navController.navigate("paypal_payment/$mobileNumber")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Proceed to Pay")
            }
        }
    }
}