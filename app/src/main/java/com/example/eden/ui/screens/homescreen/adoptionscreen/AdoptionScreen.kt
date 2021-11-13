package com.example.eden.ui.screens.homescreen.adoptionscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.eden.data.domain.PetInfo
import com.example.eden.ui.components.FilterChip
import com.example.eden.ui.components.PetCarouselCard
import com.example.eden.viewmodels.AdoptionScreenViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun AdoptionScreen(
    viewmodel: AdoptionScreenViewModel,
    onItemClicked: (PetInfo) -> Unit
) {
    val featuredList by viewmodel.featuredList
    val recommendedList by viewmodel.recommendedList
    val currentlyAppliedFilter by viewmodel.currentlyAppliedFilter
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()

    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            // chip Group
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .padding(start = 8.dp, top = 16.dp)
                    .fillMaxWidth()
            ) {
                AdoptionScreenViewModel.FilterOptions.values().forEach { selectedFilter ->
                    FilterChip(
                        onClick = { viewmodel.applyFilter(selectedFilter) },
                        isSelected = currentlyAppliedFilter == selectedFilter,
                        content = {
                            Text(
                                text = selectedFilter.name
                                    .lowercase()
                                    .replaceFirstChar { it.uppercase() })
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
            // featured pets - header
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Featured Pets",
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onPrimary
            )
            // featured pets list
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp)
            ) {
                items(featuredList) { item ->
                    PetCarouselCard(
                        modifier = Modifier.size(200.dp),
                        petInfo = item,
                        onClick = { onItemClicked(item) }
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
            // recommended pets - header
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Recommended Pets",
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onPrimary
            )
            // recommended pets - list
            LazyColumn(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                items(recommendedList) { petInfo ->
                    var isPetFavourited by remember { mutableStateOf(false) }
                    PetInfoCard(
                        petInfo = petInfo,
                        isLiked = isPetFavourited,
                        onLikeButtonClicked = {
                            isPetFavourited = !isPetFavourited
                            coroutineScope.launch {
                                onLikeButtonClicked(
                                    snackBarHostState,
                                    viewmodel,
                                    petInfo,
                                    isPetFavourited
                                )
                            }
                        },
                        onClick = { onItemClicked(petInfo) }
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                }
            }
        }
        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackBarHostState
        )
    }

}

private suspend fun onLikeButtonClicked(
    hostState: SnackbarHostState,
    viewmodel: AdoptionScreenViewModel,
    petInfo: PetInfo,
    isPetFavourited: Boolean
) {
    hostState.currentSnackbarData?.dismiss()
    hostState.showSnackbar(if (isPetFavourited) "Added pet to favourites" else "Removed pet from favourites")
    if (isPetFavourited) viewmodel.addPetToFavourites(petInfo)
    else viewmodel.removePetFromFavourites(petInfo)
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
                painter = rememberImagePainter(
                    data = petInfo.imageResource,
                    builder = { crossfade(true) }
                ),
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



