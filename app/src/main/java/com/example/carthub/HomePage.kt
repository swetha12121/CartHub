package com.example.carthub


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.carthub.Seller.navigateIfBuyer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, userViewModel: UserViewModel, userId: Int?, cartItems: MutableState<List<Car>>) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = { DrawerContent(navController, drawerState) }
        ) {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "CarHub - Home",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.Black)
                            }
                        }
                    )
                },
                content = { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize()
                            .padding(horizontal = 26.dp, vertical = 80.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Button(
                            onClick = { navigateIfBuyer(context, navController, "car_list") },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Browse Cars")
                        }

                        Button(
                            onClick = { navigateIfBuyer(context, navController, "cart") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) {
                            Text("Go to Cart (${cartItems.value.size})")
                        }

                    }
                }
            )
        }
    }
}

@Composable
fun DrawerContent(navController: NavController, drawerState: DrawerState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextButton(
            onClick = {
                navController.navigate("logout")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout", style = MaterialTheme.typography.titleMedium, color = Color.White)
        }
    }
}
