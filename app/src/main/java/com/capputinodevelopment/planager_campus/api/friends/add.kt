package com.capputinodevelopment.planager_campus.api.friends

import com.capputinodevelopment.planager_campus.database.AppDatabase
import com.capputinodevelopment.planager_campus.database.Friend

suspend fun addFriend(db: AppDatabase, friendCode: String) {
    println(friendCode)
    val friendSplit = friendCode.split("%99")
    val friend = Friend(username = friendSplit[0], key = friendSplit[1])
    db.friendsDAO().insertAll(friend)
}

