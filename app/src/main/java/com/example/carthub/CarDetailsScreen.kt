package com.example.carthub


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDetailsScreen(navController: NavController, carId: Int) {
    val car = CarRepository.getCars().find { it.id == carId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Car Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            car?.let {
                Text(text = "Basic Car Information", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Make: ${it.name.split(" ")[0]}")
                Text("Model: ${it.name.split(" ")[1]}")
                Text("Year: ${it.name.split(" ")[2]}")
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Price: \$${it.price}", style = MaterialTheme.typography.titleMedium)
                val painter = rememberAsyncImagePainter(model = car.imageResId)
                Image(
                    painter = painter,
                    contentDescription = car.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Engine & Performance", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Engine Type: Turbocharged Inline-4")
                Text("Horsepower: 250 hp")
                Text("Torque: 280 lb-ft")
                Text("Transmission: 8-speed automatic")
                Text("Drivetrain: All-Wheel Drive (AWD)")
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Fuel Economy", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text("City MPG: 22 - 30 MPG")
                Text("Highway MPG: 28 - 40 MPG")
                Text("Fuel Tank Capacity: ~15 gallons")
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    Text("Back to List")
                }
            } ?: Text("Car details not found!", style = MaterialTheme.typography.headlineSmall)
        }
    }
}
