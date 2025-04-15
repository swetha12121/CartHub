package com.example.carthub.Seller

import android.content.Context
import android.content.SharedPreferences

object SellerUtils {
    private const val PREF_NAME = "CarHubPrefs"
    private const val ROLE_KEY = "userRole"

    fun isUserSeller(context: Context): Boolean {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(ROLE_KEY, "") == "seller"
    }

    fun isUserBuyer(context: Context): Boolean {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(ROLE_KEY, "") == "buyer"
    }

    fun saveUserRole(context: Context, role: String) {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPref.edit().putString(ROLE_KEY, role).apply()
    }

    fun getUserRole(context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(ROLE_KEY, null)
    }
}
