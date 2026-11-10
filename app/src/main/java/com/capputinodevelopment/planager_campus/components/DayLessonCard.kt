package com.capputinodevelopment.planager_campus.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capputinodevelopment.planager_campus.database.Lesson


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DayLessonCard(
    lesson: Lesson,
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        modifier = Modifier
            .padding(vertical = 6.dp)
            .padding(end = 5.dp),
        shape = RoundedCornerShape(16.dp)
    ){
        Column{
            // title

            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // get rid of the id inside the title
                var numberless = lesson.title.split(Regex("(?<=\\d)(?=\\D)"))
                if (numberless.size > 1) {
                    numberless = numberless.drop(1)
                }

                Text(
                    modifier = Modifier.fillMaxSize(),
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30.sp,
                    color = MaterialTheme.colorScheme.primary,
                    text = numberless.joinToString(" "),
                )
            }


            // time and location
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        text = lesson.start + " - " + lesson.end
                    )
                }
                Spacer(Modifier.width(16.dp))
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        text = lesson.location
                    )
                }
            }
        }
    }
}
