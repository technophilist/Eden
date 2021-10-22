package com.example.eden.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.eden.data.domain.NotificationInfo
import com.example.eden.ui.navigation.BottomNavigationRoutes
import com.example.eden.ui.navigation.HomeScreenNavigationRoutes
import com.example.eden.viewmodels.EdenHomeScreenViewModel

@ExperimentalMaterialApi
@Composable
fun EdenApp() {
    val navController = rememberNavController()
    val bottomNavigationDestinations = listOf(
        BottomNavigationRoutes.HomeScreen,
        BottomNavigationRoutes.NotificationsScreen
    )
    var isBottomNavigationVisible by remember { mutableStateOf(true) }
    val testNotifications = listOf(
        NotificationInfo(
            1,
            NotificationInfo.NotificationType.ORDERS,
            header = "Hurray! Order Placed !",
            content = "Lore Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
        ),
        NotificationInfo(
            1,
            NotificationInfo.NotificationType.APPOINTMENTS,
            header = "Appointment Granted !",
            content = "Lore Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
        ),
        NotificationInfo(
            1,
            NotificationInfo.NotificationType.NGO,
            header = "New NGO event annocement",
            content = "Lore Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
        )
    )
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = BottomNavigationRoutes.HomeScreen.route
        ) {
            composable(BottomNavigationRoutes.HomeScreen.route) {
                HomeScreen(
                    viewmodel = EdenHomeScreenViewModel(),
                    onItemClicked = { homeScreenNavController, _, homeScreenRoutes ->
                        homeScreenNavController.addOnDestinationChangedListener { _, destination, _ ->
                            isBottomNavigationVisible = destination.route != homeScreenRoutes.detailsScreenRoute
                        }
                        homeScreenNavController.navigate(HomeScreenNavigationRoutes.detailsScreenRoute)
                    }
                )
            }
            composable(BottomNavigationRoutes.NotificationsScreen.route) {
                NotificationsScreen(testNotifications)
            }
        }
        if (isBottomNavigationVisible) {
            EdenBottomNavigation(
                modifier = Modifier.align(Alignment.BottomCenter),
                navController = navController,
                navigationDestinations = bottomNavigationDestinations
            )
        }
    }
}

@Composable
private fun EdenBottomNavigation(
    modifier: Modifier,
    navController: NavController,
    navigationDestinations: List<BottomNavigationRoutes>
) {
    BottomNavigation(modifier = modifier) {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route
        navigationDestinations.forEach {
            BottomNavigationItem(
                icon = { Icon(imageVector = it.icon, contentDescription = " ") },
                selected = currentRoute == it.route,
                onClick = { if (currentRoute != it.route) navController.navigate(it.route) },
                label = {
                    Text(
                        text = it.label,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            )
        }
    }
}

