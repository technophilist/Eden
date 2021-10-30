package com.example.eden.ui.screens.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eden.di.AppContainer
import com.example.eden.ui.navigation.OnBoardingNavigationRoutes
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalComposeUiApi
@Composable
fun OnBoardingNavigation(appContainer: AppContainer) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = OnBoardingNavigationRoutes.welcomeScreenRoute
    ) {
        composable(OnBoardingNavigationRoutes.welcomeScreenRoute) {
            WelcomeScreen(
                onCreateAccountButtonClick = { navController.navigate(OnBoardingNavigationRoutes.signUpScreenRoute) },
                onLoginButtonClick = { navController.navigate(OnBoardingNavigationRoutes.loginScreenRoute) }
            )
        }
        composable(OnBoardingNavigationRoutes.signUpScreenRoute) {
            SignUpScreen(viewModel(factory = appContainer.signUpViewModelFactory), navController)
        }
        composable(OnBoardingNavigationRoutes.loginScreenRoute) {
            LoginScreen(viewModel(factory = appContainer.loginViewModelFactory), navController)
        }
        composable(OnBoardingNavigationRoutes.homeScreenRoute) {
            // EdenHomeScreenNavigation()
        }

    }
}