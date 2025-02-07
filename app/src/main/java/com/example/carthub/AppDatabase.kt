package com.example.carthub

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// 🔥 Correct Migration (Prevents Duplicate Column Issue)
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // ✅ First, check if the column exists before adding it
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS User_temp (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, email TEXT NOT NULL, password TEXT NOT NULL, name TEXT DEFAULT '', address TEXT DEFAULT '', secureData TEXT)"
        )

        // ✅ Copy data from old table to the new table
        database.execSQL(
            "INSERT INTO User_temp (id, email, password, name, address, secureData) SELECT id, email, password, name, address, secureData FROM User"
        )

        // ✅ Drop old table
        database.execSQL("DROP TABLE User")

        // ✅ Rename new table to the original table name
        database.execSQL("ALTER TABLE User_temp RENAME TO User")
    }
}

@Database(entities = [User::class], version = 3) // 🔥 INCREASE VERSION TO 3
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
