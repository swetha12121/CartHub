package com.example.carthub

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, cartItems: MutableState<List<Car>>) {
    val totalPrice = cartItems.value.sumOf { it.price }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF8D1414), // Deep Black
                            Color(0xFF12123D), // Charcoal
                            Color(0xFF212175), // Slate Gray
                            Color(0xFF483110)  // Gunmetal
                        )
                    )
                )
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Your Cart", color = Color.Black
                        , fontSize = 20.sp) },
                    navigationIcon = { BackButton(navController) },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Transparent)
                )
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    if (cartItems.value.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Your cart is empty!",
                                color = Color.White,
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }
                    } else {
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            items(cartItems.value) { car ->
                                CartItemCard(car = car, onRemove = {
                                    cartItems.value = cartItems.value.filterNot { it.id == car.id }
                                }) {
                                    navController.navigate("car_details/${car.id}")
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Total: \$${"%.2f".format(totalPrice)}",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.align(Alignment.End)
                    )

                    Button(
                        onClick = { navController.navigate("payment") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    ) {
                        Text("Proceed to Checkout")
                    }
                }
            },
            containerColor = Color.Transparent
        )
    }
}

@Composable
fun CartItemCard(car: Car, onRemove: () -> Unit, onViewDetails: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.85f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = car.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(text = "Price: \$${car.price}", style = MaterialTheme.typography.bodyMedium)
            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onViewDetails) {
                    Text("View Details")
                }
                IconButton(onClick = onRemove) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove")
                }
            }
        }
    }
}
