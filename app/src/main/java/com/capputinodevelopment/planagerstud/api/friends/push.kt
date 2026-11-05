package com.capputinodevelopment.planagerstud.api.friends

import android.content.SharedPreferences
import com.capputinodevelopment.planagerstud.database.AppDatabase
import com.capputinodevelopment.planagerstud.database.preferences.aesEncrypt
import com.capputinodevelopment.planagerstud.database.preferences.getKey
import com.capputinodevelopment.planagerstud.database.preferences.getSharingProfile
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.put

suspend fun pushToServer(db: AppDatabase, prefs: SharedPreferences) {
    val lessons = db.lessonsDAO().getAll().first()
    val keyObj = Json.encodeToJsonElement(lessons)
    val encoded = aesEncrypt(keyObj.toString().encodeToByteArray(), getKey(prefs)).toString(Charsets.UTF_8)

    val username = getSharingProfile(prefs)

    val json = buildJsonObject {
        put("username", username)
        put("key", encoded)

    }

    val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }
    }
    client.post("http://194.163.141.76:3003/api/save") {
        contentType(ContentType.Application.Json)
        setBody(json)
    }
}