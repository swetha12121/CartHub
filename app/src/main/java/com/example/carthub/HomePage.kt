package com.example.carthub

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, userViewModel: UserViewModel, userId: Int) {
    var searchQuery by remember { mutableStateOf("") }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerContent(navController, drawerState) } // ✅ Pass drawerState to allow closing
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("CarHub - Home") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) { // ✅ Opens drawer properly
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search for gadgets...") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                val gadgets = listOf(
                    Gadget("Smartphone", R.drawable.laptop, "$699"),
                    Gadget("Laptop", R.drawable.laptop, "$1299"),
                    Gadget("Headphones", R.drawable.laptop, "$199"),
                    Gadget("Smartwatch", R.drawable.laptop, "$249"),
                ).filter { it.name.contains(searchQuery, ignoreCase = true) }

                LazyColumn {
                    items(gadgets) { gadget ->
                        GadgetItem(gadget)
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerContent(navController: NavController, drawerState: DrawerState) {
    Column(modifier = Modifier.padding(16.dp)) {
        TextButton(
            onClick = {
                navController.navigate("logout")
            }
        ) {
            Text("Logout")
        }
    }
}
