package com.capputinodevelopment.planagerstud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.capputinodevelopment.planagerstud.api.Authentication
import com.capputinodevelopment.planagerstud.components.NavBar
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
                Authentication("https://studip.tu-braunschweig.de/dispatch.php/login")
            }
            composable(AppDestinations.WEEK.route) {
            }
            composable(AppDestinations.SETTINGS.route) {
            }
            composable (AppDestinations.FRIENDS.route) {
            }
        }
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
    FRIENDS("Freunde", "details", Icons.Filled.Groups, Icons.Outlined.Groups),
    SETTINGS("Einstellungen", "settings", Icons.Filled.Settings, Icons.Outlined.Settings),
}
