package com.example.eden.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Report
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationDrawerRoutes(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    object ReportScreenRoutes : NavigationDrawerRoutes(
        route = EdenAppNavigationRoutes.reportScreenRoute,
        label = "Report",
        icon = Icons.Filled.Report
    )
}