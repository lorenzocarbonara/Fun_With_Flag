package com.example.quiz_game

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [User::class], version = 1)
abstract class userDatabase : RoomDatabase() {
    abstract fun getDao(): DAOusers
    companion object {
        @Volatile
        private var INSTANCE: userDatabase? = null
        fun getInstance(context: Context): userDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    userDatabase::class.java, "people_db"
                )
                    .build()
            }
        }
    }
}