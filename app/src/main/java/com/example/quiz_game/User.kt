package com.example.quiz_game
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User(
    @PrimaryKey(autoGenerate = true) val _id: Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "high score") var highScore: Int = 0,
)

