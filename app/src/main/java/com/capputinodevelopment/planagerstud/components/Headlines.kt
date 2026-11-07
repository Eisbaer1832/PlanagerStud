package com.capputinodevelopment.planagerstud.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun Headline(text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
    ) {
        Text(
            style = MaterialTheme.typography.headlineMediumEmphasized,
            fontWeight = FontWeight(1000),
            text = text
        )
    }
}

@Composable
fun SubHeadline(text: String, modifier: Modifier = Modifier) {
    Row() {
        Text(
            modifier = modifier,
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            text = text
        )
    }
}