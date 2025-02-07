package com.example.carthub

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

// Data class to represent a gadget
data class Gadget(val name: String, val imageRes: Int, val price: String)

// Composable function to display each gadget item
@Composable
fun GadgetItem(gadget: Gadget) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = gadget.imageRes),
                contentDescription = gadget.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = gadget.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = gadget.price, color = Color.Blue, style = MaterialTheme.typography.bodyMedium)
                Button(onClick = { /* Add to cart */ }, modifier = Modifier.padding(top = 8.dp)) {
                    Text("Buy Now")
                }
            }
        }
    }
}
