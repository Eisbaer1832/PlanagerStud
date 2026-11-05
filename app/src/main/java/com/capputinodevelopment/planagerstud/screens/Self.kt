package com.capputinodevelopment.planagerstud.screens

import android.content.ClipData
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.capputinodevelopment.planagerstud.components.Headline
import com.capputinodevelopment.planagerstud.components.SubHeadline
import com.capputinodevelopment.planagerstud.database.preferences.getSharingProfile
import com.capputinodevelopment.planagerstud.database.preferences.saveUsername
import io.github.alexzhirkevich.qrose.QrCodePainter
import io.github.alexzhirkevich.qrose.options.QrBallShape
import io.github.alexzhirkevich.qrose.options.QrBrush
import io.github.alexzhirkevich.qrose.options.QrFrameShape
import io.github.alexzhirkevich.qrose.options.QrPixelShape
import io.github.alexzhirkevich.qrose.options.brush
import io.github.alexzhirkevich.qrose.options.circle
import io.github.alexzhirkevich.qrose.options.roundCorners
import io.github.alexzhirkevich.qrose.options.solid
import io.github.alexzhirkevich.qrose.rememberQrCodePainter
import io.ktor.utils.io.core.Input
import androidx.core.content.edit
import com.capputinodevelopment.planagerstud.database.preferences.getKey
import com.capputinodevelopment.planagerstud.database.preferences.saveKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Base64
import javax.crypto.spec.SecretKeySpec


@Composable
fun Self(modifier: Modifier, navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("PlanagerStudPrefs", Context.MODE_PRIVATE)
    var username: String? by remember { mutableStateOf("") }
    var key: SecretKeySpec? by remember { mutableStateOf(null) }
    var keyString by remember { mutableStateOf("") }
    val clipboardManager = LocalClipboard.current

    LaunchedEffect(key) {
        if (key != null) {
            keyString = Base64.getEncoder().encodeToString(key?.encoded)
        }
    }

    username = getSharingProfile(prefs)
    key = getKey(prefs)

    //prefs.edit { remove("username") }

    if (username.isNullOrEmpty()) {
        // consent and username Form
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
        ) {
            Headline("Eigene Daten teilen")
            SubHeadline("Durch das Fortfahren stimmst du der Speicherung deines Stundenplans auf einem externen Server zu!")

            val userName = rememberTextFieldState()
            TextField(
                modifier = Modifier.fillMaxWidth(),
                state = userName,
                label = {Text("Nutzername")}
            )

            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(64.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                onClick = {
                    if (!userName.text.isEmpty()) {
                        saveUsername(prefs, userName.text.toString())
                        saveKey(prefs)
                        username = getSharingProfile(prefs)
                    }
                }
            ) {
                Text("Weiter")
            }
        }
    } else {
        // actual user body
        Column(
            modifier = modifier
        ) {
            Headline("Capputino", modifier = Modifier.padding(start = 16.dp))
            SubHeadline(
                "Zeige diesen QR Code deinen Freunden",
                modifier = Modifier.padding(start = 16.dp)
            )
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(32.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                ) {
                    Image(
                        modifier = Modifier
                            .size(256.dp)
                            .padding(32.dp),
                        painter = getQRCodePainter("$username%20$keyString"),
                        contentDescription = "Friend QR Code"
                    )
                }
            }
            val scope = rememberCoroutineScope()
            Button(
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.filledTonalButtonColors(),
                onClick = {
                    scope.launch {
                        clipboardManager.setClipEntry(ClipEntry(ClipData.newPlainText("Sharing code", "$username%20$keyString")))
                    }
                },
                modifier = Modifier
                    .padding(32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        text = "Encryption Key"
                    )
                    Text(
                        text = keyString
                    )
                }
            }
        }
    }
}


@Composable
fun getQRCodePainter(data: String): QrCodePainter {
    val c1 = MaterialTheme.colorScheme.tertiaryContainer
    val c2 = MaterialTheme.colorScheme.primary
    return rememberQrCodePainter(
        data = data,
        ballShape = QrBallShape.circle(),
        darkPixelShape = QrPixelShape.roundCorners(),
        frameShape = QrFrameShape.roundCorners(.25f),
        darkBrush = QrBrush.brush {
            Brush.linearGradient(
                0f to c1,
                1f to c2,
                end = Offset(it, it)
            )
        },
        frameBrush = QrBrush.solid(Color.Black),
    )

}