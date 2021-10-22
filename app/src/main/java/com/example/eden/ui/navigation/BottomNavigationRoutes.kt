package com.example.eden.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationRoutes(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object HomeScreen : BottomNavigationRoutes(
        "route_home",
        Icons.Filled.Home,
        "Home"
    )
    object NotificationsScreen : BottomNavigationRoutes(
        "route_notifications",
        Icons.Filled.Notifications,
        "Notifications"
    )
}