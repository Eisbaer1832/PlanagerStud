package com.capputinodevelopment.planager_campus.api.friends

import com.capputinodevelopment.planager_campus.database.AppDatabase
import com.capputinodevelopment.planager_campus.database.Friend
import com.capputinodevelopment.planager_campus.database.Lesson
import com.capputinodevelopment.planager_campus.database.preferences.aesDecrypt
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import java.util.Base64
import javax.crypto.spec.SecretKeySpec

suspend fun getFriends(db: AppDatabase,): List<Friend> {
    return db.friendsDAO().getAll().first()
}

suspend fun getFriendsData(friend: Friend): List<Lesson> {
    val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }
    }
    val keyBytes = Base64.getDecoder().decode(friend.key)
    val loadedKey = SecretKeySpec(keyBytes, "AES")

    val res = client.get("https://planagerstud.tbwebtech.de/api/get/" +friend.username)
    val body = res.body<String>()
    val data = Base64.getDecoder().decode(body)
    val decryptedTable = aesDecrypt( data, loadedKey).decodeToString()

    val parsed = Json.decodeFromString<List<Lesson>>(decryptedTable)

    return parsed
}