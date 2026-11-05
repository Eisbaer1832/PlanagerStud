package com.capputinodevelopment.planagerstud.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsBlock(text: String, onClick: () -> Unit) {
    Spacer(Modifier.height(16.dp))

    Button(
        colors = ButtonDefaults.filledTonalButtonColors(),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .height(64.dp)
                .fillMaxSize(),
            onClick = { onClick()}
    ) {
        Row() {
            Text(text)
            Spacer(Modifier.weight(1f))
            Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, "Go")
        }
    }


}