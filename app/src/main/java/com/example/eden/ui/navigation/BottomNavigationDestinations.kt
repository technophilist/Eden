package com.example.eden.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationDestinations(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object HomeScreen : BottomNavigationDestinations(
        "destination_home",
        Icons.Filled.Home,
        "Home"
    )
    object NotificationsScreen : BottomNavigationDestinations(
        "destination_notifications",
        Icons.Filled.Notifications,
        "Notifications"
    )
}