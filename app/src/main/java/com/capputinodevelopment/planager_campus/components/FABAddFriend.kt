package com.capputinodevelopment.planager_campus.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.capputinodevelopment.planager_campus.api.friends.addFriend
import com.capputinodevelopment.planager_campus.database.AppDatabase
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FABAddFriend(db: AppDatabase) {
    var expanded by remember { mutableStateOf(false) }
    var addByCode by remember { mutableStateOf(false) }
    var scope = rememberCoroutineScope()
    var scanQRCode by remember { mutableStateOf(false) }
    if (scanQRCode) {
        ScanQRCode {
            scope.launch {
                addFriend(db,it)
                scanQRCode = false
            }
        }
    }

    if (addByCode) {
        AddFriendByText{ fCode ->
            if (fCode != null) {
                scope.launch {
                    addFriend(db,fCode)
                    addByCode = false
                }
            }
        }
    }

    FloatingActionButtonMenu(
        expanded = expanded,
        button = {
            FloatingActionButton(
                onClick = {expanded = !expanded }
            ) {
                Icon(Icons.Default.Add, "add")
            }
        }
    ) {
        FloatingActionButtonMenuItem(
            onClick = {scanQRCode = true},
            text = { Text("QR Code") },
            icon = { Icon(Icons.Default.QrCode, "QR Code") }
        )

        FloatingActionButtonMenuItem(
            onClick = {addByCode = true},
            text = { Text("Freundescode") },
            icon = { Icon(Icons.Default.Key, "Sharing Key") }
        )
    }
}

