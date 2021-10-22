package com.example.eden.ui.screens.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eden.ui.navigation.OnBoardingNavigationRoutes
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@Composable
fun OnBoardingScreen() {
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
        composable(OnBoardingNavigationRoutes.loginScreenRoute) {
            LoginScreen()
        }
        composable(OnBoardingNavigationRoutes.signUpScreenRoute) {
            SignUpScreen()
        }
    }
}