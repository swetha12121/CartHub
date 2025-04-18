package com.example.carthub.utils

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.navigation.NavController

object RoleUtils {
    private const val PREF_NAME = "CarHubPrefs"
    private const val ROLE_KEY = "userRole"
    private const val LOGIN_KEY = "isLoggedIn"

    fun isUserSeller(context: Context): Boolean {
        return getUserRole(context) == "seller"
    }

    fun isUserBuyer(context: Context): Boolean {
        return getUserRole(context) == "buyer"
    }

    fun saveUserRole(context: Context, role: String) {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPref.edit().putString(ROLE_KEY, role).apply()
    }

    fun getUserRole(context: Context): String? {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(ROLE_KEY, null)
    }

    fun setLoginStatus(context: Context, status: Boolean) {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPref.edit().putBoolean(LOGIN_KEY, status).apply()
    }

    fun isLoggedIn(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(LOGIN_KEY, false)
    }
    fun navigateIfBuyer(context: Context, navController: NavController, route: String) {
        if (!isUserBuyer(context)) {
            Toast.makeText(context, "Only buyers can access this feature", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        } else {
            navController.navigate(route)
        }
    }

    fun clearSession(context: Context) {
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPref.edit()
            .remove(LOGIN_KEY)
            .remove(ROLE_KEY)
            .apply()
    }
}
