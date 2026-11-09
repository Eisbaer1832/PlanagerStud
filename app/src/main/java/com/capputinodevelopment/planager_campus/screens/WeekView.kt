package com.capputinodevelopment.planager_campus.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.capputinodevelopment.planager_campus.api.friends.getFriendsData
import com.capputinodevelopment.planager_campus.components.DetailsSheet
import com.capputinodevelopment.planager_campus.components.WeekLessonCard
import com.capputinodevelopment.planager_campus.database.Database.db
import com.capputinodevelopment.planager_campus.database.Friend
import com.capputinodevelopment.planager_campus.database.Lesson
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import kotlin.math.floor


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun WeekView(modifier: Modifier = Modifier, startDate: LocalDate, friend: Friend? = null) {
    var lessons by remember { mutableStateOf(listOf<Lesson>()) }
    var detailLesson by remember { mutableStateOf<Lesson?>(null) }

    LaunchedEffect(Unit) {
        lessons = if (friend == null) {
             db.lessonsDAO().getAll().first();
        }else {
            getFriendsData(friend)
        }
    }

    // show details of a lesson (by clicking on it)
    detailLesson?.let { DetailsSheet(it) { detailLesson = null } }

    val state = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    val onRefresh: () -> Unit = {
        TODO()
    }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier,
        state = state,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                state = state
            )
        },
    ) {
        if (lessons.isEmpty()) {
            Row(
                Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
            )
            {
                LoadingIndicator(modifier = Modifier.size(60.dp))
            }
        } else {
            val days = arrayOf("Mo", "Di", "Mi", "Do", "Fr")
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                val availableHeight: Dp = maxHeight
                Column(
                    modifier = Modifier.padding(start=75.dp)
                ) {
                    Spacer(Modifier.height(70.dp))
                    for (i in 8..18) {
                        Surface(
                            color = MaterialTheme.colorScheme.outlineVariant,
                            modifier = Modifier
                                .height(3.dp)
                                .fillMaxWidth()
                        ) {}
                        Spacer(Modifier.height((availableHeight / 20) + 19.5.dp)) // why 19.5, well really it should be the text height (25) - the line height (3), but that didn't work and 19.5 does so wee'l ignore this until it breaks
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    BoxWithConstraints(
                        modifier = Modifier
                            .width(screenWidth / 6)
                            .padding(top = 60.dp)
                            .fillMaxHeight()
                    ) {
                        val availableHeight: Dp = maxHeight
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            for (i in 8..18) {
                                Text(
                                    text = if(i < 10 ) {"0${i}:00"}else {"${i}:00"},
                                    modifier = Modifier.height(25.dp)
                                )
                                Spacer(Modifier.height(availableHeight / 20))
                            }
                        }
                    }
                    for (i in 0..<days.size) {
                        Column {
                            Card(
                                modifier = Modifier
                                    .height(67.dp)
                                    .width(screenWidth / 6)
                                    .padding(3.dp)
                            ) {
                                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = days[i],
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(top=5.dp, start = 5.dp, end = 10.dp)
                                    )
                                    val day = startDate.plusDays(i.toLong())
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = "${day.dayOfMonth}.${day.monthValue}",
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            BoxWithConstraints(
                                modifier = Modifier.fillMaxHeight()
                            ) {
                                val availableHeight: Dp = maxHeight
                                // show lessons of the day
                                var lastPos = 8.0;
                                Column {
                                    lessons
                                        .filter { it.weekDay - 1 == i }
                                        .sortedBy { it.start }
                                        .forEachIndexed {i, lesson ->
                                            val t = decTime(lesson.start)
                                            val timeBetween = (t - lastPos)
                                            lastPos = decTime(lesson.end);

                                            var height = calcSpaceBetween(decTime(lesson.end)- decTime(lesson.start), t, availableHeight,0)
                                            var space = (calcSpaceBetween(timeBetween, t, availableHeight,i))
                                            if (decTime(lesson.end).mod(1f) != 0.0) {
                                                println("true for" + lesson.end)
                                                space -= 25.dp
                                            }

                                            Spacer(modifier = Modifier.height(space))
                                            WeekLessonCard(lesson, height) {detailLesson = lesson}
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun calcSpaceBetween(timeBetween: Double, t: Double, availableHeight: Dp, i: Int): Dp {
    var headingSize = floor(timeBetween) * 25.dp
    if (
        t.mod(1f).toInt() != 0 ||
        (i == 0)
    ) {
        headingSize += 25.dp
    }
    val spaceBetween = (timeBetween * (availableHeight / 20)) + headingSize
    return spaceBetween
}

fun decTime(str: String): Double {
    val timeSplit = str.split(":");
    return timeSplit[0].toFloat() + timeSplit[1].toFloat() / 60.0
}
