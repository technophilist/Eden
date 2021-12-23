package com.example.eden.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Report
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * A sealed class that contains the [route],[icon] and [label]
 * associated with a single navigation drawer item.
 */
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