package com.capputinodevelopment.planagerstud.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capputinodevelopment.planagerstud.database.Lesson


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DayLessonCard(
    lesson: Lesson,
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),

        modifier = Modifier.padding(end = 5.dp),
        shape = RoundedCornerShape(16.dp)
    ){

        Column{
            // title
            Column {
                Surface(
                    modifier = Modifier.padding(10.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(MaterialShapes.Cookie7Sided.toShape())
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ){
                            Icon(
                                modifier = Modifier.size(40.dp),
                                imageVector = Icons.Default.School,
                                contentDescription = "Localized description",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                        Text(
                            modifier = Modifier.fillMaxSize(),
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 30.sp,
                            text = lesson.title.substringAfter(" "),
                        )
                    }
                }

                // time and location
                Column(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = lesson.location
                    )
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                        text = lesson.start + " - " + lesson.end,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}
