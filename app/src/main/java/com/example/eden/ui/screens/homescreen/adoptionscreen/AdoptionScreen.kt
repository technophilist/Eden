package com.example.eden.ui.screens.homescreen.adoptionscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eden.data.domain.PetInfo
import com.example.eden.ui.components.EdenSearchBar
import com.example.eden.ui.components.IconWithDropDownMenu
import com.example.eden.ui.components.MenuOption
import com.example.eden.ui.components.PetCarouselCard
import com.example.eden.ui.navigation.HomeScreenNavigationRoutes
import com.example.eden.viewmodels.HomeScreenViewModel

// TODO Add docs and explicitly mention why home screen has its own nav controller
@ExperimentalMaterialApi
@Composable
private fun AdoptionScreen(
    viewmodel: HomeScreenViewModel,
    navController: NavController,
    onItemClicked: (NavController, PetInfo, HomeScreenNavigationRoutes) -> Unit
) {
    var isFilterMenuVisible by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val filterOptions = listOf(
        MenuOption("Dogs") { viewmodel.filterRecommendedList(HomeScreenViewModel.FilterOptions.DOGS) },
        MenuOption("Cats") { viewmodel.filterRecommendedList(HomeScreenViewModel.FilterOptions.CATS) }
    )
    // featured pets - header
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        ) {
            EdenSearchBar(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search") },
                onValueChange = { searchText = it },
                value = searchText,
            )
            Text(
                modifier = Modifier.paddingFromBaseline(top = 32.dp),
                text = "Featured Pets",
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onPrimary
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        // featured pets - list
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp)
        ) {
            items(viewmodel.featuredList) { item ->
                PetCarouselCard(
                    modifier = Modifier.size(200.dp),
                    petInfo = item,
                    onClick = { onItemClicked(navController, item, HomeScreenNavigationRoutes) }
                )
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
        // recommended pets - header
        Row(
            modifier = Modifier
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "Recommended Pets",
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onPrimary
            )
            IconWithDropDownMenu(
                icon = Icons.Filled.FilterList,
                menuOptions = filterOptions,
                isDropDownMenuExpanded = isFilterMenuVisible,
                onDismissRequest = { isFilterMenuVisible = false },
                onClick = { isFilterMenuVisible = !isFilterMenuVisible }
            )
        }
        LazyColumn(modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)) {
            items(viewmodel.recommendedList) { petInfo ->
                var isPetFavourited by remember {
                    mutableStateOf(false)
                }
                PetInfoCard(
                    petInfo = petInfo,
                    isLiked = isPetFavourited,
                    onLikeButtonClicked = { isPetFavourited = !isPetFavourited },
                    onClick = { onItemClicked(navController, petInfo, HomeScreenNavigationRoutes) }
                )
                Spacer(modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun PetInfoCard(
    petInfo: PetInfo,
    isLiked: Boolean = false,
    onLikeButtonClicked: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.requiredHeight(80.dp),
        shape = MaterialTheme.shapes.small,
        onClick = onClick
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .weight(2f),
                painter = painterResource(petInfo.imageResource),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
            Column(
                Modifier
                    .weight(4f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = petInfo.name,
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = "${petInfo.type} | ${petInfo.breed}",
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onPrimary
                )
                Text(
                    text = petInfo.gender,
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.onPrimary
                )
            }
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 16.dp),
                onClick = onLikeButtonClicked
            ) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.Favorite
                    else Icons.Outlined.FavoriteBorder,
                    contentDescription = "",
                    tint = if (isLiked) MaterialTheme.colors.secondary
                    else Color.Gray
                )
            }
        }
    }
}



