package com.example.carthub

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.carthub.Seller.SellerHomePage
import com.example.carthub.Seller.SellerLoginPage
import com.example.carthub.Seller.SellerRegistrationPage
import com.example.carthub.Seller.isUserBuyer

@Composable
fun NavGraph(
    navController: NavHostController,
    userViewModel: UserViewModel,
    startDestination: String,
    context: Context
) {
    val cartItems = remember { mutableStateOf(emptyList<Car>()) }

    NavHost(navController = navController, startDestination = startDestination) {
        composable("roleSelection") {
            RoleSelectionScreen(
                context = LocalContext.current,
                onRoleSelected = { role ->
                    val sharedPref = context.getSharedPreferences("CarHubPrefs", Context.MODE_PRIVATE)
                    sharedPref.edit().putString("userRole", role).apply()

                    when (role) {
                        "buyer" -> navController.navigate("register")
                        "seller" -> navController.navigate("sellerRegister")
                    }
                }
            )
        }

        composable("login") { LoginPage(navController, userViewModel) }
        composable("register") { RegistrationPage(navController, userViewModel) }

        composable("home/buyer") {
            HomePage(navController, userViewModel, userId = null, cartItems = cartItems)
        }

        composable("home/seller") {
            SellerHomePage(navController)
        }

        composable("home/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
            HomePage(navController, userViewModel, userId, cartItems)
        }

        composable("car_list") {
            if (isUserBuyer(context)) {
                CarListScreen(navController, cartItems)
            } else {
                Toast.makeText(context, "Access denied for sellers.", Toast.LENGTH_SHORT).show()
            }
        }

        composable("cart") {
            if (isUserBuyer(context)) {
                CartScreen(navController, cartItems)
            } else {
                Toast.makeText(context, "Access denied for sellers.", Toast.LENGTH_SHORT).show()
            }
        }
        composable("sellerLogin") {
            SellerLoginPage(navController)
        }

        composable("sellerRegister") {
            SellerRegistrationPage(navController) // or whatever your composable is
        }

        composable("payment") {
            if (isUserBuyer(context)) {
                PaymentScreen(
                    navController = navController,
                    cartItems = cartItems.value,
                    onPaymentSuccess = { cartItems.value = emptyList() }
                )
            } else {
                Toast.makeText(context, "Access denied for sellers.", Toast.LENGTH_SHORT).show()
            }
        }

        composable("logout") {
            LogoutPage(navController)
        }

        composable("car_details/{carId}") { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId")?.toInt() ?: 0
            CarDetailsScreen(navController, carId)
        }

        composable("add_listing") {
            CarListScreen(navController, cartItems)
        }
    }
}

