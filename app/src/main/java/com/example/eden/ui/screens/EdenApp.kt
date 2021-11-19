package com.example.eden.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.eden.di.AppContainer
import com.example.eden.ui.navigation.AdoptionScreenNavigationRoutes
import com.example.eden.ui.navigation.BottomNavigationRoutes
import com.example.eden.ui.navigation.EdenAppNavigationRoutes
import com.example.eden.ui.screens.homescreen.NotificationsScreen
import com.example.eden.ui.screens.homescreen.adoptionscreen.adoptionScreenGraph
import com.example.eden.ui.screens.onboarding.onBoardingNavGraph
import com.example.eden.viewmodels.EdenNotificationsScreenViewmodel
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun EdenApp(appContainer: AppContainer) {
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val isBottomBarVisible by remember(currentBackStackEntry.value?.destination?.route) {
        val currentRoute = currentBackStackEntry.value?.destination?.route
        val currentNavGraphRoute = currentBackStackEntry.value?.destination?.parent?.route
        mutableStateOf(
            currentRoute != AdoptionScreenNavigationRoutes.detailsScreenRoute &&
                    currentNavGraphRoute != EdenAppNavigationRoutes.onBoardingRoute
        )
    }

    val bottomBar = @Composable {
        if (isBottomBarVisible) {
            EdenBottomNavigation(
                modifier = Modifier.navigationBarsPadding(),
                navController = navController,
                navigationDestinations = listOf(
                    BottomNavigationRoutes.AdoptionScreen,
                    BottomNavigationRoutes.NotificationsScreen
                )
            )
        }
    }
    Scaffold(bottomBar = bottomBar) { scaffoldPaddingValues ->
        NavHost(
            modifier = Modifier.padding(scaffoldPaddingValues),
            navController = navController,
            startDestination = EdenAppNavigationRoutes.onBoardingRoute
        ) {

            onBoardingNavGraph(
                route = EdenAppNavigationRoutes.onBoardingRoute,
                navController = navController,
                signUpViewModelFactory = appContainer.signUpViewModelFactory,
                logInViewModelFactory = appContainer.loginViewModelFactory,
            )

            adoptionScreenGraph(
                route = EdenAppNavigationRoutes.homeScreenRoute,
                navController = navController,
                adoptionScreenViewModelFactory = appContainer.adoptionScreenViewModelFactory
            )
            composable(EdenAppNavigationRoutes.notificationsScreenRoute) {
                val viewModel = viewModel<EdenNotificationsScreenViewmodel>(
                    viewModelStoreOwner = it,
                    factory = appContainer.notificationScreenViewModelFactory
                )
                val currentContext = LocalContext.current
                NotificationsScreen(
                    notifications = viewModel.notificationList.observeAsState().value
                        ?: emptyList(),
                    onNotificationClicked = { notificationInfo ->
                        val openUrlIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(notificationInfo.urlString))
                        currentContext.startActivity(openUrlIntent)
                    }
                )
            }
        }
    }
}

@Composable
private fun EdenBottomNavigation(
    navController: NavController,
    navigationDestinations: List<BottomNavigationRoutes>,
    modifier: Modifier = Modifier,
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

