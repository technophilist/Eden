package com.example.eden.ui.screens.homescreen.adoptionscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.eden.R
import com.example.eden.data.domain.PetInfo
import com.example.eden.viewmodels.AdoptionScreenViewModel
import com.google.accompanist.insets.navigationBarsPadding

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun DetailsScreen(
    viewModel: AdoptionScreenViewModel,
    petInfo: PetInfo
) {
    var isLiked by remember { mutableStateOf(false) }
    var isSuccessAnimationVisible by remember { mutableStateOf(false) }
    var isAdoptButtonEnabled by remember { mutableStateOf(true) }
    val lottieComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.adoption_request_sent_anim))
    val lottieAnimationState = animateLottieCompositionAsState(
        composition = lottieComposition,
        isPlaying = isSuccessAnimationVisible,
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth(),
            painter = rememberImagePainter(petInfo.imageResource, builder = { crossfade(true) }),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .clip(
                    MaterialTheme.shapes.medium.copy(
                        bottomStart = CornerSize(0.dp),
                        bottomEnd = CornerSize(0.dp)
                    )
                )
                .background(MaterialTheme.colors.background)
                .align(Alignment.BottomCenter)
                .fillMaxHeight(0.55f)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Header(petInfo = petInfo)
            Spacer(modifier = Modifier.size(16.dp))
            HighlightsCarousel(
                age = petInfo.age,
                weight = petInfo.weight,
                color = petInfo.color
            )
            Spacer(modifier = Modifier.size(8.dp))
            Description(petInfo.description)
            Spacer(modifier = Modifier.size(16.dp))
            Footer(
                onAdoptButtonClick = { isSuccessAnimationVisible = true },
                onLikeButtonClick = {
                    isLiked = if (isLiked) {
                        viewModel.addPetToFavourites(petInfo)
                        false
                    } else {
                        viewModel.removePetFromFavourites(petInfo)
                        true
                    }
                },
                isLiked = isLiked,
                isAdoptButtonEnabled = isAdoptButtonEnabled,
                adoptButtonText = stringResource(
                    id = if (isAdoptButtonEnabled) R.string.label_adopt_pet
                    else R.string.label_adopt_pet_request_sent
                )
            )
        }
        AnimatedVisibility(
            visible = isSuccessAnimationVisible && lottieAnimationState.isPlaying,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LottieAnimation(
                composition = lottieComposition,
                progress = lottieAnimationState.progress
            )
            isAdoptButtonEnabled = false
        }
    }
}

@Composable
private fun Header(petInfo: PetInfo) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.alignByBaseline(),
            text = petInfo.name,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
        Text(
            modifier = Modifier.alignByBaseline(),
            text = petInfo.type,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.size(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${petInfo.gender} | ${petInfo.breed}",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.SemiBold
        )
    }
}


@Composable
private fun HighlightsCarousel(
    age: String,
    color: String,
    weight: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        listOf("Age", "Color", "Weight").zip(listOf(age, color, weight)).forEach {
            Card(
                modifier = Modifier.size(width = 100.dp, height = 90.dp),
                shape = RoundedCornerShape(20.0f)
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .padding(16.dp)
                        .fillMaxSize(),
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = it.first,
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.h1
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = it.second,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
    }
}

@Composable
private fun Description(content: String) {
    Column(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Summary",
            style = MaterialTheme.typography.h1,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.body1,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Justify,
        )
    }
}

@ExperimentalMaterialApi
@Composable
private fun Footer(
    onAdoptButtonClick: () -> Unit,
    onLikeButtonClick: () -> Unit,
    isLiked: Boolean,
    adoptButtonText: String,
    isAdoptButtonEnabled: Boolean = true,
) {
    Row(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth()
    ) {
        Button(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
            onClick = onAdoptButtonClick,
            content = { Text(text = adoptButtonText, fontWeight = FontWeight.Bold) },
            enabled = isAdoptButtonEnabled
        )
        Spacer(modifier = Modifier.size(16.dp))
        Card(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxHeight(),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = MaterialTheme.colors.primary,
            onClick = onLikeButtonClick
        ) {
            Icon(
                modifier = Modifier.padding(16.dp),
                imageVector = if (isLiked) Icons.Filled.Favorite
                else Icons.Filled.FavoriteBorder,
                contentDescription = ""
            )
        }
    }
}
