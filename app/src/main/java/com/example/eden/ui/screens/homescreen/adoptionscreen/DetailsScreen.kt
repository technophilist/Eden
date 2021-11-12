package com.example.eden.ui.screens.homescreen.adoptionscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.eden.data.domain.PetInfo

private data class Highlight(
    val label: String,
    val imageVector: ImageVector,
)

@ExperimentalMaterialApi
@Composable
fun DetailsScreen(petInfo: PetInfo) {
    var isLiked by remember { mutableStateOf(false) }
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
                arrayOf(
                    Highlight("Friendly", Icons.Filled.Home), //
                    Highlight("Neat", Icons.Filled.AutoAwesome), //
                    Highlight("Vocal", Icons.Filled.MusicNote), //
                )
            )
            Spacer(modifier = Modifier.size(8.dp))
            Description(petInfo.description)
            Spacer(modifier = Modifier.size(16.dp))
            Footer(
                onAdoptButtonClick = {},//
                onLikeButtonClick = { isLiked = !isLiked },//
                isLiked = isLiked
            )
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
//        Text(
//            text = "Rs. ${petInfo.price}",
//            style = MaterialTheme.typography.subtitle1,
//            fontWeight = FontWeight.SemiBold
//        )
    }
}


@Composable
private fun HighlightsCarousel(highlights: Array<Highlight>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        highlights.forEach {
            Card(
                modifier = Modifier.size(width = 100.dp, height = 90.dp),
                shape = RoundedCornerShape(20.0f)
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = it.imageVector,
                        contentDescription = ""
                    )
                    Text(
                        text = it.label,
                        fontWeight = FontWeight.Bold
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
    isLiked: Boolean
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
            onClick = onAdoptButtonClick,
            content = { Text(text = "Adopt Pet", fontWeight = FontWeight.Bold) }
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
