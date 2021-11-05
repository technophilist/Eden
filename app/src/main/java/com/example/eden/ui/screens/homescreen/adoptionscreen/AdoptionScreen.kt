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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.eden.data.domain.PetInfo
import com.example.eden.ui.components.FilterChip
import com.example.eden.ui.components.PetCarouselCard
import com.example.eden.viewmodels.AdoptionScreenViewModel

@ExperimentalMaterialApi
@Composable
fun AdoptionScreen(
    viewmodel: AdoptionScreenViewModel,
    onItemClicked: (PetInfo) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.fillMaxSize()) {
        // chip Group
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(start = 8.dp, top = 16.dp)
                .fillMaxWidth()
        ) {
            AdoptionScreenViewModel.FilterOptions.values().forEach {
                var isSelected by remember { mutableStateOf(false) }
                FilterChip(
                    onClick = { isSelected = !isSelected },
                    isSelected = isSelected,
                    content = { Text(text = it.name.lowercase().capitalize(Locale.current)) }
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
            items(viewmodel.featuredList) { item ->
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
            items(viewmodel.recommendedList) { petInfo ->
                var isPetFavourited by remember { mutableStateOf(false) }
                PetInfoCard(
                    petInfo = petInfo,
                    isLiked = isPetFavourited,
                    onLikeButtonClicked = { isPetFavourited = !isPetFavourited },
                    onClick = { onItemClicked(petInfo) }
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
//            Image(
//                modifier = Modifier
//                    .clip(MaterialTheme.shapes.small)
//                    .weight(2f),
//                painter = painterResource(petInfo.imageResource),
//                contentDescription = "",
//                contentScale = ContentScale.Crop,
//            )
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



