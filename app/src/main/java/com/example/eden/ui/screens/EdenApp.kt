package com.example.eden.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.eden.data.domain.EdenUser
import com.example.eden.di.AppContainer
import com.example.eden.ui.navigation.AdoptionScreenNavigationRoutes
import com.example.eden.ui.navigation.BottomNavigationRoutes
import com.example.eden.ui.navigation.EdenAppNavigationRoutes
import com.example.eden.ui.navigation.NavigationDrawerRoutes
import com.example.eden.ui.screens.homescreen.NotificationsScreen
import com.example.eden.ui.screens.homescreen.adoptionscreen.adoptionScreenGraph
import com.example.eden.ui.screens.onboarding.onBoardingNavGraph
import com.example.eden.viewmodels.EdenNotificationsScreenViewmodel
import com.example.eden.viewmodels.EdenReportScreenViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
                    currentNavGraphRoute != EdenAppNavigationRoutes.onBoardingRoute &&
                    currentRoute != EdenAppNavigationRoutes.reportScreenRoute
        )
    }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

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
    val navigationDrawerRoutes = remember { listOf(NavigationDrawerRoutes.ReportScreenRoutes) }
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = bottomBar,
        drawerContent = if (currentBackStackEntry.value?.destination?.parent?.route != EdenAppNavigationRoutes.onBoardingRoute) {
            {
                EdenNavigationDrawer(
                    appContainer.authenticationService.currentUser,
                    scaffoldState,
                    navController,
                    navigationDrawerRoutes,
                    coroutineScope
                )
            }
        } else null
    ) { scaffoldPaddingValues ->
        NavHost(
            modifier = Modifier.padding(scaffoldPaddingValues),
            navController = navController,
            startDestination = if (appContainer.authenticationService.currentUser == null)
                EdenAppNavigationRoutes.onBoardingRoute else EdenAppNavigationRoutes.homeScreenRoute
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
            composable(EdenAppNavigationRoutes.reportScreenRoute) {
                val viewModel = viewModel<EdenReportScreenViewModel>(
                    viewModelStoreOwner = it,
                    factory = appContainer.reportScreenViewModelFactory
                )
                ReportScreen(viewModel)
            }
        }
    }
}

//TODO("Remove after tomorrow's event")
@Composable
private fun EdenNavigationDrawer(
    currentUser: EdenUser? = null,
    scaffoldState: ScaffoldState,
    navController: NavController,
    navigationRoutes: List<NavigationDrawerRoutes>,
    coroutineScope: CoroutineScope,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        navigationRoutes.forEach {
            // header
            currentUser?.let {
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = currentUser.name ?: "User101",
                        style = MaterialTheme.typography.h3,
                    )
                    Text(
                        text = currentUser.email ?: "user101@email.com",
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Bold,
                    )
                    Divider(modifier = Modifier.padding(8.dp))
                }
            }
            Row(modifier = Modifier
                .clickable {
                    navController.navigate(it.route)
                    coroutineScope.launch { scaffoldState.drawerState.close() }
                }
                .fillMaxWidth()
            ) {
                Icon(
                    modifier = Modifier.padding(8.dp),
                    imageVector = it.icon,
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = it.label,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
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
                },
                alwaysShowLabel = false
            )
        }
    }
}

