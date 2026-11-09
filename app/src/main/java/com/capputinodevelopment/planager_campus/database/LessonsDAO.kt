package com.capputinodevelopment.planager_campus.database

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface LessonsDAO {
    @Query("SELECT * FROM lessons")
    fun getAll(): Flow<List<Lesson>>

    @Upsert
    suspend fun insertAll(vararg sample: Lesson)

    @Delete
    suspend fun delete(sample: Lesson)
}