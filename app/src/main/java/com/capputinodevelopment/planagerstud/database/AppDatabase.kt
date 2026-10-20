package com.capputinodevelopment.planagerstud.database

import androidx.room3.RoomDatabase
import androidx.room3.Database

@Database(entities = [Lesson::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lessonsDAO(): LessonsDAO
}

object Database {
    lateinit var db: AppDatabase
}