package com.example.carthub

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(private val userDao: UserDao) : ViewModel() {
    var loginResult: User? = null
        private set

    suspend fun loginUser(email: String, password: String): User? {
        return withContext(Dispatchers.IO) {
            val user = userDao.loginUser(email, password)
            if (user != null) {
                Log.d("UserViewModel", "Login successful for: ${user.email}")
            } else {
                Log.d("UserViewModel", "Login failed for: $email")
            }
            user // Return user
        }
    }

    fun registerUser(email: String, password: String, name: String, address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userDao.insertUser(User(email = email, password = password, name = name, address = address))
                Log.d("UserViewModel", "User registered successfully: $email")
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error registering user: $e")
            }
        }
    }

    fun logout() {
        loginResult = null
    }
}
