package com.example.quiz_game
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
@Dao
interface DAOusers {
    @Query("SELECT * FROM Users")
    fun getAll(): Array<User>

    @Insert
    fun insert(vararg u: User)

    @Update
    fun update(u: User)

    @Delete
    fun delete(u: User)

    @Query("SELECT * FROM Users WHERE name = :nickname ")
    fun getUserByName(nickname: String): Array<User>
}