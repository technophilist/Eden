package com.example.eden.ui.screens.onboarding

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.eden.ui.navigation.EdenAppNavigationRoutes
import com.example.eden.ui.navigation.OnBoardingNavigationRoutes
import com.example.eden.utils.LogInViewModelFactory
import com.example.eden.utils.SignUpViewModelFactory
import com.example.eden.viewmodels.EdenLogInViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalComposeUiApi
@ExperimentalPagerApi
fun NavGraphBuilder.onBoardingNavGraph(
    navController: NavController,
    route: String,
    signUpViewModelFactory: SignUpViewModelFactory,
    logInViewModelFactory: LogInViewModelFactory
) {

    navigation(route = route, startDestination = OnBoardingNavigationRoutes.welcomeScreenRoute) {
        composable(OnBoardingNavigationRoutes.welcomeScreenRoute) {
            WelcomeScreen(
                onCreateAccountButtonClick = { navController.navigate(OnBoardingNavigationRoutes.signUpScreenRoute) },
                onLoginButtonClick = { navController.navigate(OnBoardingNavigationRoutes.loginScreenRoute) }
            )
        }
        composable(OnBoardingNavigationRoutes.signUpScreenRoute) {
            SignUpScreen(
                viewModel = viewModel(
                    viewModelStoreOwner = it,
                    factory = signUpViewModelFactory
                ),
                onAccountCreatedSuccessfully = { navigateToHomeScreen(navController) }
            )
        }
        composable(OnBoardingNavigationRoutes.loginScreenRoute) {
            LoginScreen(
                viewModel = viewModel<EdenLogInViewModel>(
                    viewModelStoreOwner = it,
                    factory = logInViewModelFactory
                ),
                onSuccessfulAuthentication = { navigateToHomeScreen(navController) }
            )
        }
    }
}

private fun navigateToHomeScreen(navController: NavController) {
    navController.navigate(EdenAppNavigationRoutes.homeScreenRoute) {
        popUpTo(OnBoardingNavigationRoutes.welcomeScreenRoute) { inclusive = true }
    }
}