package com.capputinodevelopment.planager_campus.database.preferences

import android.content.SharedPreferences
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.core.content.edit
import androidx.datastore.core.DataStore
import com.zaki.dynamic.core.adapter.ComposeThemeAdapter
import com.zaki.dynamic.core.model.ThemeDefinition
import com.zaki.dynamic.core.model.ThemeSelection
import com.zaki.dynamic.core.persistance.ThemeStore
import kotlinx.serialization.json.Json
import com.capputinodevelopment.planager_campus.ui.theme.PlanagerStudTheme

class SettingsThemeStore(
    private val prefs: SharedPreferences
) : ThemeStore {
    private val KEY = "theme.selection.json"
    private val json = Json {
        ignoreUnknownKeys = true
    }

    override suspend fun load(): ThemeSelection? =
        prefs.getString(KEY, null)?.let {
            json.decodeFromString<ThemeSelection>(it)
        }

    override suspend fun save(selection: ThemeSelection) {
        prefs.edit { putString(KEY, json.encodeToString(selection)) }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
class ExpressiveM3Adapter : ComposeThemeAdapter {
    @Composable
    override fun MaterialTheme(
        theme: ThemeDefinition,
        typography: androidx.compose.material3.Typography?,
        shapes: Shapes?,
        content: @Composable () -> Unit
    ) {
        MaterialExpressiveTheme(
            colorScheme = MaterialTheme.colorScheme,
            typography = typography ?: MaterialTheme.typography,
            shapes = shapes ?: MaterialTheme.shapes,
            content = content
        )
    }
}
