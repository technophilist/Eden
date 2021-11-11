package com.example.eden

/*
Include gender info in home screen
Migrate all strings to stings.xml
Optimize app for darj theme
State hoising for adoption screen composables
class EdenAppState(
    val navController: NavController,
    private val isUserSignedIn: Boolean = false,
    val snackbarHostState: SnackbarHostState = SnackbarHostState()
) {

    val startDestinationRoute: String
        get() = if (isUserSignedIn) OnBoardingNavigationRoutes.homeScreenRoute
        else OnBoardingNavigationRoutes.welcomeScreenRoute

    val isBottomNavigationBarVisible: Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route !in listOf(
            OnBoardingNavigationRoutes.loginScreenRoute,
            OnBoardingNavigationRoutes.signUpScreenRoute,
            OnBoardingNavigationRoutes.welcomeScreenRoute
        )

}
 */
