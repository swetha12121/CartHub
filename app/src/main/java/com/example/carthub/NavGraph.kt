import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.carthub.Car
import com.example.carthub.CarDetailsScreen
import com.example.carthub.CarListScreen
import com.example.carthub.CartScreen
import com.example.carthub.HomePage
import com.example.carthub.LoginPage
import com.example.carthub.LogoutPage
import com.example.carthub.PayPalPaymentScreen
import com.example.carthub.PaymentScreen
import com.example.carthub.PaymentSuccessScreen
import com.example.carthub.RegistrationPage
import com.example.carthub.UserViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    userViewModel: UserViewModel,
    startDestination: String // ✅ Accept as a parameter
) {
    val cartItems = remember { mutableStateOf(emptyList<Car>()) }

    NavHost(navController = navController, startDestination = startDestination) { // ✅ Use here
        composable("login") { LoginPage(navController, userViewModel) }
        composable("register") { RegistrationPage(navController, userViewModel) }

        composable("home/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
            HomePage(navController, userViewModel, userId, cartItems)
        }

        composable("car_list") { CarListScreen(navController, cartItems) }
        composable("cart") { CartScreen(navController, cartItems) }

        composable("payment") {
            PaymentScreen(
                navController = navController,
                cartItems = cartItems.value,
                onPaymentSuccess = {
                    cartItems.value = emptyList() // ✅ Clear cart after payment
                }
            )
        }

        composable("logout") { LogoutPage(navController, userViewModel) }

        composable("car_details/{carId}") { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId")?.toInt() ?: 0
            CarDetailsScreen(navController, carId)
        }

        composable("paypal_payment/{mobileNumber}") { backStackEntry ->
            val mobileNumber = backStackEntry.arguments?.getString("mobileNumber") ?: ""
            PayPalPaymentScreen(navController, mobileNumber)
        }

        composable("payment_success/{mobileNumber}") { backStackEntry ->
            val mobileNumber = backStackEntry.arguments?.getString("mobileNumber") ?: ""
            PaymentSuccessScreen(navController, mobileNumber)
        }
    }
}
