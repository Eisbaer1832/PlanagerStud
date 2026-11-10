package com.capputinodevelopment.planager_campus.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.temporal.ChronoField

// this wraps the week view, so you can smoothly swipe between different weeks
@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Composable
fun ViewWrapper(addWeeks: Boolean = false, view: @Composable (LocalDate) -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 5 })
    val monday = now().with(ChronoField.DAY_OF_WEEK, 1)

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        key = { it },
    ) { index ->
        if (addWeeks) {
            view(monday.plusWeeks(index.toLong()))
        }else {
            view(monday.plusDays(index.toLong()))
        }
    }}


