package com.capputinodevelopment.planagerstud.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.capputinodevelopment.planagerstud.components.Headline
import com.capputinodevelopment.planagerstud.components.SubHeadline

@Composable
fun Friends(modifier: Modifier, navController: NavController) {
    Column(
        modifier = modifier
    ) {
        Headline("Freunde", modifier = Modifier.padding(start = 16.dp))
        SubHeadline("Füge neue Freunde hinzu oder bearbeite bestehende", modifier = Modifier.padding(start = 16.dp))

        Button(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(16.dp),
            onClick = {
                navController.navigate(route = "friends/self")
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Share, "Share")
                Text("Teile deine Daten")
            }
        }

        OutlinedButton(
            elevation = ButtonDefaults.elevatedButtonElevation(),
            onClick = {},
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .height(64.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
        ) {
            Text("Kristin")
        }

    }
}