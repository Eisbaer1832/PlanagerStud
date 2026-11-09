package com.capputinodevelopment.planager_campus.database

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "lessons")
data class Lesson (
    @PrimaryKey var id: String = "",
    var title: String = "",
    var start: String = "",
    var end: String = "",
    var weekDay: Int = 0,
    var freq: String = "",
    var interval: Int = 0,
    var from: String = "",
    var until: String = "",
    var exDates: String = "",
    var location: String = "",
)