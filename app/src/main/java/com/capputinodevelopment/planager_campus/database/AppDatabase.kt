package com.capputinodevelopment.planager_campus.database

import androidx.room3.RoomDatabase
import androidx.room3.Database

@Database(entities = [Lesson::class, Friend::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lessonsDAO(): LessonsDAO
    abstract fun friendsDAO(): FriendsDAO
}

object Database {
    lateinit var db: AppDatabase
}