package com.example.carthub

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.carthub.Seller.isUserBuyer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarListScreen(navController: NavController, cartItems: MutableState<List<Car>>) {
    val context = LocalContext.current

    // 🔐 Restrict sellers from viewing car list
    if (!isUserBuyer(context)) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, "Sellers cannot access this page", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
        return
    }

    var searchQuery by remember { mutableStateOf("") }
    val cars = CarRepository.getCars().filter { it.name.contains(searchQuery, ignoreCase = true) }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF8D1414),
                            Color(0xFF12123D),
                            Color(0xFF212175),
                            Color(0xFF483110)
                        )
                    )
                )
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("Car List", color = Color.Black) },
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
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search for cars...", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        unfocusedContainerColor = Color.White.copy(alpha = 0.2f),
                        focusedContainerColor = Color.White.copy(alpha = 0.2f)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(cars) { car ->
                        CarItem(
                            car = car,
                            navController = navController,
                            cartItems = cartItems,
                            onAddToCart = {
                                if (cartItems.value.none { it.id == car.id }) {
                                    cartItems.value = cartItems.value + car
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CarItem(
    car: Car,
    navController: NavController,
    cartItems: MutableState<List<Car>>,
    onAddToCart: () -> Unit
) {
    val isInCart = cartItems.value.any { it.id == car.id }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.7f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = car.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Price: \$${car.price}", style = MaterialTheme.typography.bodyMedium)
            Row(modifier = Modifier.padding(top = 8.dp)) {
                if (!isInCart) {
                    Button(onClick = onAddToCart, modifier = Modifier.weight(1f)) {
                        Text("Add to Cart")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Button(
                    onClick = { navController.navigate("car_details/${car.id}") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("View Details")
                }
            }
        }
    }
}