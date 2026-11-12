package com.capputinodevelopment.planager_campus.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Publish
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
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
import com.capputinodevelopment.planager_campus.api.Authentication
import com.capputinodevelopment.planager_campus.api.friends.pushToServer
import com.capputinodevelopment.planager_campus.api.parseLesson
import com.capputinodevelopment.planager_campus.components.Headline
import com.capputinodevelopment.planager_campus.components.SettingsBlock
import com.capputinodevelopment.planager_campus.components.ThemePickerBottomSheet
import com.capputinodevelopment.planager_campus.database.AppDatabase
import com.zaki.dynamic.core.sheet.ThemePickerContainer
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject


@Composable
fun Settings(db: AppDatabase, innerPadding: PaddingValues, snackbarHostState: SnackbarHostState) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("PlanagerStudPrefs", Context.MODE_PRIVATE)
    var fetchNewData by remember { mutableStateOf(false) }
    var openThemeSwitcher by remember { mutableStateOf(false) }

    if (openThemeSwitcher) {
        ThemePickerBottomSheet (
            onDismiss = { openThemeSwitcher = false }
        )
    }

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
            //SettingsBlock("Testdaten laden", Icons.Default.Sync) {
            //    scope.launch { googleTest() }
            //}
            SettingsBlock("Daten von StudIp laden", Icons.Default.Download) {fetchNewData = true}
            SettingsBlock("Geteilten Plan aktualisieren", Icons.Default.Publish) {
                scope.launch {
                    pushToServer(db, prefs){
                        //success
                        scope.launch {
                            snackbarHostState.showSnackbar("Erfolgreich synchronisiert")
                        }
                    }
                }
            }

            Text(
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary,
                text = "Anpassung"
            )

            SettingsBlock("Theme wechseln", Icons.Default.ColorLens) {openThemeSwitcher = true}

        }
    }
}

suspend fun googleTest() {
    val client = HttpClient(CIO)
    val response: HttpResponse = client.get("https://planager.tbwebtech.de/example.json")
    val jsonRes = Json.parseToJsonElement(response.bodyAsText()).jsonObject
    jsonRes["data"]!!.jsonArray.forEach {
        parseLesson(it)
    }
}