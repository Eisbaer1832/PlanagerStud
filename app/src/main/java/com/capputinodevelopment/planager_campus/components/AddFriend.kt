package com.capputinodevelopment.planager_campus.components

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import io.ktor.websocket.Frame

@Composable
fun AddFriendByText(
    onDismissRequest: (friendCode:String?) -> Unit,
) {
    val friendCode = rememberTextFieldState()
    AlertDialog(
        icon = {
            Icon(Icons.Filled.PersonAdd, contentDescription = "add friend")
        },
        title = {
            Text(text = "Freund hinzufügen")
        },
        text = {
            TextField(
                state = friendCode,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "freundescode"
                    )
                },
                label = { Frame.Text("Freundescode") },

            )
        },
        onDismissRequest = {
            onDismissRequest(friendCode.text.toString())
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismissRequest(friendCode.text.toString())
                }
            ) {

                Text("Weiter")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest(null)
                }
            ) {
                Text("Abbrechen")
            }
        }
    )
}