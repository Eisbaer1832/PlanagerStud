package com.capputinodevelopment.planagerstud.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capputinodevelopment.planagerstud.api.Authentication
import com.capputinodevelopment.planagerstud.api.friends.pushToServer
import com.capputinodevelopment.planagerstud.components.Headline
import com.capputinodevelopment.planagerstud.components.SettingsBlock
import com.capputinodevelopment.planagerstud.components.SubHeadline
import com.capputinodevelopment.planagerstud.database.AppDatabase
import kotlinx.coroutines.launch


@Composable
fun Settings(db: AppDatabase, innerPadding: PaddingValues) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("PlanagerStudPrefs", Context.MODE_PRIVATE)
    var fetchNewData by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    if (fetchNewData) {
        Authentication("https://studip.tu-braunschweig.de/dispatch.php/login") {
            fetchNewData = false
        }
    }else {
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
        ) {
            Headline("Einstellungen")

            Text(
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary,
                text = "Synchronisation"
            )
            SettingsBlock("Daten von StudIp laden", Icons.Default.Sync) {fetchNewData = true}
            SettingsBlock("Stundenplan mit deinen Freunden teilen", Icons.Default.Share) {
                scope.launch {
                    pushToServer(db, prefs)
                }
            }

        }
    }
}