package com.capputinodevelopment.planager_campus.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.capputinodevelopment.planager_campus.AppDestinations

@Composable
fun NavBar(
    currentScreen: String?,
    onNavigate: (AppDestinations) -> Unit
) {

    val expanded = true
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalFloatingToolbar(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(start = 16.dp, end = 16.dp, bottom = 0.dp),
            expanded = expanded,
            colors = FloatingToolbarDefaults.vibrantFloatingToolbarColors(
                toolbarContentColor = MaterialTheme.colorScheme.onSurface,
                toolbarContainerColor = MaterialTheme.colorScheme.primary,
            ),
            content = {
                for (item in AppDestinations.values()) {
                    val isSelected = currentScreen?.startsWith(item.route) ?: false
                    val itemWidth by animateDpAsState(
                        targetValue = if (expanded || isSelected) 48.dp else 0.dp,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                    )

                    val labelWidth by animateDpAsState(
                        targetValue = if (isSelected) 80.dp else 0.dp,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                    )

                    val spacerWidth by animateDpAsState(
                        targetValue = if (item.ordinal < AppDestinations.entries.size - 1) 8.dp else 0.dp,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                    )

                    if (itemWidth > 0.dp || isSelected) {
                        IconButton(
                            onClick = {
                                onNavigate(item)
                            },
                            modifier = Modifier
                                .width(itemWidth + labelWidth)
                                .height(48.dp),
                            colors = if (isSelected) {
                                IconButtonDefaults.filledIconButtonColors(
                                    contentColor = MaterialTheme.colorScheme.primary,
                                    containerColor = MaterialTheme.colorScheme.background
                                )
                            } else {
                                IconButtonDefaults.iconButtonColors(
                                    contentColor = MaterialTheme.colorScheme.background,
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Box {
                                    Icon(
                                        imageVector = (if (isSelected) item.selectedIcon else item.unSelectedIcon),
                                        contentDescription = item.label,
                                        tint = if (isSelected) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.background
                                        },
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                if (isSelected) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = item.label,
                                        style = MaterialTheme.typography.labelLarge,
                                        maxLines = 1,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }

                        if (item.ordinal < AppDestinations.entries.size - 1) {
                            Spacer(modifier = Modifier.width(spacerWidth))
                        }
                    }
                }
            }
        )
    }
}
