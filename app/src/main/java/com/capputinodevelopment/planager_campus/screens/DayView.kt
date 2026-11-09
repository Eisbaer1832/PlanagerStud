package com.capputinodevelopment.planager_campus.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capputinodevelopment.planager_campus.components.DayLessonCard
import com.capputinodevelopment.planager_campus.database.Database.db
import com.capputinodevelopment.planager_campus.database.Lesson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DayView(date: LocalDate, onRefresh: () -> Unit) {
    var lessons by remember { mutableStateOf(listOf<Lesson>()) }
    val currentDayOfWeek = date.dayOfWeek.ordinal
    val state = rememberPullToRefreshState()

    LaunchedEffect(Unit) {
        lessons = db.lessonsDAO().getAll().first();
    }

    var isRefreshing by remember { mutableStateOf(false) }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = state,
        modifier = Modifier.fillMaxSize(),
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
            Row(Modifier.fillMaxSize(),horizontalArrangement = Arrangement.Center){
                LoadingIndicator(modifier = Modifier.size(60.dp))
            }
        } else {
            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
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

                    Column (
                        modifier = Modifier
                            .padding(vertical = 30.dp)
                    ) {
                        Text(
                            fontSize = 40.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            text = date.dayOfWeek.getDisplayName(
                                TextStyle.FULL_STANDALONE ,
                                Locale.GERMANY),
                        )
                        val pattern = "dd.MM.yyyy"
                        val formatter = DateTimeFormatter.ofPattern(pattern)
                        Text(
                            text = date.format(formatter)
                        )
                    }
                }
                lessons
                    .filter { it.weekDay - 1 == currentDayOfWeek}
                    .sortedBy { it.start }
                    .forEachIndexed {index, lesson ->
                        var visible by remember { mutableStateOf(false) }

                        LaunchedEffect(Unit) {
                            delay(index * 50L) // staggered animation
                            visible = true
                        }

                        AnimatedVisibility(
                            visible = visible,
                            enter = slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn(),
                            exit = slideOutHorizontally(targetOffsetX = { it / 2 }) + fadeOut()
                        ) {
                            DayLessonCard(lesson)
                        }
                    }

            }
        }
    }
}
