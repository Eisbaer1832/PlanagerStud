package com.capputinodevelopment.planager_campus.api.friends

import android.content.SharedPreferences
import com.capputinodevelopment.planager_campus.database.AppDatabase
import com.capputinodevelopment.planager_campus.database.preferences.aesEncrypt
import com.capputinodevelopment.planager_campus.database.preferences.getKey
import com.capputinodevelopment.planager_campus.database.preferences.getSharingProfile
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
import kotlinx.serialization.json.put
import java.util.Base64

suspend fun pushToServer(db: AppDatabase, prefs: SharedPreferences, success: () -> Unit) {
    val lessons = db.lessonsDAO().getAll().first()
    val plain = Json.encodeToString(lessons).encodeToByteArray()
    val cipher = aesEncrypt(plain, getKey(prefs)!!)
    val encoded = Base64.getEncoder().encodeToString(cipher)   // <-- not toString(UTF_8)
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
    val response = client.post("http://194.163.141.76:3003/api/save") {
        contentType(ContentType.Application.Json)
        setBody(json)
    }

    if (response.status.value == 200) {
        println("save successful")
        success()
    }

}