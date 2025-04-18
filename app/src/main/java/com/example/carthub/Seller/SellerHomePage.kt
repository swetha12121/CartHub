package com.example.carthub.Seller

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.carthub.utils.RoleUtils
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerHomePage(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Check if the role is valid
    val isSeller = RoleUtils.isUserSeller(context)
    if (!isSeller) {
        Toast.makeText(context, "Access restricted to sellers only", Toast.LENGTH_SHORT).show()
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
        return
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF8D1414), // Dark red
                            Color(0xFF12123D), // Charcoal
                            Color(0xFF212175), // Navy
                            Color(0xFF483110)  // Brown
                        )
                    )
                )
        )

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = { SellerDrawerContent(navController, drawerState) }
        ) {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        title = { Text("Home", color = Color.Black) },
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
                        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { navController.navigate("add_listing") },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Add New Listing")
                        }

                        Button(
                            onClick = { navController.navigate("view_seller_listings") },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("View My Listings")
                        }

                        Button(
                            onClick = {
                                val sharedPref = context.getSharedPreferences("CarHubPrefs", Context.MODE_PRIVATE)
                                sharedPref.edit().putBoolean("isLoggedIn", false).apply()

                                navController.navigate("roleSelection") {
                                    popUpTo("home/seller") { inclusive = true }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Logout")
                        }

                    }
                }
            )
        }
    }
}

@Composable
fun SellerDrawerContent(navController: NavController, drawerState: DrawerState) {
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
