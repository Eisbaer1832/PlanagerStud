package com.capputinodevelopment.planagerstud.database

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface FriendsDAO {
    @Query("SELECT * FROM friends")
    fun getAll(): Flow<List<Friend>>

    @Upsert
    suspend fun insertAll(vararg sample: Friend)

    @Delete
    suspend fun delete(sample: Friend)
}