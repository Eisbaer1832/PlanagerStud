package com.capputinodevelopment.planagerstud.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.capputinodevelopment.planagerstud.components.Headline
import com.capputinodevelopment.planagerstud.components.SubHeadline
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


@Composable
fun Self(modifier: Modifier, navController: NavController) {
    Column(
        modifier = modifier
    ) {
        Headline("Capputino", modifier = Modifier.padding(start = 16.dp))
        SubHeadline("Zeige diesen QR Code deinen Freunden", modifier = Modifier.padding(start = 16.dp))
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
                    painter = getQRCodePainter("sampleDataBlabBlaBlaBliBlubBlabBlaBlaBliBlubBlabBlaBlaBliBlubBlabBlaBlaBliBlubBlabBlaBlaBliBlubBlabBlaBlaBliBlub"),
                    contentDescription = "Friend QR Code"
                )
            }
        }
        Button (
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.filledTonalButtonColors(),
            onClick = {},
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
                    text = "0WN0C7AiMHQKWwbfcveUXjcomPBs3UY5RY08G4fY6MHdMcXv92I1ul1MuK0IiJ4dfYg7pq6mNz7yx8gbgpsfgcObrv+a8NcRqSFUOAch0y3O5PxFCfAYSzW9xa39ofg5jmc96f2mak7ZkwQRCpKQbIBIqYOhvQqdLL1GArY+Z3HvDoPvyGQl2M3a8ezHGF8Vu3jqr+ohrXDssvnWvLGiBgmaUYmW9AC4UOu3zcyd7oqJzeJWYvlfaYIgdWoCGM6pI="
                )
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