package com.example.eden.ui.screens.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eden.di.AppContainer
import com.example.eden.ui.navigation.OnBoardingNavigationRoutes
import com.example.eden.utils.LogInViewModelFactory
import com.example.eden.viewmodels.EdenLogInViewModel
import com.example.eden.viewmodels.LogInViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi
@ExperimentalComposeUiApi
@Composable
fun OnBoardingNavigation(appContainer: AppContainer) {
    // TODO("Check if viewmodel correctly gets created and destroyed)
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
            SignUpScreen(viewModel(viewModelStoreOwner = it,factory = appContainer.signUpViewModelFactory), navController)
        }
        composable(OnBoardingNavigationRoutes.loginScreenRoute) {
            LoginScreen(viewModel<EdenLogInViewModel>(viewModelStoreOwner = it,factory = appContainer.loginViewModelFactory), navController)
        }
        composable(OnBoardingNavigationRoutes.homeScreenRoute) {
            // EdenHomeScreenNavigation()
            Box(modifier = Modifier.fillMaxSize()) {

            }
        }

    }
}