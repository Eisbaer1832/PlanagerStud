package com.capputinodevelopment.planagerstud.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capputinodevelopment.planagerstud.components.DayLessonCard
import com.capputinodevelopment.planagerstud.database.Database.db
import com.capputinodevelopment.planagerstud.database.Lesson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DayView(modifier: Modifier = Modifier) {
    var lessons by remember { mutableStateOf(listOf<Lesson>()) }
    val currentDayOfWeek = LocalDate.now().dayOfWeek.ordinal
    val state = rememberPullToRefreshState()

    LaunchedEffect(Unit) {
        lessons = db.lessonsDAO().getAll().first();
    }

    var isRefreshing by remember { mutableStateOf(false) }
    val onRefresh: () -> Unit = {
        TODO()
    }


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
            Row(Modifier.fillMaxSize(),horizontalArrangement = Arrangement.Center){
                LoadingIndicator(modifier = Modifier.size(60.dp))
            }
        } else {
            Column(
                Modifier
                    .padding(horizontal = 10.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 30.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        text = LocalDate.now().dayOfWeek.getDisplayName(
                            TextStyle.FULL_STANDALONE ,
                            Locale.GERMANY),
                    )
                }
                lessons
                    .filter { it.weekDay - 1 == currentDayOfWeek}
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
