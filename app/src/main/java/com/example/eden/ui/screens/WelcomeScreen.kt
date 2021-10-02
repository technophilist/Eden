package com.example.eden.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.eden.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

private data class VectorArtCard(
    @DrawableRes val id: Int,
    val title: String,
    val description: String,
    val imageDescription: String
)

@ExperimentalPagerApi
@Composable
fun WelcomeScreen() {
    val vectorArtCards = listOf(
        VectorArtCard(
            R.drawable.welcome_screen_save_files,
            title = stringResource(id = R.string.label_save_what_interests_you_title),
            description = stringResource(id = R.string.label_save_what_interests_you_desc),
            imageDescription = ""
        ),
        VectorArtCard(
            id = R.drawable.welcome_screen_read_peacefully_vector_art,
            title = stringResource(id = R.string.label_quiet_corner_of_the_internet_title),
            description = stringResource(id = R.string.label_quiet_corner_of_the_internet_desc),
            imageDescription = ""
        ),
        VectorArtCard(
            id = R.drawable.welcome_screen_dark_mode_vector_art,
            title = stringResource(id = R.string.label_reduce_eye_strain_with_dark_mode_title),
            description = stringResource(id = R.string.label_reduce_eye_strain_with_dark_mode_desc),
            imageDescription = ""
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = "${stringResource(id = R.string.label_welcome_to)} ${stringResource(id = R.string.app_name)}",
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1,
        )

        VectorArtCarousel(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            vectorArtCards = vectorArtCards
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = { TODO() },
            content = { Text(text = stringResource(id = R.string.label_create_account)) },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        )
        TextButton(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 32.dp),
            onClick = { TODO() }) {
            Text(
                text = stringResource(id = R.string.text_login),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
private fun VectorArtCarousel(modifier: Modifier = Modifier, vectorArtCards: List<VectorArtCard>) {

    val pagerState = rememberPagerState(
        pageCount = vectorArtCards.size,
        infiniteLoop = true,
        initialOffscreenLimit = 2,
    )

    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {

        HorizontalPager(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            state = pagerState,
        ) { page ->
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = vectorArtCards[page].id),
                    contentDescription = vectorArtCards[page].imageDescription
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    text = vectorArtCards[page].title,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, end = 32.dp),
                    text = vectorArtCards[page].description,
                    textAlign = TextAlign.Center
                )

            }
        }

        HorizontalPagerIndicator(
            modifier = Modifier
                .padding(top = 40.dp)
                .align(Alignment.CenterHorizontally),
            pagerState = pagerState
        )

    }
}






