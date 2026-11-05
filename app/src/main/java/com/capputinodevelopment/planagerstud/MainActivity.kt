package com.capputinodevelopment.planagerstud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ViewWeek
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ViewWeek
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.room3.Room
import com.capputinodevelopment.planagerstud.components.NavBar
import com.capputinodevelopment.planagerstud.database.AppDatabase
import com.capputinodevelopment.planagerstud.database.Database.db
import com.capputinodevelopment.planagerstud.screens.DayView
import com.capputinodevelopment.planagerstud.screens.Friends
import com.capputinodevelopment.planagerstud.screens.Self
import com.capputinodevelopment.planagerstud.screens.Settings
import com.capputinodevelopment.planagerstud.screens.ViewWrapper
import com.capputinodevelopment.planagerstud.screens.WeekView
import com.capputinodevelopment.planagerstud.ui.theme.PlanagerStudTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlanagerStudTheme {
                PlanagerStudApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun PlanagerStudApp() {
    // nav controlling
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current

    db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "PlanagerStud"
    ).build()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavBar(
                currentScreen = currentRoute,
            ) { destination ->
                navController.navigate(destination.route) {
                    popUpTo(navController.graph.findStartDestination().id) {saveState = true}
                    launchSingleTop = true
                    restoreState = false
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestinations.TODAY.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn(animationSpec = tween(100)) },
            exitTransition = { fadeOut(animationSpec = tween(100)) },
            popEnterTransition = { fadeIn(animationSpec = tween(100)) },
            popExitTransition = { fadeOut(animationSpec = tween(100)) }
        ) {
            composable(AppDestinations.TODAY.route) {
                ViewWrapper { DayView(it) {} }
            }
            composable(AppDestinations.WEEK.route) { ViewWrapper(true){WeekView(startDate = it)} }
            composable(AppDestinations.SETTINGS.route) { Settings(db) }
            friendsGraph(navController,innerPadding)
        }
    }

}


fun NavGraphBuilder.friendsGraph(navController: NavController, paddingValues: PaddingValues) {
    navigation(startDestination = "friends/overview", route = "friends") {
        composable("friends/overview") { Friends(Modifier.padding(paddingValues), navController) }
        composable("friends/self") { Self(Modifier.padding(paddingValues), navController) }
    }
}

enum class AppDestinations(
    val label: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
) {
    TODAY("Heute", "home", Icons.Filled.Home, Icons.Outlined.Home),
    WEEK("Woche", "types", Icons.Filled.ViewWeek, Icons.Outlined.ViewWeek),
    FRIENDS("Freunde", "friends", Icons.Filled.Groups, Icons.Outlined.Groups),
    SETTINGS("Einstellungen", "settings", Icons.Filled.Settings, Icons.Outlined.Settings),
}
