package com.capputinodevelopment.planagerstud.database

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "friends")
data class Friend (
    @PrimaryKey var username: String = "",
    var key: String = "",
)