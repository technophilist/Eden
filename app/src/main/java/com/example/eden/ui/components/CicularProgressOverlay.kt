package com.example.eden.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color

/**
 * Displays an overlay over the [content] with a circular progress indicator.It takes up the
 * entire size of size of its parent.
 *
 * @param modifier Modifier that will be applied to the overlay.
 * @param overlayColor The background color of the overlay.This color will always be displayed
 *                     with an alpha of 0.5f.
 * @param isOverlayVisible Indicates whether the overlay is visible or not.
 * @param content The content over which the overlay should be visible.
 */
@Composable
fun CircularLoadingProgressOverlay(
    modifier: Modifier = Modifier,
    overlayColor: Color = Color.Black,
    isOverlayVisible: Boolean,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        content()
        if (isOverlayVisible) {
            Surface(
                modifier = modifier
                    .fillMaxSize()
                    .alpha(0.5f),
                color = overlayColor
            ) {
                Box {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

