package com.example.eden.ui.screens.homescreen.adoptionscreen

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eden.ui.navigation.AdoptionScreenNavigationRoutes
import com.example.eden.viewmodels.EdenAdoptionScreenViewModel


@ExperimentalMaterialApi
@Composable
fun AdoptionScreenNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AdoptionScreenNavigationRoutes.homeScreenRoute
    ) {
        composable(AdoptionScreenNavigationRoutes.homeScreenRoute) {
            AdoptionScreen(
                viewmodel = viewModel<EdenAdoptionScreenViewModel>(viewModelStoreOwner = it),
                navController = navController,
                onItemClicked = { petInfo ->
                    navController.navigate(
                        AdoptionScreenNavigationRoutes.detailsScreenRoute
                    )
                }
            )
        }
        composable(AdoptionScreenNavigationRoutes.detailsScreenRoute) {
            DetailsScreen()
        }
    }
}