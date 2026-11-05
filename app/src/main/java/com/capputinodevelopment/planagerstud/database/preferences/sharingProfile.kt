package com.capputinodevelopment.planagerstud.database.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


fun getSharingProfile(prefs: SharedPreferences): String? {
    val username = prefs.getString("username", "")
    return username
}

fun saveKey(prefs: SharedPreferences) {
    val keyGenerator = KeyGenerator.getInstance("AES")
    keyGenerator.init(256)
    val key = keyGenerator.generateKey()
    val keyString = Base64.getEncoder().encodeToString(key.encoded)
    prefs.edit {
        putString("key", keyString)
    }
}

fun getKey(prefs: SharedPreferences): SecretKeySpec {
    val keyString = prefs.getString("key", "")
    val keyBytes = Base64.getDecoder().decode(keyString)
    val loadedKey = SecretKeySpec(keyBytes, "AES")

    return loadedKey
}


fun saveUsername(prefs: SharedPreferences, username: String) {
    prefs.edit {
        putString("username", username)
    }
}

fun aesEncrypt(data: ByteArray, secretKey: SecretKey): ByteArray {
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    val ivParameterSpec = IvParameterSpec(ByteArray(16)) // Use a secure IV in production
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
    return cipher.doFinal(data)
}

fun aesDecrypt(encryptedData: ByteArray, secretKey: SecretKey): ByteArray {
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    val ivParameterSpec = IvParameterSpec(ByteArray(16)) // Use the same IV as used in encryption
    cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)
    return cipher.doFinal(encryptedData)
}