package com.example.eden.ui.screens.homescreen.adoptionscreen

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.eden.ui.navigation.AdoptionScreenNavigationRoutes
import com.example.eden.utils.AdoptionScreenViewModelFactory
import com.example.eden.viewmodels.EdenAdoptionScreenViewModel


@ExperimentalMaterialApi
fun NavGraphBuilder.adoptionScreenGraph(
    navController: NavController,
    route: String,
    adoptionScreenViewModelFactory: AdoptionScreenViewModelFactory,
) {
    navigation(route = route, startDestination = AdoptionScreenNavigationRoutes.homeScreenRoute) {
        composable(route = AdoptionScreenNavigationRoutes.homeScreenRoute) {
            AdoptionScreen(
                viewmodel = viewModel(it, factory = adoptionScreenViewModelFactory),
                onItemClicked = {
                    navController.navigate(AdoptionScreenNavigationRoutes.detailsScreenRoute)
                }
            )
        }
        composable(route = AdoptionScreenNavigationRoutes.detailsScreenRoute) {
            DetailsScreen()
        }
    }
}