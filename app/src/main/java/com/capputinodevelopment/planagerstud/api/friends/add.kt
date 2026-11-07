package com.capputinodevelopment.planagerstud.api.friends

import androidx.compose.runtime.key
import com.capputinodevelopment.planagerstud.database.AppDatabase
import com.capputinodevelopment.planagerstud.database.Friend

suspend fun addFriend(db: AppDatabase, friendCode: String) {
    println(friendCode)
    val friendSplit = friendCode.split("%99")
    val friend = Friend(username = friendSplit[0], key = friendSplit[1])
    db.friendsDAO().insertAll(friend)
}

