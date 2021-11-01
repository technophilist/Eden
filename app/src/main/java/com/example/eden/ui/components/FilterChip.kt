package com.example.eden.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@ExperimentalMaterialApi
@Composable
fun FilterChip(
    onClick: () -> Unit,
    isSelected: Boolean = false,
    outlinedBorderColor: Color = MaterialTheme.colors.secondary,
    selectedBackgroundColor: Color = MaterialTheme.colors.secondary,
    content: @Composable () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Max),
        onClick = onClick,
        border = if (isSelected) null else BorderStroke(1.dp, outlinedBorderColor),
        color = if (isSelected) selectedBackgroundColor else MaterialTheme.colors.surface,
        shape = CircleShape,
    ) {
        Row(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp
            )
        ) {
            if (isSelected) {
                Icon(
                    modifier = Modifier
                        .align(CenterVertically)
                        .padding(end = 8.dp)
                        .size(16.dp),
                    imageVector = Icons.Filled.Done,
                    contentDescription = null
                )
            }
            content()
        }
    }
}