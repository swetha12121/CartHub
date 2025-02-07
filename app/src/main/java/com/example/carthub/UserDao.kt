package com.example.carthub

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE email = :email AND password = :password LIMIT 1")
    suspend fun loginUser(email: String, password: String): User?

    @Query("SELECT * FROM User WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: Int): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("UPDATE User SET secureData = :data WHERE id = :userId")
    suspend fun updateSecureData(userId: Int, data: String)

    @Query("SELECT secureData FROM User WHERE id = :userId")
    suspend fun getSecureData(userId: Int): String?
}
