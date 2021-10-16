package com.example.eden.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.eden.R
import com.example.eden.data.domain.PetInfo
import com.example.eden.ui.components.PetCarouselCard
import com.example.eden.viewmodels.HomeScreenViewModel

@ExperimentalMaterialApi
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .padding(start = 16.dp,end = 16.dp)
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.paddingFromBaseline(top = 32.dp),
            text = "Featured Pets",
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onPrimary

        )
        Spacer(modifier = Modifier.size(16.dp))
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            val petList = List(10) {
                PetInfo(
                    "Cherry",
                    "Des",
                    "Dog",
                    "Pug",
                    12.45f,
                    R.drawable.placeholder
                )
            }
            items(petList) { item ->
                PetCarouselCard(modifier = Modifier.size(200.dp), petInfo = item)
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .paddingFromBaseline(top = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Recommended Pets",
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onPrimary
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = ""
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        PetInfoCard(
            petInfo = PetInfo(
                "Cherry",
                "Des",
                "Dog",
                "Pug",
                25000f,
                R.drawable.placeholder
            ),
            isLiked = false,
            onLikeButtonClicked = {},
            onClick = {}
        )

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
                    text = "₹${petInfo.price}",
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

