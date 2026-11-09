package com.capputinodevelopment.planager_campus.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.capputinodevelopment.planager_campus.database.Lesson
import kotlin.text.substringAfter


@Composable
fun WeekLessonCard (lesson: Lesson, height: Dp, onClick: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp


    var h = height
    if (height < 80.dp) {h = 80.dp}
    Card(
        modifier = Modifier
            .width(screenWidth / 6)
            .height(h)
            .padding(3.dp),
        onClick = {onClick()}
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface  (
                color =  MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = nameShortener(lesson.title.substringAfter(" "))
                )
            }
            Column{
                Text(
                    text = lesson.start,
                    color =  MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = lesson.end,
                    color =  MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}


fun nameShortener(name: String): String{
    val ns = name
        .replace("und", "&")
        .split("für")[0]
        .trim()
        .split(" ")

    println(ns)
    return when (ns.size) {
        1 -> {name}
        2 -> {ns[0].take(3) + " " + ns[1].take(3)}
        3 -> {ns[0].take(1) + ns[1].take(1) + ns[2].take(1)}
        else -> {name}
    }
}