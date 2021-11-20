package com.example.eden.ui.screens.onboarding

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
import com.google.accompanist.insets.statusBarsPadding
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
fun WelcomeScreen(
    onCreateAccountButtonClick: () -> Unit,
    onLoginButtonClick: () -> Unit
) {
    val vectorArtCards = listOf(
        VectorArtCard(
            R.drawable.pet_deserves_home_vector_art,
            title = stringResource(id = R.string.label_title_pet_deserves_home),
            description = stringResource(id = R.string.label_description_pet_deserves_home),
            imageDescription = ""
        ),
        VectorArtCard(
            id = R.drawable.veterinarian_vector_art,
            title = stringResource(id = R.string.label_title_take_care_with_veterinarians),
            description = stringResource(id = R.string.label_description_take_care_with_veterinarians),
            imageDescription = ""
        ),
        VectorArtCard(
            id = R.drawable.timely_notifications_vector_art,
            title = stringResource(id = R.string.label_title_timely_notifications),
            description = stringResource(id = R.string.label_description_timely_notifications),
            imageDescription = ""
        )
    )
    Column(
        modifier = Modifier
            .padding(8.dp)
            .statusBarsPadding()
            .fillMaxSize(),
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
                .padding(bottom = 24.dp)
                .fillMaxWidth(),
            vectorArtCards = vectorArtCards
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            onClick = onCreateAccountButtonClick,
            content = { Text(text = stringResource(id = R.string.label_create_account)) },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        )
        TextButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp),
            onClick = onLoginButtonClick
        ) {
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
                    modifier = Modifier
                        .height(350.dp)
                        .fillMaxWidth(),
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
                        .padding(start = 32.dp, end = 32.dp)
                        .fillMaxWidth(),
                    text = vectorArtCards[page].description,
                    textAlign = TextAlign.Center
                )

            }
        }

        HorizontalPagerIndicator(
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally),
            pagerState = pagerState
        )

    }
}






